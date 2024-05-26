import { useLocation } from "react-router-dom";

const OrderDetailsPage = () => {
    const url = useLocation().pathname;
    const orderId = url.split('/').reverse()[0];

    return (
        <>
            Order details
        </>
    );
};

export default OrderDetailsPage;