import OrderItem from "../models/OrderItem";
import IEventFunction from '../interfaces/IEventFunction';
import IAssignedOrderItemEvent from "../interfaces/IAssignedOrderItemEvent";
import { Message } from 'stompjs';

/**
 * Creates an CustomEvent for new messages from topic/items/asignados/{id}. For cook users.
 */
const assignedOrderItemEvent: IEventFunction = (message: Message) => {
    const body = JSON.parse(message.body);
    const { idItemMenu, idItemOrden, cantidad, estado, fecha } = body?.items;
    const creationDateTime = new Date(fecha);

    const item = new OrderItem(idItemOrden, undefined, idItemMenu, estado, cantidad, creationDateTime);
    Object.freeze(item);

    const detail = {
        orderItem: item
    };

    const event: IAssignedOrderItemEvent = new CustomEvent('assignedOrderItem', { detail });
    return event;
}

export default assignedOrderItemEvent;