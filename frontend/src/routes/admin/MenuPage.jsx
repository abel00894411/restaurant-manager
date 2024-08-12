import fetchAPI from "../../util/fetchAPI";
import AdminMenu from "../../components/AdminMenu";
import AddButton from '../../components/AddButton';
import './MenuPage.css';

const addCategory = () => {
    const name = prompt('Escribe el nombre de la nueva categoría');

    if (!name) {
        return;
    }

    fetchAPI('categorias', 'POST', {
        categoria: name
    })
    .then(res => {
        location.reload();
    })
    .catch(error => {
        alert('No fue posible crear la categoría');
        devLog(error.message);
    });
};

const MenuPage = () => {
    return (
        <>
            <h1>Menú</h1>
            <AdminMenu />
            <div className="MenuPage__buttons">
                <AddButton redirect="/menu/nuevo">Nuevo ítem</AddButton>
                <AddButton onClick={addCategory}>Nueva categoría</AddButton>
            </div>
        </>
    );
};

export default MenuPage;