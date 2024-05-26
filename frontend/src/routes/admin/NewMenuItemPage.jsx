import MenuItemForm from "../../components/MenuItemForm";

const NewUserPage = () => {
    return (
        <>
            <h1>Crear ítem del menú</h1>
            <MenuItemForm newMenuItem={true} />
        </>
    )
};

export default NewUserPage;