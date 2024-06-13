class MenuItem {
    readonly id: number;
    readonly name: string;
    readonly category: string;
    readonly price: number;
    readonly description: string;
    readonly image: string;

    constructor(id: number, name: string, category: string, price: number, description: string, image: string) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.image = image;
        Object.freeze(this);
    }
}

export default MenuItem;