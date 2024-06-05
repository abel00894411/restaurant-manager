import DateTime from '../../components/DateTime';
import RecentActivityBox from '../../components/RecentActivityBox';
import Count from '../../components/Count';
import { activityHistory } from '../../models/ActivityHistory';
import { jobManager } from '../../util/jobManager';
import './Dashboard.css';

const Dashboard = () => {
    const orders = jobManager.getAll();
    const activeOrders = orders.filter(order => order.state == 'ACTIVA');
    const finishedOrders = orders.filter(order => {
        const today = new Date();
        const currentDay = today.getDate();
        const currentMonth = today.getMonth();
        const currentYear = today.getFullYear();

        const orderDate = order.date;
        const orderDay = orderDate.getDate();
        const orderMonth = orderDate.getMonth();
        const orderYear = orderDate.getFullYear();

        if (
            order.state != 'DESPACHADA' ||
            currentDay != orderDay ||
            currentMonth != orderMonth ||
            currentYear != orderYear
        ) {
            return false;
        }

        return true;
    });

    return (
        <>
            <DateTime />

            <div className="waiterDashboard-section shadow">
                <h3>Actividad reciente</h3>
                <div className="waiterDashboard__activityBox-container">
                    <RecentActivityBox historyList={activityHistory.getRecent()} />
                </div>
            </div>
            
            <div className="waiterDashboard-section shadow">
                <h3>Órdenes</h3>
                <div className="waiterDashboard__orders">
                <Count title='Activas'>{activeOrders.length}</Count>
                <Count title='Despachadas hoy'>{finishedOrders.length}</Count>

                </div>
            </div>

            {/* TODO: Make this dynamic */}
            <div className="waiterDashboard-section shadow">
                <h3>Meseros activos</h3>
                <h4>1</h4>
            </div>
        </>
    );
};

export default Dashboard;