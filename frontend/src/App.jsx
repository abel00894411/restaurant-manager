import { useState } from "react";
import { Outlet } from 'react-router-dom';
import Navigation from './components/Navigation';
import './App.css';

function App() {
    return (
        <div className="layout-container">
            <Navigation />
            <Outlet />
        </div>
    )
}

export default App;
