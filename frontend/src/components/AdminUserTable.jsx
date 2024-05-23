import { useState, useEffect } from "react";
import fetchAPI from '../util/fetchAPI';
import devLog from "../util/devLog";
import TinyButton from "./TinyButton";

const AdminUserTable = () => {
    const [users, setUsers] = useState(undefined);

    useEffect(() => {
        fetchAPI('usuarios')
            .then(res => {
                setUsers(res.usuarios);
            })
            .catch(error => {
                devLog(`Unable to get user list: ${error.message}`);
                setUsers([]);
            });
    }, []);

    if (!users) {
        return <>Cargando...</>
    }

    if (users.length == 0) {
        return <>Sin usuarios para mostrar</>;
    }

    return (
        <table>
            <thead>
                <tr>
                    <th>Nombre</th>
                    <th>RFC</th>
                    <th>Sueldo</th>
                    <th>Puesto</th>
                    <th>Fecha de contratación</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                { users.map((u, i) => (
                    <tr key={i}>
                        <td>{`${u.nombre} ${u.apellido}`}</td>
                        <td>{u.rfc}</td>
                        <td>${u.sueldo}</td>
                        <td>{u.puesto}</td>
                        <td>{u.fechaContratacion}</td>
                        <td>
                            <TinyButton icon="edit" redirect={`/usuarios/editar/${u.id}`}/>
                        </td>
                    </tr>
                )) }
            </tbody>
        </table>
    );
};

export default AdminUserTable;