import IEventFunction from '../interfaces/IEventFunction';
import ICreatedOrderEvent from "../interfaces/ICreatedOrderEvent";
import { Message } from 'stompjs';

/**
 * Creates an CustomEvent for new messages from topic/ordenes/creadas/{id}. For waiter users.
 */
const createdOrderEvent: IEventFunction = (message: Message): ICreatedOrderEvent => {
    const body = JSON.parse(message.body);
    const { idMesero, mesero } = body;

    const detail = {
        waiterId: idMesero,
        waiter: mesero
    };

    Object.freeze(detail);

    return new CustomEvent('createdOrder', { detail });
}

export default createdOrderEvent;