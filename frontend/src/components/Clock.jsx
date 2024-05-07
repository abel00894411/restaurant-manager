import { useState, useEffect } from 'react';
import devLog from '../util/devLog';
import './Clock.css';

const Clock = ({ onUpdate }) => {
    const getTime = () => {
        const now = new Date();
        const hour = now.getHours();
        const min = now.getMinutes()
        const sec = now.getSeconds();

        const fHour = (hour == 0) ? 12
                    : (hour > 12) ? hour - 12
                    : hour;
        const fMin = (min < 10) ? `0${min}` : min;
        const fSec = (sec < 10) ? `0${sec}` : sec;
        const amPm = (hour > 11) ? 'p.m.' : 'a.m.';

        return `${fHour}:${fMin}:${fSec} ${amPm}`;
    };

    const [ time, setTime ] = useState(getTime());
    
    useEffect(() => {
        devLog('Clock started');
        
        const interval = setInterval(() => {
            setTime(getTime());
            
            if (typeof onUpdate == 'function') {
                onUpdate();
            }
            
        }, 1000);

        return () => {
            clearInterval(interval);
            devLog('Clock stoped');
        };

    }, []);
    

    return (
        <div className="clock">{time}</div>
    )
}

export default Clock;