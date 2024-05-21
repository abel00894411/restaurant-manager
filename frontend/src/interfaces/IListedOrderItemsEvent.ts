import OrderItem from "../models/OrderItem";

interface IListedOrderItemsEvent extends CustomEvent{
    detail: {
        items: OrderItem[]
    }
}

export default IListedOrderItemsEvent;