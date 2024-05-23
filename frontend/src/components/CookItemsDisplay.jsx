import { useState } from 'react';
import { jobManager } from '../util/jobManager';
import OrderItemCard from './OrderItemCard';
import OrderItem from '../models/OrderItem';
import useEventListener from '../hooks/useEventListener';
import devLog from '../util/devLog';
import './CookItemsDisplay.css';

const CookItemsDisplay = () => {
    // necesita soportar dos casos: cuando la lista ya se obtuvo y cuando todavia no se tiene respuesta de listedOrderItems
    // Error cuando se actualiza el item en preparacion

    const items = jobManager.getAll();
    const [itemsQueue, setItemsQueue] = useState(items);
    const [currentItemInPreparationId, setItemInPreparationId] = useState(items[0]?.id);
    
    const selectItem = (orderItem) => {
        if (itemsQueue.length == 0) {
            return;
        }

        if ( !(orderItem instanceof OrderItem) ) {
            return;
        }

        setItemInPreparationId(orderItem.id);
    };

    const finishItem = (orderItem) => {
        if ( !(orderItem instanceof OrderItem) ) {
            devLog(`Error: Se intentó finalizar item ${orderItem}`);
            return;
        }
        
        const newItem = new OrderItem(orderItem.id, undefined, undefined, 'PREPARADO');
        jobManager.set(newItem);
    }

    if (!currentItemInPreparationId && itemsQueue.length > 0) {
        setItemInPreparationId(itemsQueue[0].id);
    }


    const onListedOrderItems = (event) => {
        const detail = (event).detail;
        const { items } = detail;
        
        const itemInPreparation = items.find(item => item.id == currentItemInPreparationId);

        if (!itemInPreparation) {
            setItemInPreparationId(undefined);
        }

        setItemsQueue(items);
    };

    const onAssignedOrderItems = (event) => {
        jobManager.sendMessage('/app/items/enlistar');
    };

    const onUpdatedOrderItems = (event) => {
        jobManager.sendMessage('/app/items/enlistar');
    };

    useEventListener('listedOrderItems', onListedOrderItems);
    useEventListener('assignedOrderItem', onAssignedOrderItems);
    useEventListener('updatedOrderItem', onUpdatedOrderItems);


    /*
        FIX: currentItemInPreparation will be undefined when following the next steps:
        Having at least two items in itemsQueue, item index [0] will be 'A' and some other item will be 'B'.
        1. Mount the component, so A is in preparation and B in queue
        2. Click B to mark it as in preparation
        3. Unmount the component by going to other route
        4. Mount the component by returning to the initial route, A will be in preparation and B in queue
        5. Click B to mark it as in preparation
        6. Click the finish button on B to finish it
        7. When the component rerenders, currentItemInPreparation will be undefined, thus "No ordenes para realizar"
        will be displayed
    */
    let currentItemInPreparation = itemsQueue.find(item => item.id == currentItemInPreparationId);

    // This is a hotfix for the undefined currentItemInPreparation bug, its broken as well as it duplicates
    // the item with index [0].
    if (!currentItemInPreparation && itemsQueue.length > 0) {
        currentItemInPreparation = itemsQueue[0];
    }

    if (currentItemInPreparation) {

        const pendingItems = itemsQueue.filter(item => item.id != currentItemInPreparationId);

        return (
            <>
                <h1>Preparando</h1>
                <OrderItemCard orderItem={currentItemInPreparation} inPreparation={true} onClick={() => { finishItem(currentItemInPreparation) }} />
                <h3>Items en cola</h3>
                <div className="cookItemsDisplay__pending">
                    { pendingItems.map((item, i) => {
                        return (
                            <OrderItemCard
                                key={i}
                                orderItem={item} 
                                onClick={() => selectItem(item)}
                            />
                        )
                    })
                    }
                </div>
            </>
        );

    } else {

        return (
            <>
                <h3>Sin ordenes por realizar</h3>
            </>
        );

    }
}

export default CookItemsDisplay;