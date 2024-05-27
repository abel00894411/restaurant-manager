import Order from "../models/Order";
import IEventFunction from '../interfaces/IEventFunction';
import IListedOrdersEvent from '../interfaces/IListedOrdersEvent';
import { Message } from 'stompjs';
import OrderItem from "../models/OrderItem";

/**
 * Creates an event for new messages from topic/ordenes/listados/{id}. For waiter users.
 */
const listedOrdersEvent: IEventFunction = (message: Message): IListedOrdersEvent => {
    const body = JSON.parse(message.body);
    const list: [] = body.ordenes;

    const orders = list.map((order: any) => {
        const orderObj = new Order(order.idOrden, order.fecha, order.estado);

        const items = order.items;

        for (const item of items) {
            const itemObj = new OrderItem(item.idItemOrden, order.idOrden, item.idItemMenu, item.estado, item.cantidad);
            orderObj.setItem(itemObj);
        }

        Object.freeze(orderObj);
        return orderObj;
    });

    Object.freeze(orders);

    const detail = {
        orders: orders
    };

    return new CustomEvent('listedOrders', { detail });
}

export default listedOrdersEvent;