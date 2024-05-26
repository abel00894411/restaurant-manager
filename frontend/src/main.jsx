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
import { menu } from './models/Menu';

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
        path: '/ordenes/:idOrden',
        element: <Waiter.OrderDetailsPage />
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
        display: 'Cocina'
    },
    {
        path: '/panel',
        element: <Cook.Dashboard />,
        display: 'Cocina'
    },
    {
        path: '/menu',
        element: <Cook.MenuPage />,
        display: 'Menú'
    },
    {
        path: '/perfil',
        element: <Cook.ProfilePage />,
        display: 'Perfil'
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
    },
    {
        path: '/usuarios',
        element: <Admin.UsersPage />,
        display: 'Usuarios'
    },
    {
        path: '/usuarios/editar/:idUsuario',
        element: <Admin.EditUserPage />
    },
    {   path: '/usuarios/nuevo',
        element: <Admin.NewUserPage />
    },
    {
        path: '/menu',
        element: <Admin.MenuPage />,
        display: 'Menú'
    },
    {
        path: '/menu/editar/:idMenu',
        element: <Admin.EditMenuPage />
    },
    {
        path: 'menu/nuevo',
        element: <Admin.NewMenuItemPage />
    },
    {
        path: '/informes',
        element: <Admin.ReportsPage />,
        display: 'Informes'
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
        path: '/facturas',
        element: <Customer.InvoicePage />,
        display: 'Invoice'
    }
];

let token = getTokenData();
const userType = token?.tipo;

// Choose the correct path list for the type of user logged-in
let paths = (
    userType == 'MESERO' ? waiterPaths
    : userType == 'COCINERO' ? cookPaths
    : userType == 'ADMINISTRADOR' ? adminPaths
    : undefined
);

if (!paths) {
    console.log(token.tipo);
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

// Initiate menu before starting app
menu.onReady = () => {
    ReactDOM.createRoot(document.getElementById('root')).render(
        <React.StrictMode>
            <RouterProvider router={router} />
        </React.StrictMode>,
    );
}
