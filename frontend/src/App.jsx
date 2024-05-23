import { Outlet } from 'react-router-dom';
import Navigation from './components/Navigation';
import { jobManager } from './util/jobManager';
import './App.css';

function App({ token, paths }) {
    const className = token ? 'employee-layout' : 'public-layout';

    const content = (token) ?
        <> 
            <Navigation paths={paths} />
            <main>
                <Outlet /> 
            </main>
        </> 
    : 
        <main>
            <Outlet />
        </main>
    ;

    return (
        <div className={className} >
            { content }
        </div>
    );
}

export default App;
