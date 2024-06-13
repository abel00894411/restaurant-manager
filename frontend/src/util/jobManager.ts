import OrderManager from '../models/OrderManager';
import KitchenManager from "../models/KitchenManager";
import getTokenData from "./getTokenData";

const token = getTokenData();
const userType = token?.tipo;

const jobManager = (
    userType == 'MESERO' ? new OrderManager()
    : userType == 'COCINERO' ? new KitchenManager() 
    : undefined
);

export { jobManager };