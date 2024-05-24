import { useState } from 'react';
import './DateSelector.css';

const DateSelector = ({ initialDate = new Date(), onSelection = (date) => {} }) => {
    const [date, setDate] = useState(initialDate);
    const day = date.getDate();
    const fday = day < 10 ? `0${day}` : day;
    const month = date.getMonth() + 1;
    const fmonth = month < 10 ? `0${month}` : month;
    const year = date.getFullYear();

    const onClick = () => {
        const res = prompt(`Escribe una nueva fecha en formato DD-MM-YYYY`);
        if (!res) {
            return;
        }

        const processedRes = res.replaceAll('/', '-');
        const [d, m, y] = processedRes.split('-');
        const newDate = new Date(y, m-1, d);
        
        if (isNaN(newDate)) {
            alert(`La fecha introducida no es válida`);
            return;
        }

        setDate(newDate);
        onSelection(newDate);
    }

    return (
        <span className="dateSelector" onClick={onClick}>
            {fday}/{fmonth}/{year}
        </span>
    );
};

export default DateSelector;