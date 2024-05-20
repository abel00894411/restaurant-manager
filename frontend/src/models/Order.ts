import OrderItem from "./OrderItem";

/**
 * A representation of an order, intended only to store and organize data.
 * Important: all its properties can be undefined, those undefined properties
 * will be ignored, if no id is provided, the object will represent a new order.
 */
class Order {
    id?: number;
    date?: Date;
    state?: string;
    #items: OrderItem[] = [];

    constructor(
        id: number | undefined = undefined,
        date: Date | undefined = undefined,
        state: string | undefined = undefined
    ) {
        this.id = id;
        this.date = date;
        this.state = state;
    }

    /**
     * Calculates and returns the order's subtotal, using prices from the MenuDataContext.
     * @returns The subtotal for the order
     */
    getSubtotal(): number {
        let subtotal = 0;
        
        for (const item of this.#items) {
            try {
                subtotal += item.getCost();
            } catch(error) {
                throw new Error(`Unable to get subtotal from order with id ${this.id}, at item with id ${item.id}: ${error.message}`);
            }
        }

        return subtotal;
    }

    /**
     * Calculates and returns the order's VAT, using prices from the MenuDataContext.
     * @returns The VAT for the order
     */
    getVat(): number {
        const vatFactor = 0.16;
        const subtotal = this.getSubtotal();
        return subtotal * vatFactor;
    }

    /**
     * Calculates and returns the order's total cost, using prices from the MenuDataContext.
     * @returns The total cost for the order
     */
    getTotal(): number {
        const subtotal = this.getSubtotal();
        const vat = this.getVat();
        return subtotal + vat;
    }

    /**
     * Returns an array containing copys of the order's items.
     */
    getItems(): OrderItem[] {
        const res = this.#items.map(item => new OrderItem(item.id, item.orderId, item.menuItemId, item.state, item.quantity));
        return res;
    }

    /**
     * Finds and returns a copy of the item with the given id, or undefined if none.
     * @param orderItemId The id of the item to find
     * @returns A copy of the order item or undefined if not found
     */
    findItem(orderItemId: number): OrderItem | undefined {
        const ref = this.#items.find(item => item.id === orderItemId);

        if (!ref) {
            return undefined;
        }

        const copy = new OrderItem(ref.id, ref.orderId, ref.menuItemId, ref.state, ref.quantity);
        return copy;
    }

     /**
     * It updates or creates an item in the order. The id of the given object must be defined,
     * and the item's orderId must match this order's id. Undefined properties in the given
     * object will be ignored and not updated, however, when creating a new order all properties
     * must be set.
     * @param item 
     */
     setItem(item: OrderItem) {
        const { id, orderId, menuItemId, state, quantity } = item;

        if (!id) {
            throw new Error(`Invalid item provided: no id (${id})`);
        }

        if (orderId !== this.id) {
            throw new Error(`Invalid item provided: item order id (${orderId}) does not match order id (${this.id})`);
        }

        const ref = this.#items.find(item => item.id == id);

        if (!ref && (!menuItemId || !state || !quantity) ) {
            throw new Error(`Unable to create item ${id} in order ${this.id}: Some properties are undefined`);
        }

        if (!ref) {
            const copy = new OrderItem(item.id, item.orderId, item.menuItemId, item.state, item.quantity);
            this.#items.push(copy);
        } else {

            if (menuItemId) {
                ref.menuItemId = menuItemId;
            }

            if (state) {
                ref.state = state;
            }

            if (quantity) {
                ref.quantity = quantity;
            }

        }
    }
    
}

export default Order;