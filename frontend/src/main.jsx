import React from 'react';
import ReactDOM from 'react-dom/client';
import { createBrowserRouter, redirect, RouterProvider } from 'react-router-dom';
import App from './App.jsx';
import * as Waiter from './routes/waiter/index.jsx';
import * as Cook from './routes/cook/index.jsx';
import * as Admin from './routes/admin/index.jsx';
import * as Customer from './routes/customer/index.jsx';
import logout from './util/logout.js';
import getTokenData from './util/getTokenData.js';

const waiterPaths = [
    {
        path: '/',
        element: <Waiter.Dashboard />,
        display: 'Panel'
    },
    {
        path: '/panel',
        element: <Waiter.Dashboard />,
        display: 'Panel'
    },
    {
        path: '/menu',
        element: <Waiter.MenuPage />,
        display: 'Menú'
    },
    {
        path: '/ordenes',
        element: <Waiter.OrdersPage />,
        display: 'Órdenes'
    },
    {
        path: '/perfil',
        element: <Waiter.ProfilePage />,
        display: 'Perfil'
    }
];

const cookPaths = [
    {
        path: '/',
        element: <Cook.Dashboard />,
        display: 'Panel'
    },
    {
        path: '/panel',
        element: <Cook.Dashboard />,
        display: 'Panel'
    }
];

const adminPaths = [
    {
        path: '/',
        element: <Admin.Dashboard />,
        display: 'Panel'
    },
    {
        path: '/panel',
        element: <Admin.Dashboard />,
        display: 'Panel'
    }
];

const customerPaths = [
    {
        path: '/',
        element: <Customer.LoginPage />,
        display: 'Login'
    },
    {
        path: '/menu',
        element: <Customer.MenuPage />,
        display: 'Menu'
    },
    {
        path: '/factura',
        element: <Customer.InvoicePage />,
        display: 'Invoice'
    }
];

let token = getTokenData();

// Choose the correct path list for the type of user logged-in
let paths = (
    token?.tipo == 'MESERO' ? waiterPaths
    : token?.tipo == 'COCINERO' ? cookPaths
    : token?.tipo == 'ADMINISTRADOR' ? adminPaths
    : undefined
);

if (!paths) {
    logout({ redirect: false });
    token = getTokenData();
    paths = customerPaths
}

const router = createBrowserRouter([
    {
        path: '/',
        element: <App token={token} paths={paths} />,
        errorElement: <h1>Error de ruta</h1>,
        children: paths
    }
]);

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <RouterProvider router={router} />
    </React.StrictMode>,
)
