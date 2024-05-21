import OrderItem from "../models/OrderItem";
import IEventFunction from '../interfaces/IEventFunction';
import IAssignedOrderItemEvent from "../interfaces/IAssignedOrderItemEvent";
import { Message } from 'stompjs';

/**
 * Fires an event for new messages from topic/items/asignados/{id}. For cook users.
 */
const assignedOrderItemEvent: IEventFunction = (message: Message) => {
    const body = JSON.parse(message.body);
    const { idItemMenu, idItemOrden, cantidad, estado } = body?.items;

    const item = new OrderItem(idItemOrden, undefined, idItemMenu, estado, cantidad);
    Object.freeze(item);

    const detail = {
        orderItem: item
    };

    const event: IAssignedOrderItemEvent = new CustomEvent('assignedOrderItem', { detail });
    document.dispatchEvent(event);
    return event;
}

export default assignedOrderItemEvent;