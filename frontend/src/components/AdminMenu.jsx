import './AdminMenu.css';
import { menu } from '../models/Menu';
import TinyButton from '../components/TinyButton';
import fetchAPI from '../util/fetchAPI';
import devLog from '../util/devLog';

const currencyFormatter = new Intl.NumberFormat('es-MX', { style: 'currency', currency: 'MXN', maximumFractionDigits: 0 });

const editCategoryName = (categoryId) => {
    const newName = prompt('Escribe un nuevo nombre para la categoría');

    if (!newName) {
        return;
    }

    fetchAPI(`categoria/${categoryId}`, 'PUT', { categoria: newName })
        .then(res => {
            location.reload();
        })
        .catch(error => {
            alert('No fue posible renombrar la categoría');
            devLog(error.message);
        });
};

const deleteCategory = (categoryId) => {
    const confirmation = confirm('¿Desea eliminar la categoría? Esto eliminará todos sus ítems');

    if (!confirmation) {
        return;
    }

    fetchAPI(`categoria/${categoryId}`, 'DELETE')
        .then(res => {
            location.reload();
        })
        .catch(error => {
            alert('No fue posible eliminar la categoría');
        });
};

const deleteItem = (itemId) => {
    const confirmation = confirm('¿Desea eliminar el ítem?');

    if (!confirmation) {
        return;
    }

    fetchAPI(`menu/${itemId}`, 'DELETE')
        .then(res => {
            location.reload();
        })
        .catch(error => {
            alert('No fue posible eliminar el ítem');
            devLog(error.message);
        });
};

const AdminMenu = () => {
    const categories = menu.getAllCategories();
    const items = menu.getAllItems();

    if (categories.length == 0) {
        return <>Agrega categorías para poder llenar el menú</>;
    }

    return (
        <div className="adminMenu">
            { categories.map((category, i) => {

                const categoryItems = items.filter(item => item.category == category.categoria);

                return (
                    <div className="adminMenu__category" key={i}>
                        <div className="adminMenu__category__title-container">
                            <h4 className="adminMenu__category__title-container__title">{category.categoria}</h4>
                            <div className="adminMenu__category__title-container__buttons">
                                <TinyButton icon='edit' onClick={()=>editCategoryName(category.idCategoria)} />
                                <TinyButton icon='delete' onClick={()=>deleteCategory(category.idCategoria)} />
                            </div>
                        </div>

                        { categoryItems.length == 0 ? 
                            <>La categoría está vacía</>
                            : 
                            categoryItems.map((item, i) => {
                                return (
                                    <div className="adminMenu__category__item" key={i}>
                                        <div className="adminMenu__category__item__details shadow rounded-porder">
                                            <img src={`data:image/jpeg;base64,${item.image}`} />
                                            <p className="adminMenu__category__item__details__name">{item.name}</p>
                                            <p className="adminMenu__category__item__details__price">{currencyFormatter.format(item.price)}</p>
                                        </div>

                                        <div className="adminMenu__category__item__description">
                                            <p className="adminMenu__category__item__description__title" >Descripción</p>
                                            <p className="adminMenu__category__item__description__content">{item.description}</p>
                                        </div>

                                        <div className="adminMenu__category__item__buttons">
                                            <TinyButton icon="edit" redirect={`/menu/editar/${item.id}`} />
                                            <TinyButton icon="delete" onClick={() => deleteItem(item.id)} />
                                        </div>
                                    </div>  
                                );
                            }) 
                        }

                    </div>
                );

            }) }
        </div>
    );
};

export default AdminMenu;