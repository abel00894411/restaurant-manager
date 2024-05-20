import MenuItem from './MenuItem';
import fetchAPI from '../util/fetchAPI';
import devLog from '../util/devLog';

/**
 * When instanciated, fetches menu data from backend and stores it.
 */
class Menu {
    #categories: object[] = [];
    #items: MenuItem[] = [];
    #constructed = false;

    constructor() {
        fetchAPI('categorias', 'GET')
            .then(async (resCat: any) => {
                
                resCat.categorias.forEach((cat: any) => {
                    this.#categories.push(cat);
                });

                try {
                    const resMenu: any = await fetchAPI('menu', 'GET');

                    resMenu.itemsMenu.forEach((item: any) => {
                        const categoryObj: any = this.#categories.find((cat: any) => cat.idCategoria == item.categoria);
    
                        if (!categoryObj) {
                            throw new Error(`undefined category for item ${item.producto} (category id ${item.categoria})`);
                        }
    
                        const categoryName = categoryObj.categoria;
    
                        const menuItem = new MenuItem(item.idItemMenu, item.producto, categoryName, item.precio, item.descripcion, item.imagen);
                        this.#items.push(menuItem);
                    });
    
                    this.#constructed = true;

                } catch(error) {
                    devLog(`Unable to instanciate Menu object: ${error.message}`);
                    alert('No se pudo obtener los datos del menú, vuelve a intentarlo.');
                }
                    
            })
            .catch(error => {
                devLog(`Unable to get categories: ${error.message}`);
                alert(`No se pudo obtener los datos de las categorías del menú, vuelve a intentarlo.`);
            })
    }


    /**
     * Finds and returns the menu item object with the given id, or undefined if none.
     * @param id 
     */
    getItem(id: number): MenuItem | undefined {
        if (!this.#constructed) {
            throw new Error(`Cannot use Menu before construction finalization.`);
        }

        return this.#items.find((item: MenuItem) => item.id == id);
    }

    /**
     * Returns true if the object has finished construction, false otherwise.
     */
    isReady(): boolean {
        return this.#constructed;
    }
}

const menu = new Menu();

export { menu };