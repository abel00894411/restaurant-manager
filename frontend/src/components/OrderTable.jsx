import { Link } from "react-router-dom";
import { orderEditorManager } from "../models/OrderEditorManager";
import { menu } from '../models/Menu';
import SmallButton from './SmallButton';
import OrderItem from "../models/OrderItem";
import { jobManager } from "../util/jobManager";
import useEventListener from '../hooks/useEventListener';
import './OrderTable.css';
import { useState } from "react";

// TODO: Add EventListener for updating the table when the order changes

const finishItem = (item) => {
    const copy = new OrderItem(item.id, undefined, undefined, 'SERVIDO');
    jobManager.set(copy);
};

const OrderTable = ({ order }) => {
    const [itemList, setItemList] = useState(order.getItems());

    useEventListener('updatedOrderItem', (event) => {
        const { item } = event.detail;

        setItemList(oldList => {
            let index;

            for (let i = 0; i < oldList.length; i++) {
                if (oldList[i].id == item.id) {
                    index = i;
                    break;
                }
            }

            if (index === undefined) {
                return oldList;
            }

            oldList[index].state = item.state;
            return [...oldList];
        });
    });

    return (
        <table className="orderTable">
            <thead>
                <tr>
                    <th>Ítems</th>
                    <th>Precio unitario</th>
                    <th>Cantidad</th>
                    <th>Precio total</th>
                    <th>Estado</th>
                </tr>
            </thead>
            <tbody>
                {itemList.map((item, i) => {
                    return (
                        <tr key={i}>
                            <td>{menu.getItem(item.menuItemId).name}</td>
                            <td>{menu.getItem(item.menuItemId).price}</td>
                            <td>{item.quantity}</td>
                            <td>{item.getCost()}</td>
                            <td>
                                {(()=>{
                                    const icon = 
                                    item.state == 'PENDIENTE' ? 'skillet' :
                                    item.state == 'PREPARADO' ? 'restaurant' :
                                    item.state == 'SERVIDO' ? 'check'
                                    : '';

                                    const color = 
                                        item.state == 'PENDIENTE' ? 'var(--gray)' :
                                        item.state == 'PREPARADO' ? 'var(--tertiary)' :
                                        item.state == 'SERVIDO' ? 'var(--primary)'
                                    : '';

                                    return (
                                        <span className="material-symbols-outlined" style={{ color: color }}>{icon}</span>
                                    );
                                })()}
                                { item.state == 'PREPARADO' &&
                                    <SmallButton className="orderTable__finishBtn" size={40} icon={'check'} onClick={()=>{finishItem(item)}}/>
                                }
                            </td>
                        </tr>
                    );
                })}
            </tbody>
            <tfoot>
                <tr>
                    <td colSpan={999}>
                        <Link to="/menu" onClick={() => { orderEditorManager.selectOrder(order.id) }}>
                            Agregar ítems <span className="material-symbols-outlined">add</span>
                        </Link>
                    </td>
                </tr>
            </tfoot>
        </table>
    );
};

export default OrderTable;