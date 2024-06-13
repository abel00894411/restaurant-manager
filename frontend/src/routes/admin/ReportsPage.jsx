import { useState } from 'react';
import SalesReport from "../../components/SalesReport";
import DateSelector from '../../components/DateSelector';

const ReportsPage = () => {
    const [startDate, setStartDate] = useState(new Date());
    const [endDate, setEndDate] = useState(new Date());

    return (
        <>
            <h1>Informe de ventas</h1>

            <div className="dateIntervalText">
                Desde el <DateSelector date={startDate} onSelection={setStartDate}/>
                hasta el <DateSelector date={endDate} onSelection={setEndDate}/>
            </div>

            <SalesReport startDate={startDate} endDate={endDate} />
        </>
    );
}

export default ReportsPage;