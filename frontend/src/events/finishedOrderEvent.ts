import IEventFunction from '../interfaces/IEventFunction';
import { Message } from 'stompjs';
import IFinishedOrderEvent from "../interfaces/IFinishedOrderEvent";

/**
 * Creates an event for new messages from topic/ordenes/terminadas/{id}. For waiter users.
 */
const listedOrderItemsEvent: IEventFunction = (message: Message): IFinishedOrderEvent => {
    const body = JSON.parse(message.body);
    const { idOrden, subtotal, iva, total } = body;

    const detail = {
        orderId: idOrden,
        subtotal: subtotal,
        vat: iva,
        total: total
    };

    Object.freeze(detail);

    return new CustomEvent('finishedOrder', { detail });
}

export default listedOrderItemsEvent;