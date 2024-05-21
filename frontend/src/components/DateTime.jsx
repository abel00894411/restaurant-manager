import { useState } from 'react';
import Clock from './Clock';
import './DateTime.css';

const monthNames = ['enero', 'febrero', 'marzo', 'abril', 'mayo', 'junio', 'julio', 'agosto', 'septiembre', 'octubre', 'noviembre', 'diciembre'];

const DateTime = () => {
    const getDate = () => {
        const now = new Date();
        const day = now.getDate();
        const month = now.getMonth();
        const year = now.getFullYear();
        const monthName = monthNames[month];
        return `${day} de ${monthName} de ${year}`;
    };

    const [date, setDate] = useState(getDate());

    return (
        <div className="date-time">
            <div className="date-time__date">
                {date}    
            </div>
            <div className="date-time__time">
                <Clock onUpdate={() => setDate(getDate())} />
            </div>
        </div>
    );
};

export default DateTime;