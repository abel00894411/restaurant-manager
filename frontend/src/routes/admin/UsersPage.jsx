import AdminUserTable from "../../components/AdminUserTable";
import AddButton from "../../components/AddButton";

const UsersPage = () => {
    return (
        <>
            <h1>Ver usuarios</h1>
            <AdminUserTable />
            <AddButton redirect="/usuarios/nuevo">Nuevo usuario</AddButton>
        </>
    );
};

export default UsersPage;