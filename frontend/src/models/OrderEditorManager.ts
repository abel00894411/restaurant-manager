import Order from "./Order";
import OrderItem from "./OrderItem";
import OrderManager from "./OrderManager";
import { jobManager } from "../util/jobManager";

class OrderEditorManager {
    #currentOrder: Order | undefined = undefined;
    #isNew: boolean = false;

    
    addItem(menuItemId: number, quantity: number) {
        if (!this.#currentOrder) {
            throw new Error(`Can't add item because there is no current order`);
        }

        const jm = jobManager as OrderManager;

        if (this.isNew) {
            const filledItem = new OrderItem(Math.random(), this.#currentOrder.id, menuItemId, 'state', quantity, new Date());
            this.#currentOrder.setItem(filledItem);
            this.#currentOrder.id = undefined;
            jm.set(this.#currentOrder);
            
        } else {
            const item = new OrderItem(undefined, this.#currentOrder?.id, menuItemId, 'PENDIENTE', quantity, undefined);
            jm.set(item);
            
        }

        this.reset();
    }

    reset() {
        this.#currentOrder = undefined;
        this.#isNew = false;
    }

    
    selectOrder(orderId: number) {
        this.reset();
        this.#currentOrder = (jobManager)?.getAll().find(order => order.id == orderId) as Order;
        this.#isNew = false;

        if (!this.#currentOrder) {
            this.reset();
            throw new Error(`Can't select order with id ${orderId} because it doesn't exist in the local list`);
        }
    }

    startNewOrder() {
        this.reset();
        this.#currentOrder = new Order();
        this.#isNew = true;
    }

    get isNew() {
        return this.#isNew;
    }

    get onEditionMode() {
        return !!this.#currentOrder;
    }
}

const orderEditorManager = new OrderEditorManager();

export { orderEditorManager };