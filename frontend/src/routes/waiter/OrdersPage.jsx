import AddButton from '../../components/AddButton';
import OrderCard from '../../components/OrderCard';
import { jobManager } from '../../util/jobManager';
import { orderEditorManager } from '../../models/OrderEditorManager';
import { Navigate } from 'react-router-dom';
import { useState } from 'react';
import useEventListener from '../../hooks/useEventListener';
import './OrdersPage.css';

const onAddButtonClick = (setTriggerUpdate) => {
    orderEditorManager.startNewOrder();
    setTriggerUpdate(old => old+1);
};

const OrdersPage = () => {
    const [triggerUpdate, setTriggerUpdate] = useState(0);
    
    const forceUpdate = () => {
        setTriggerUpdate(old => old+1);
    }

    useEventListener('listedOrders', forceUpdate);
    useEventListener('assignedOrder', forceUpdate);
    useEventListener('finishedOrder', forceUpdate);

    return (
        <>
            { orderEditorManager.onEditionMode && <Navigate to='/menu' />}

            <div className="ordersPage__addButton-container">
                <AddButton onClick={() => {onAddButtonClick(setTriggerUpdate)}} >Nueva orden</AddButton>
            </div>

            <div className="ordersPage__orderCards">
                { jobManager.getAll().map((order, i) => {
                    if (order.state == 'DESPACHADA') {
                        return;
                    }

                    return (
                        <OrderCard order={order} key={i} />
                    );
                })}
            </div>
        </>
    );
};

export default OrdersPage;