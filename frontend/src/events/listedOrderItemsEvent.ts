import OrderItem from "../models/OrderItem";
import IEventFunction from '../interfaces/IEventFunction';
import IListedOrderItemsEvent from '../interfaces/IListedOrderItemsEvent';
import { Message } from 'stompjs';

/**
 * Creates an event for new messages from topic/items/listados/{id}. For cook users.
 */
const listedOrderItemsEvent: IEventFunction = (message: Message) => {
    const body = JSON.parse(message.body);
    const list: [] = body.items;

    const items = list.map((item: any) => new OrderItem(item.idItemOrden, undefined, item.idItemMenu, item.estado, item.cantidad, new Date(item.fecha) ));
    Object.freeze(items);

    const detail = {
        items: items
    };

    const event: IListedOrderItemsEvent = new CustomEvent('listedOrderItems', { detail });
    return event;
}

export default listedOrderItemsEvent;