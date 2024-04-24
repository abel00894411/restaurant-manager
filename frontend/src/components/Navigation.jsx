import { useState } from 'react';
import { useLocation, Link } from 'react-router-dom';
import './Navigation.css';

const Navigation = ({ paths }) => {
    let options = [];

    for (const path of paths) {
        const option = options.find(opt => path.display == opt.display);

        if (!option) {
            path.equivalentPaths = [ path.path ];
            options.push(path);
        } else {
            option.equivalentPaths.push(path.path);
        }
    }

    const optClassName = 'navigation__options__option';
    const currentOptClassName = optClassName + '--current';

    const location = useLocation().pathname;

    return (
        <nav className="navigation">
            <div className="navigation__logo-container">
                <div className="navigation__logo-container__logo"></div>
            </div>

            <nav className="navigation__options">
                {
                    options.map((opt, i) =>
                        <Link 
                            key={i}
                            className={opt.equivalentPaths.includes(location) ? currentOptClassName : optClassName}
                            to={opt.path}
                        >
                            {opt.display}
                        </Link>
                    )
                }
            </nav>
        </nav>
    );
}

export default Navigation;