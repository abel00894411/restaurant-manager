import OrderItem from "../models/OrderItem";

interface IAddedOrderItemsEvent extends CustomEvent {
    detail: {
        orderId: number,
        items: OrderItem[]
    }
}

export default IAddedOrderItemsEvent;