import DateTime from '../../components/DateTime';
import RecentActivityBox from '../../components/RecentActivityBox';
import Count from '../../components/Count';
import { activityHistory } from '../../models/ActivityHistory';
import { jobManager } from '../../util/jobManager';
import fetchAPI from '../../util/fetchAPI';
import { useState, useEffect } from 'react';
import './Dashboard.css';
import devLog from '../../util/devLog';

const Dashboard = () => {
    const [ activeWaiters, setActiveWaiters ] = useState();

    // TODO: Show counter only after the list of orders has been received, or else it will display zero
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

    useEffect(() => {
        fetchAPI('usuarios/activos')
            .then(res => {
                const activeWaiters = res.filter(user => user.tipo == 'MESERO');
                setActiveWaiters(activeWaiters.length);
            })
            .catch(error => {
                devLog(`Unable to get active waiters: ${error.message}`);
            })
    }, []);

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

            { !!activeWaiters &&
                <div className="waiterDashboard-section shadow">
                    <h3>Meseros activos</h3>
                    <h4>{activeWaiters}</h4>
                </div>
            }
        </>
    );
};

export default Dashboard;