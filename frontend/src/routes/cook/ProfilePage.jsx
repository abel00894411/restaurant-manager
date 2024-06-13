import UserForm from "../../components/UserForm";
import getTokenData from '../../util/getTokenData';

const token = getTokenData();
const userId = token.idUsuario;

const ProfilePage = () => {
    return (
        <>
            <h1>Configuración de usuario</h1>

            <UserForm userId={userId} passwordOnly={true} />
        </>
    );
}

export default ProfilePage;