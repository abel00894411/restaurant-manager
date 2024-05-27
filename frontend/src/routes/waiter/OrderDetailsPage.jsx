import { useLocation } from "react-router-dom";
import { useState } from "react";
import OrderTable from "../../components/OrderTable";
import { jobManager } from "../../util/jobManager";
import Order from "../../models/Order";
import useEventListener from "../../hooks/useEventListener";
import { Navigate } from "react-router-dom";
import './OrderDetailsPage.css';

const currencyFormatter = new Intl.NumberFormat('es-MX', { style: 'currency', currency: 'MXN', maximumFractionDigits: 0 });

const OrderDetailsPage = () => {
    const url = useLocation().pathname;
    const orderId = url.split('/').reverse()[0];
    const order = jobManager.get(Number(orderId), Order);
    
    if (!order) {
        return (
            <>Sin datos para la orden</>
        );
    }

    const onFinishButtonClick = () => {
        const newOrder = new Order(orderId, undefined, 'DESPACHADA');
        jobManager.set(newOrder);
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

    const [triggerUpdate, setTriggerUpdate] = useState(0);
    const [redirect, setRedirect] = useState(undefined);

    useEventListener('updatedOrderItem', () => setTriggerUpdate(old => old+1));
    useEventListener('finishedOrder', () => setTimeout(()=>setRedirect('/ordenes'), 500)); // Temporal fix (the timeout)

    return (
        <>
            { redirect && <Navigate to={redirect} /> }

            <div className="orderDetailsPage__title">
                <h1>Orden #{order.id}</h1>
                <p className="orderDetailsPage__title__time">{`${fHour}:${fMin}${amPm}`}</p>
            </div>

            <h3 className="orderDetailsPage__state">{formattedState}</h3>

            <OrderTable order={order} />

            <div className="orderDetailsPage__subtotal">
                <p className="orderDetailsPage__subtotal__title">Subtotal (sin IVA)</p>
                <p className="orderDetailsPage__subtotal__quantity">{currencyFormatter.format(order.getSubtotal())}</p>
            </div>

            { order.getItems().every(item => item.state == 'SERVIDO') &&
                <button type="button" className="orderDetailsPage__finish-order-button" onClick={onFinishButtonClick}>Terminar orden</button>
            }
        </>
    );
};

export default OrderDetailsPage;