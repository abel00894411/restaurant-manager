import { useEffect, useState } from 'react';
import fetchAPI from '../util/fetchAPI';
import devLog from '../util/devLog';
import { menu } from '../models/Menu';
import './MenuItemForm.css';

const MenuItemForm = ({ menuItemId, newMenuItem = false }) => {
    if (menuItemId && newMenuItem) {
        devLog('Error: menuItemForm has menuItemId and newMenuItem == true');
        return <>...</>
    }

    const localItemData = menu.getItem(menuItemId);

    const fields = {
        name: '',
        price: '',
        description: '',
        categoryId: '',
        image: ''
    };

    const filledFields = {
        name: localItemData?.name,
        price: localItemData?.price,
        description: localItemData?.description,
        categoryId: menu.getAllCategories().find(cat => cat.categoria == localItemData.category)?.idCategoria,
        image: localItemData?.image
    };

    const [menuItemData, setmenuItemData] = useState( newMenuItem ? fields : filledFields );

    const handleChange = (event) => {
        const input = event.target;

        if (input.name == 'image') {

            return;
        }

        setmenuItemData(oldData => {
            return {
                ...oldData,
                [input.name]: input.value
            }
        });
    }

    const submit = () => {

        const updateMenuItemBody = {
            producto: menuItemData.name,
            precio: menuItemData.price,
            descripcion: menuItemData.description,
            idCategoria: menuItemData.categoryId,
        };

        const newMenuItemBody = {
            ...updateMenuItemBody,
            imagen: menuItemData.image
        };
        
        if (newMenuItem) {
            
            fetchAPI('menu', 'POST', newMenuItemBody)
                .then(res => {
                    alert(`Ítem creado con éxito`);
                    location.assign(`/menu/editar/${res.itemMenu.id}`);
                })
                .catch(error => {
                    alert('No fue posible crear el nuevo ítem');
                    devLog(`POST (create menu item): ${error.message}`);
                });

        } else {

            fetchAPI(`menu/${menuItemId}`, 'PUT', updateMenuItemBody)
                .then(res => {
                    alert('Ítem actualizado con éxito');
                    location.reload();
                })
                .catch(error => {
                    alert('No fue posible actualizar los datos');
                    devLog(`PUT (update data): ${error.message}`)
                });

        }

    }

    return (
        <form className='menuItemForm'>
            
            <div className="menuItemForm__fields">

                <label>
                    Nombre
                    <input autoComplete="off" type="text" name="name" value={menuItemData.name} onChange={handleChange} />
                </label>

                <label>
                    Precio
                    <input autoComplete="off" type="text" name="price" value={menuItemData.price} onChange={handleChange} />
                </label>

                <label>
                    Descripcion (opcional)
                    <input autoComplete="off" type="text" name="description" value={menuItemData.description} onChange={handleChange} />
                </label>


                <label>
                    Categoría
                    <select name="categoryId" value={menuItemData.categoryId} onChange={handleChange} >
                        { menu.getAllCategories().map((cat, i) => {
                            return (
                                    <option key={i} value={cat.idCategoria}>
                                        {cat.categoria}
                                    </option>
                                );
                            }
                        )}
                    </select>
                </label>

                { newMenuItem &&
                    <label>
                        Foto
                        <input autoComplete="off" type="file" name="image" onChange={handleChange} />
                    </label>
                }
                

            </div>

            <button type='button' onClick={submit}>{ newMenuItem ? 'Crear usuario' : 'Guardar cambios' }</button>

        </form>
    );
};

export default MenuItemForm;