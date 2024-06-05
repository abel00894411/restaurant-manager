import { useState, useEffect } from 'react';
import OrderItem from '../models/OrderItem';
import SmallButton from './SmallButton';
import { menu } from '../models/Menu';
import useEventListener from '../hooks/useEventListener';
import './OrderItemCard.css';

/**
 * Visually displays an order item to the cook user
 * @param { { orderItem: OrderItem, inPreparation: boolean } } props
 */
const OrderItemCard = ({ orderItem, onClick = ()=>{}, inPreparation = false }) => {
    if (!(orderItem instanceof OrderItem)) {
        throw new Error(`Unable to instanciate OrderItemCard component: ${orderItem} is not an instance of OrderItem`);
    }
    
    const resizeBreakpoint = 500;

    const buttonSmallSize = 32;
    const buttonBigSize = 64;
    const initialInPreparationButtonSize = window.innerWidth < resizeBreakpoint ? buttonSmallSize : buttonBigSize;

    const timeLabelColorDuration = 60 * 10; // In seconds
    const timeLabelColorUpdateIntervalDelay = 10; // In seconds

    const [ buttonSize, setButtonSize ] = useState(inPreparation ? initialInPreparationButtonSize : buttonSmallSize);
    const [ timeLabelCompact, setTimeLabelCompact ] = useState(window.innerWidth < resizeBreakpoint);
    const [ timeLabelColor, setTimeLabelColor ] = useState('var(--gray)');

    const onResize = (event) => {
        const onSmallScreen = window.innerWidth < resizeBreakpoint;
        let newSize = onSmallScreen ? buttonSmallSize : buttonBigSize;
        setButtonSize(newSize);
        setTimeLabelCompact(onSmallScreen);
    };

    const updateTimeLabelColor = () => {
        const cardLifetime = (Date.now() - date.getTime()) / 1000; // In seconds
        let color = 'var(--gray)';
        
        if (cardLifetime > timeLabelColorDuration) {
            color = '#ECC706'; // Yellow
        }

        if (cardLifetime > timeLabelColorDuration*2) {
            color = '#c0392b'; // Red
        }

        setTimeLabelColor(color);
    };

    if (inPreparation) {
        useEventListener('resize', onResize, window);
    }

    useEffect(() => {
        const timeLabelColorUpdateInterval = setInterval(updateTimeLabelColor, timeLabelColorUpdateIntervalDelay * 1000);

        return () => clearInterval(timeLabelColorUpdateInterval)
    }, []);


    const parentClass = inPreparation ? 'orderItemCard--in-preparation' : 'orderItemCard';

    const date = orderItem.creationDateTime;
    const hour = date.getHours();
    const min = date.getMinutes();
    const fHour = (hour == 0) ? 12
                : (hour > 12) ? hour - 12
                : hour;
    const fMin = (min < 10) ? `0${min}` : min;
    const amPm = (hour > 11) ? 'p.m.' : 'a.m.';

    const formattedTime = `${fHour}:${fMin} ${amPm}`;

    return (
        <div className={parentClass}>
            
            <div className={`${parentClass}__details`}>

                <p className={`${parentClass}__details__name`}>
                    {orderItem.quantity} {menu.getItem(orderItem.menuItemId).name}
                    { inPreparation && <span className="material-symbols-outlined">skillet</span> }
                </p>

                <p className={`${parentClass}__details__time`} style={{ color: timeLabelColor }}>
                    <span className="material-symbols-outlined">schedule</span>
                    { (timeLabelCompact || !inPreparation) ?
                        <>{formattedTime}</>
                        :
                        <>Ordenado a las {formattedTime}</>
                    }
                </p>
            </div>

            <SmallButton className={`${parentClass}__button`} icon={inPreparation ? 'check' : 'cooking'} size={buttonSize} onClick={onClick} />
        </div>
    );
};

export default OrderItemCard;