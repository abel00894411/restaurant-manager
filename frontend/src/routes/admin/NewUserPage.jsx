import UserForm from "../../components/UserForm";

const NewUserPage = () => {
    return (
        <>
            <h1>Crear usuario</h1>
            <UserForm newUser={true} exclude={['password']}/>
        </>
    )
};

export default NewUserPage;