import Count from "../../components/Count";
import DateTime from "../../components/DateTime";
import fetchAPI from '../../util/fetchAPI';
import './Dashboard.css';

const Dashboard = () => {
    const handleEmployeeCount = (setContent, userType) => {
        const userCount = {
            'MESERO': 0,
            'COCINERO': 0,
            'ADMINISTRADOR': 0
        };

        fetchAPI('usuarios')
            .then(res => {
                for (const user of res.usuarios) {
                    userCount[user.puesto]++;
                }
                setContent(userCount[userType]);
            });
    };

    const handleOrdersCount = (setContent, type) => {
        fetchAPI('ordenes')
            .then(res => {
                let orders = res.ordenes;

                switch (type) {
                    case 'active':
                        orders = orders.filter(order => order.estado == 'ACTIVA');
                        break;
                    case 'dispatched':
                        orders = orders.filter(order => order.estado == 'DESPACHADA');
                        break;
                    default:
                        orders = [];
                }

                const count = orders.length;
                setContent(count);
            });
    };

    return (
        <>
            <DateTime />

            <div className="employees shadow">
                <h3>Empleados registrados</h3>
                
                <div className="employees__counts">
                    <Count title="Meseros" onMount={(setContent) => handleEmployeeCount(setContent, 'MESERO')}>...</Count>
                    <Count title="Cocineros" onMount={(setContent) => handleEmployeeCount(setContent, 'COCINERO')}>...</Count>
                    <Count title="Administradores" onMount={(setContent) => handleEmployeeCount(setContent, 'ADMINISTRADOR')}>...</Count>
                </div>
            </div>

            <div className="orders shadow">
                <h3>Órdenes</h3>

                <div className="orders__counts">
                    <Count title="Activas" onMount={(setContent) => handleOrdersCount(setContent, 'active')}>...</Count>
                    <Count title="Despachadas hoy" onMount={(setContent) => handleOrdersCount(setContent, 'dispatched')}>...</Count>
                </div>
            </div>
        </>
    );
};

export default Dashboard;