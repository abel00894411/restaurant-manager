import JobManager from "./JobManager";
import Order from "./Order";
import OrderItem from "./OrderItem";
import getTokenData from "../util/getTokenData";
import ITopic from "../interfaces/ITopic";
import createdOrderEvent from '../events/createdOrderEvent';
import assignedOrderEvent from '../events/assignedOrderEvent';
import listedOrdersEvent from '../events/listedOrdersEvent';
import finishedOrderEvent from '../events/finishedOrderEvent';
import addedOrderItemsEvent from '../events/addedOrderItemsEvent';
import updatedOrderItemEvent from "../events/updatedOrderItemEvent";
import ICreatedOrderEvent from "../interfaces/ICreatedOrderEvent";
import IAssignedOrderEvent from "../interfaces/IAssignedOrderEvent";
import IListedOrdersEvent from "../interfaces/IListedOrdersEvent";
import IFinishedOrderEvent from "../interfaces/IFinishedOrderEvent";
import IAddedOrderItemsEvent from "../interfaces/IAddedOrderItemsEvent";
import IUpdatedOrderItem from "../interfaces/IUpdatedOrderItemEvent";

const tokenData = getTokenData();
const userId = tokenData.idUsuario;

const topics: ITopic[] = [
    {
        path: `/topic/ordenes/creadas/${userId}`,
        event: createdOrderEvent
    },
    {
        path: `/topic/ordenes/asignados/${userId}`,
        event: assignedOrderEvent
    },
    {
        path: `/topic/ordenes/listados/${userId}`,
        event: listedOrdersEvent
    },
    {
        path: `/topic/ordenes/terminadas/${userId}`,
        event: finishedOrderEvent
    },
    {
        path: `/topic/items/agregados/${userId}`,
        event: addedOrderItemsEvent
    },
    {
        path: `/topic/items/actualizaciones/${userId}`,
        event: updatedOrderItemEvent
    }
];

/**
 * Manages tasks, data, events and server connections related to the orders
 * assigned to the waiter user.
 */
class OrderManager extends JobManager {
    constructor() {
        super(topics);

        /* GET ASSIGNED ITEMS LIST */

        const listDestination = '/app/ordenes/enlistar';
        const listSendMaxAttempts = 5;
        let listSendAttempts = 0;

        const listInterval = setInterval(() => {
            const sent = this.sendMessage(listDestination);
            listSendAttempts++;
            
            if (sent) {
                clearInterval(listInterval);
                return;
            }

            if (listSendAttempts >= listSendMaxAttempts) {
                clearInterval(listInterval);
                alert(`No se pudo obtener las ordenes correspondientes al mesero, vuelve a intentarlo`);
            }
        }, 1000);


        /* HANDLE EVENTS */

        document.addEventListener('createdOrder', (event) => {
            const detail = (event as ICreatedOrderEvent).detail;
            const { waiterId, waiter } = detail;
            const message = (waiterId == userId) ? 'La nueva orden te fue asignada' : `La nueva orden se le asignó a ${waiter}`;
            console.log(message);
        });

        document.addEventListener('assignedOrder', (event) => {
            const detail = (event as IAssignedOrderEvent).detail;
            const { order } = detail; // 'order' is frozen

            const newOrder = new Order(order.id, order.date, order.state);
            for (const item of order.getItems()) {
                newOrder.setItem(new OrderItem(item.id, item.orderId, item.menuItemId, item.state, item.quantity, item.creationDateTime));
            }

            this.list.push(newOrder as (Order & OrderItem));
        });

        document.addEventListener('listedOrders', (event) => {
            const detail = (event as IListedOrdersEvent).detail;
            const { orders } = detail; // each order in 'orders' is frozen

            const newOrders: Order[] = [];
            for (const order of orders) {
                const newOrder = new Order(order.id, order.date, order.state);
                for (const item of order.getItems()) {
                    newOrder.setItem(new OrderItem(item.id, item.orderId, item.menuItemId, item.state, item.quantity, item.creationDateTime));
                }
                newOrders.push(newOrder);
            }

            this.list = newOrders;
        });

        document.addEventListener('finishedOrder', (event) => {
            const detail = (event as IFinishedOrderEvent).detail;
            const { orderId, subtotal, vat, total } = detail;
            
            const orderRef = this.list.find(order => order.id == orderId);

            if (!orderRef) {
                throw new Error(`Tried to finish an order not found in the local list (id: ${orderId})`);
            }

            orderRef.state = 'DESPACHADA';
        });

        document.addEventListener('addedOrderItems', (event) => {
            const detail = (event as IAddedOrderItemsEvent).detail;
            const { orderId, items } = detail;

            const orderRef = this.list.find(order => order.id == orderId);

            if (!orderRef) {
                throw new Error(`Tried to fill an order not found in the local list (id: ${orderId})`);
            }

            for (const item of items) {
                (orderRef as Order).setItem(item);
            }
        });

        document.addEventListener('updatedOrderItem', (event) => {
            const detail = (event as IUpdatedOrderItem).detail;
            const { item } = detail;

            const orderRef = this.list.find(order => order.id == item.orderId) as Order;
            orderRef.setItem(item);
        });

    }

