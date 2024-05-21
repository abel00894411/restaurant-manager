import JobManager from "./JobManager";
import ITopic from "../interfaces/ITopic";
import getTokenData from "../util/getTokenData";
import devLog from "../util/devLog";
import Order from "./Order";
import OrderItem from "./OrderItem";
import assignedOrderItemEvent from "../events/assignedOrderItemEvent";
import IAssignedOrderItemEvent from "../interfaces/IAssignedOrderItemEvent";
import listedOrderItemsEvent from "../events/listedOrderItemsEvent";
import IListedOrderItemsEvent from "../interfaces/IListedOrderItemsEvent";

const tokenData = getTokenData();
const userId = tokenData.idUsuario;

const topics: ITopic[] = [
    { 
        path: `/topic/items/listados/${userId}`,
        event: listedOrderItemsEvent
    },
    { 
        path: `/topic/items/asignados/${userId}`,
        event: assignedOrderItemEvent
    }
];

/**
 * Manages all tasks, data, events and server connections related to the order
 * items assigned to the cook user.
 */
class KitchenManager extends JobManager {
    constructor() {
        super(topics);

        /* GET ASSIGNED ITEMS LIST */

        const listDestination = '/app/items/enlistar';
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
                alert(`No se pudo obtener los items de orden correspondientes al cocinero, vuelve a intentarlo`);
            }
        }, 1000);


        /* HANDLE EVENTS */
        
        document.addEventListener('assignedOrderItem', (event: Event) => {
            const detail = (event as IAssignedOrderItemEvent).detail;
            const { orderItem } = detail;
            this.list.push(orderItem as (Order & OrderItem));
        });

        document.addEventListener('listedOrderItems', (event: Event) => {
            const detail = (event as IListedOrderItemsEvent).detail;
            const { items } = detail;
            this.list = Array.from(items);
        });
        
    }
    
    set(item: OrderItem) {
        if (!(item instanceof OrderItem)) {
            throw new Error(`Item is not an instance of OrderItem. Item was ignored.`);
        }

        const ref = this.list.find(it => it.id == item.id);

        if (ref) {
            /* UPDATE ITEM */

            const destination = '/app/items/actualizar';
            return this.sendMessage(destination, {
                "idItemOrden": item.id,
                "estado": item.state
            });
            
        } else {
            devLog(`Error: Cook user trying to set an order item not included in the local list, id ${item.id}`);
            return false;
        }
    

    }

    get(id: number) {
        const ref = this.list.find(item => item.id == id);

        if (!ref) {
            return undefined;
        }

        const copy = Object.assign({}, ref);
        return copy;
    }
}

export default KitchenManager;