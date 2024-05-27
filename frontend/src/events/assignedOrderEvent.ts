import IEventFunction from "../interfaces/IEventFunction";
import { Message } from "stompjs";
import OrderItem from "../models/OrderItem";
import IAssignedOrderEvent from "../interfaces/IAssignedOrderEvent";
import Order from "../models/Order";

/**
 * Creates an CustomEvent for new messages from topic/ordenes/asignados/{id}. For waiter users.
 */
const assignedOrderEvent: IEventFunction = (message: Message): IAssignedOrderEvent => {
    const body = JSON.parse(message.body);
    
    const { idOrden, fecha, subtotal, estado, items } = body.orden;
    const order = new Order(idOrden, new Date(fecha), estado);

    for (const item of items) {
        const orderItem = new OrderItem(item.idItemOrden, idOrden, item.idItemMenu, item.estado, item.cantidad);
        order.setItem(orderItem);
    }

    Object.freeze(order);

    const detail = {
        order: order,
    };
    
    return new CustomEvent('assignedOrder', { detail });
}

export default assignedOrderEvent;