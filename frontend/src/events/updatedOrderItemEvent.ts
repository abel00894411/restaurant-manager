import IEventFunction from "../interfaces/IEventFunction";
import IUpdatedOrderItemEvent from "../interfaces/IUpdatedOrderItemEvent";
import { Message } from "stompjs";
import OrderItem from "../models/OrderItem";

/**
 * Creates an CustomEvent for new messages from topic/items/actualizaciones/{id}.
 * For waiter and cook users.
 */
const updatedOrderItemEvent: IEventFunction = (message: Message): IUpdatedOrderItemEvent => {
    const body = JSON.parse(message.body);
    const { idItemOrden, idOrden, estado } = body;
    const item = new OrderItem(idItemOrden, idOrden, undefined, estado, undefined, undefined);
    Object.freeze(item);
    
    const detail = {
        item: item,
    };
    
    return new CustomEvent('updatedOrderItem', { detail });
}

export default updatedOrderItemEvent;