    set(obj: Order | OrderItem) {
        if (obj instanceof Order) {

            const orderRef = this.list.find(order => order.id == obj.id) as Order | undefined;

            if (orderRef) {

                /* UPDATE ORDER */

                if (obj.getItems().length > 0) {
                    throw new Error(`Unable to update order. Order is not empty.`);
                }

                const finishOrderDestination = '/app/ordenes/terminar';

                if (obj.state == 'DESPACHADA') {
                    return this.sendMessage(finishOrderDestination, {
                        idOrden: obj.id
                    });
                }

                return true;

            } else {

                if (obj.id) {
                    throw new Error(`User trying to set an order with an id that doesn't match any order in the local list (id: ${obj.id})`);
                }

                /* CREATE NEW ORDER */
                
                if (obj.getItems().length == 0) {
                    throw new Error(`Unable to create new order as its item list is empty`);
                }

                const newOrderDestination = '/app/ordenes/nueva';
                const newOrderItems = obj.getItems().map(item => ({
                    idItemMenu: item.menuItemId,
                    cantidad: item.quantity
                }));

                return this.sendMessage(newOrderDestination, {
                    items: newOrderItems
                });
            }


        } else if (obj instanceof OrderItem) {

            let itemRef: OrderItem | undefined = undefined;

            if (obj.id) {
                for (const order of this.list as Order[]) {
                    itemRef =  order.findItem(obj.id);
    
                    if (itemRef) {
                        break;
                    }
                }

                if (!itemRef) {
                    throw new Error(`User trying to set an order item with an id that doesn't match any order item in the local list (id: ${obj.id})`);
                }
            }

            if (itemRef) {
                
                /* UPDATE ITEM */

                const updateOrderItemDestination = '/app/items/actualizar';

                if (obj.state != itemRef.state) {
                    return this.sendMessage(updateOrderItemDestination, {
                        idItemOrden: obj.id,
                        estado: obj.state
                    });
                }

                return true;
                
            } else {

                /* CREATE NEW ITEM */

                if (!obj.orderId || !obj.menuItemId || !obj.quantity || !obj.state ) {
                    throw new Error(`Cannot create order item as some required properties are undefined`);
                }

                const newOrderItemDestination = '/app/ordenes/llenar';
                return this.sendMessage(newOrderItemDestination, {
                    idOrden: obj.orderId,
                    items: [
                        {
                            idItemMenu: obj.menuItemId,
                            cantidad: obj.quantity
                        }
                    ]
                });
            }

        } else {
            throw new Error(`Item is not an instance of Order or OrderItem. Item was ignored.`);
        }
    }

    get(id: number, type: Function) {
        switch (type) {
            case Order:
                const orderRef = this.list.find(order => order.id == id) as Order | undefined;

                if (!orderRef) {
                    return undefined;
                }

                const orderCopy = new Order(orderRef.id, orderRef.date, orderRef.state);
                
                for (const item of orderRef.getItems()) {
                    orderCopy.setItem(item);
                }

                return orderCopy;
                
            case OrderItem:
                let itemRef: OrderItem | undefined;

                for (const order of this.list as Order[]) {
                    itemRef =  order.findItem(id);

                    if (itemRef) {
                        break;
                    }
                }

                if (!itemRef) {
                    return undefined;
                }

                const itemCopy = new OrderItem(itemRef.id, itemRef.orderId, itemRef.menuItemId, itemRef.state, itemRef.quantity, itemRef.creationDateTime);
                return itemCopy;

            default:
                throw new Error(`Unable to get item of type ${type?.name}`);
        }
    }

}

export default OrderManager;