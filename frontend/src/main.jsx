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
        element: <Waiter.Dashboard />
    },
    {
        path: '/panel',
        element: <Waiter.Dashboard />
    }
];

const cookPaths = [
    {
        path: '/',
        element: <Cook.Dashboard />
    },
    {
        path: '/panel',
        element: <Cook.Dashboard />
    }
];

const adminPaths = [
    {
        path: '/',
        element: <Admin.Dashboard />
    },
    {
        path: '/panel',
        element: <Admin.Dashboard />
    }
];

const customerPaths = [
    {
        path: '/',
        element: <h1>Public index</h1>
    },
    {
        path: '/menu',
        element: <Customer.MenuPage />
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
        element: <App token={getTokenData()} />,
        errorElement: <h1>Error de ruta</h1>,
        children: paths
    }
]);

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <RouterProvider router={router} />
    </React.StrictMode>,
)
