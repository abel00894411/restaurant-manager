import Order from "../models/Order";

interface IAssignedOrderEvent extends CustomEvent {
    detail: {
        order: Order
    }
}

export default IAssignedOrderEvent;