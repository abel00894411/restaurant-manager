import AdminMenu from "../../components/AdminMenu";
import AddButton from '../../components/AddButton';

const MenuPage = () => {
    return (
        <>
            <h1>Menú</h1>
            <AdminMenu />
            <AddButton redirect="/menu/nuevo">Nuevo ítem</AddButton>
        </>
    );
};

export default MenuPage;