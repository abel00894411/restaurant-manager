import { useState, useEffect } from "react";
import fetchAPI from '../util/fetchAPI';
import devLog from "../util/devLog";
import Count from '../components/Count';
import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, layouts } from 'chart.js';
import { Line } from "react-chartjs-2";
import './SalesReport.css';

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement);

const SalesReport = ({ startDate = new Date(), endDate = new Date() }) => {
    const [ordersData, setordersData] = useState(undefined);

    useEffect(() => {
        const startD = startDate.getDate();
        const fstartD = startD < 10 ? `0${startD}` : startD;
        const startM = startDate.getMonth() + 1;
        const fstartM = startM < 10 ? `0${startM}` : startM;
        const startY = startDate.getFullYear();

        const endD = endDate.getDate();
        const fendD = endD < 10 ? `0${endD}` : endD;
        const endM = endDate.getMonth() + 1;
        const fendM = endM < 10 ? `0${endM}` : endM;
        const endY = endDate.getFullYear();

        const dateMin = `${fstartD}-${fstartM}-${startY}`;
        const dateMax = `${fendD}-${fendM}-${endY}`;
        const url = `ordenes?dateMin=${dateMin}&dateMax=${dateMax}`;
        
        fetchAPI(url)
            .then(res => {
                setordersData(res.ordenes);
            })
            .catch(error => {
                setordersData(undefined);
            })
    }, [startDate, endDate]);
    
    if (!ordersData) {
        return <>Cargando...</>;
    }

    if (ordersData.length == 0) {
        return <>No hay datos para mostrar</>;
    }

    const finishedOrders = ordersData.filter(order => order.estado == 'DESPACHADA');
    const finishedOrdersCount = finishedOrders.length;
    let totalEarnings = 0;

    const dailyFinishedOrders = {};
    const dailyEarnings = {};

    for (const order of finishedOrders) {
        const date = order.fecha;
        
        if (!dailyFinishedOrders[date]) {
            dailyFinishedOrders[date] = [];
            dailyEarnings[date] = 0;
        }

        dailyFinishedOrders[date].push(date);
        dailyEarnings[date] += order.subtotal;
        totalEarnings += order.subtotal;
    }

    // Logging for debugging
    // for (const date in dailyFinishedOrders) {
    //     devLog('Number of orders per day:');
    //     devLog(`${date}: ${dailyFinishedOrders[date].length}`);
    // }

    // for (const date in dailyFinishedOrders) {
    //     devLog('Earnings per day:');
    //     devLog(`${date}: ${dailyEarnings[date]}`);
    // }

    const currencyFormatter = new Intl.NumberFormat('es-MX' , { style: 'currency', currency: 'MXN', maximumFractionDigits: 0 });
    const formattedTotalEarnings = currencyFormatter.format(totalEarnings);

    const dailyFinishedOrdersArray = [];
    for (const date in dailyFinishedOrders) {
        dailyFinishedOrdersArray.push({ date: date, value: dailyFinishedOrders[date].length });
    }

    const dailyFinishedOrdersChartData = {
        labels: dailyFinishedOrdersArray.map(item => item.date),
        datasets: [
            {
                data: dailyFinishedOrdersArray.map(item => item.value),
                borderColor: '#27ae60'
            }
        ]
    };

    const dailyEarningsArray = [];
    for (const date in dailyEarnings) {
        dailyEarningsArray.push({ date: date, value: dailyEarnings[date] });
    }

    const dailyEarningsChartData = {
        labels: dailyEarningsArray.map(item => item.date),
        datasets: [
            {
                data: dailyEarningsArray.map(item => item.value),
                borderColor: '#27ae60'
            }
        ]
    }

    return (
        <div className="salesReport">
            <Count title="Ordenes despachadas">{finishedOrdersCount}</Count>

            <h3>Ordenes despachadas por día</h3>

            <div className="salesReport__chartContainer">
                <Line data={dailyFinishedOrdersChartData} />
            </div>

            <Count title="Ingresos totales">{formattedTotalEarnings}</Count>

            <h3>Ingresos por día</h3>

            <div className="salesReport__chartContainer">
                <Line data={dailyEarningsChartData} />
            </div>
        </div>
    );
};

export default SalesReport;