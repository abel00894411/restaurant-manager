import { useLocation } from "react-router-dom";
import MenuItemForm from "../../components/MenuItemForm";

const EditMenuPage = () => {
    const url = useLocation().pathname;
    const menuItemId = url.split('/').reverse()[0];

    return (
        <>
            <h1>Modificar ítem del menú</h1>
            <MenuItemForm menuItemId={menuItemId} />
        </>
    );
};

export default EditMenuPage;