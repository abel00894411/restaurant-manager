import { Message } from "stompjs";

/**
 * A function to be called when a STOMP message is received on a certain channel.
 * This function creates, fills and returns a CustomEvent to be dispatched by
 * the JobManager.
 * @argument message A STOMP message
 * @returns The CustomEvent to be dispatched
 */
interface IEventFunction {
    (message: Message): CustomEvent;
}

export default IEventFunction;