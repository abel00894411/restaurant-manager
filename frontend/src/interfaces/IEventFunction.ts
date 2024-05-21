import { Message } from "stompjs";

interface IEventFunction {
    (message: Message): CustomEvent;
}

export default IEventFunction;