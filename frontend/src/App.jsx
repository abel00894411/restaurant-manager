import { useState } from "react";
import { Outlet } from 'react-router-dom';
import Navigation from './components/Navigation';
import './App.css';

function App({ token, paths }) {
    const className = token ? 'employee-layout' : 'public-layout';
    const content = token ? <> <Navigation paths={paths} /> <Outlet /> </> : <Outlet />;

    return (
        <div className={className} >
            { content }
        </div>
    );
}

export default App;
