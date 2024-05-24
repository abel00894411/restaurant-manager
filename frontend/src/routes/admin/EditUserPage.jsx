import { useLocation } from "react-router-dom";
import UserForm from "../../components/UserForm";
import getTokenData from "../../util/getTokenData";

const EditUserPage = () => {
    const url = useLocation().pathname;
    const userId = url.split('/').reverse()[0];

    const exclude = [];

    if (getTokenData().idUsuario != userId) {
        exclude.push('password');
        exclude.push('logout');
    }

    return (
        <>
            <h1>Modificar usuario</h1>

            <UserForm userId={userId} exclude={exclude}/>
        </>
    );
};

export default EditUserPage;