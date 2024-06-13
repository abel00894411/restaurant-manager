import OrderItem from "../models/OrderItem";

interface IAssignedOrderItemEvent extends CustomEvent {
    detail: {
        orderItem: OrderItem
    }
}

export default IAssignedOrderItemEvent;