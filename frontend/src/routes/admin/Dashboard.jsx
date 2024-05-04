import Count from "../../components/Count";
import DateTime from "../../components/DateTime";
import './Dashboard.css';

const Dashboard = () => {
    return (
        <main>
            <DateTime />

            <div className="employees shadow">
                <h3>Empleados registrados</h3>
                
                <div className="employees__counts">
                    <Count title="Meseros">...</Count>
                    <Count title="Cocineros">...</Count>
                    <Count title="Administradores">...</Count>
                </div>
            </div>

            <div className="orders shadow">
                <h3>Órdenes</h3>

                <div className="orders__counts">
                    <Count title="Activas">...</Count>
                    <Count title="Despachadas hoy">...</Count>
                </div>
            </div>
        </main>
    );
};

export default Dashboard;