import './TinyButton.css';
import { Link } from 'react-router-dom';

const TinyButton = ({icon, onClick = () => {}, disabled = false, redirect = ''}) => {
    let res = (
        <button className='tinyButton' disabled={disabled} onClick={onClick}>
            {icon && <span className="material-symbols-outlined">{icon}</span>}
        </button>
    );

    if (redirect) {
        res = (
            <Link className='tinyButtonLink' to={redirect}>
                {res}
            </Link>
        );
    }

    return res;
};

export default TinyButton;