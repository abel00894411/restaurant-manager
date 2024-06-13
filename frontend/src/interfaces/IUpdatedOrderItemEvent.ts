import OrderItem from "../models/OrderItem";

interface IUpdatedOrderItem extends CustomEvent {
    detail: {
        item: OrderItem
    }
}

export default IUpdatedOrderItem;