import { useLocation } from "react-router-dom";
import OrderTable from "../../components/OrderTable";
import { jobManager } from "../../util/jobManager";
import './OrderDetailsPage.css';
import Order from "../../models/Order";

const OrderDetailsPage = () => {
    const url = useLocation().pathname;
    const orderId = url.split('/').reverse()[0];
    const order = jobManager.get(Number(orderId), Order);
    
    if (!order) {
        return (
            <>Sin datos para la orden</>
        );
    }

    const date = order.date;
    const hour = date.getHours();
    const min = date.getMinutes()
    const sec = date.getSeconds();

    const fHour = (hour == 0) ? 12
                : (hour > 12) ? hour - 12
                : hour;
    const fMin = (min < 10) ? `0${min}` : min;
    const fSec = (sec < 10) ? `0${sec}` : sec;
    const amPm = (hour > 11) ? 'p.m.' : 'a.m.';

    const formattedState = `${order.state[0].toUpperCase()}${order.state.slice(1).toLowerCase()}`;

    return (
        <>
            <div className="orderDetailsPage__title">
                <h1>Orden #{order.id}</h1>
                <p className="orderDetailsPage__title__time">{`${fHour}:${fMin}${amPm}`}</p>
            </div>

            <h3 className="orderDetailsPage__state">{formattedState}</h3>

            <OrderTable order={order} />
        </>
    );
};

export default OrderDetailsPage;