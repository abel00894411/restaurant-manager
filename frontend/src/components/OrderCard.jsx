import { menu } from '../models/Menu';
import { Link } from 'react-router-dom';
import './OrderCard.css';

// TODO: Add EventListener for updating the card when the order changes

const OrderCard = ({ order }) => {
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

    return (
        <Link to={`/ordenes/${order.id}`} className='orderCardLink' >
            <div className='orderCard'>
                <div className="orderCard__details">
                    <p className="orderCard__details__id">Orden #{order.id}</p>
                    <p className="orderCard__details__time">{`${fHour}:${fMin}${amPm}`}</p>
                </div>

                <table className='orderCard__items'>
                    <thead>
                        <tr>
                            <th>Ítem</th>
                            <th>Cantidad</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {order.getItems().map((item, i) => {
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

                                // TODO: different color for each icon
                            return (
                                <tr key={i}>
                                    <td>{menu.getItem(item.menuItemId).name}</td>
                                    <td>{item.quantity}</td>
                                    <td><span className="material-symbols-outlined" style={{ color: color }}>{icon}</span></td>
                                </tr>
                            );
                        })}
                    </tbody>
                </table>

                <div className="orderCard__footer">
                    <p className="orderCard__footer__state">{order.state}</p>
                    <p className="orderCard__footer__legend">click para ver más detalles</p>
                </div>
            </div>
        </Link>
    );
};

export default OrderCard;