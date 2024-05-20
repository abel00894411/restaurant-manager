import { menu } from './Menu';

/**
 * A representation of an order item, intended only to store and organize
 * data. Important: all its properties can be undefined, those undefined
 * properties will be ignored, if no id is provided, the object will
 * represent a new order item.
 */
class OrderItem {
    id?: number;
    orderId?: number;
    menuItemId?: number;
    state?: string;
    quantity?: number;

    /**
     * @param id The id of the order item this object represents, if undefined, this object represents a new order item
     * @param orderId The id of the order this item belongs to, can't add item to an order if id's doesn't match
     * @param menuItemId 
     * @param state
     * @param quantity 
     */
    constructor(
        id: number | undefined = undefined, 
        orderId: number | undefined = undefined, 
        menuItemId: number | undefined = undefined, 
        state: string | undefined = undefined, 
        quantity: number | undefined = undefined
    ) {
        this.id = id;
        this.orderId = orderId;
        this.menuItemId = menuItemId;
        this.state = state;
        this.quantity = quantity;
    }

    getCost(): number {
        if (!this.menuItemId) {
            throw new Error(`No menu item id, unable to calculate cost`);
        }

        if (!this.quantity) {
            throw new Error(`No quantity, unable to calculate cost`);
        }

        if (!menu) {
            throw new Error(`No menu instance, unable to calculate cost`);
        }
        
        if (!menu.isReady()) {
            throw new Error("Menu isn't ready yet, unable to calculate cost");
        }

        const item = menu.getItem(this.menuItemId);

        if (!item) {
            throw new Error(`No data for menu item with id ${this.menuItemId} was found in menu, unable to calculate cost`);
        }

        const price = item.price;

        if (!price) {
            throw new Error(`No price for menu item with id ${item.id}, unable to calculate cost`);
        }

        const cost = this.quantity * price;
        return cost;
    }
}

export default OrderItem;