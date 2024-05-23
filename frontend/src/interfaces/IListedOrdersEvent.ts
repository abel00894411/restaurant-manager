import Order from "../models/Order";

interface IListedOrdersEvent extends CustomEvent {
    detail: {
        orders: Order[]
    }
}

export default IListedOrdersEvent;