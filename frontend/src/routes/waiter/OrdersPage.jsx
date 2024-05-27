import AddButton from '../../components/AddButton';
import OrderCard from '../../components/OrderCard';
import Order from '../../models/Order';
import { jobManager } from '../../util/jobManager';
import { orderEditorManager } from '../../models/OrderEditorManager';
import { Navigate } from 'react-router-dom';
import './OrdersPage.css';


import OrderItem from '../../models/OrderItem';
import { useState } from 'react';
window.jobManager = jobManager; /////////////
window.Order = Order; ////
window.OrderItem = OrderItem; ///////
window.orderEditorManager = orderEditorManager;

const onAddButtonClick = (setTriggerUpdate) => {
    orderEditorManager.startNewOrder();
    setTriggerUpdate(old => old+1);
};

const OrdersPage = () => {
    const [triggerUpdate, setTriggerUpdate] = useState(0);
    
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