import OrderItem from "../models/OrderItem";
import IEventFunction from '../interfaces/IEventFunction';
import { Message } from 'stompjs';
import IAddedOrderItemsEvent from "../interfaces/IAddedOrderItemsEvent";

/**
 * Creates an event for new messages from topic/items/agregados/{id}. For cook users.
 */
const listedOrderItemsEvent: IEventFunction = (message: Message): IAddedOrderItemsEvent => {
    const body = JSON.parse(message.body);
    const { idOrden, items } = body;
    
    const itemsObj = items.map(item => {
        const itemObj = new OrderItem(item.idItemOrden, idOrden, item.idItemMenu, item.estado, item.cantidad);
        Object.freeze(itemObj);
        return itemObj
    });

    const detail = {
        orderId: idOrden,
        items: itemsObj
    };

    return new CustomEvent('addedOrderItems', { detail });
}

export default listedOrderItemsEvent;