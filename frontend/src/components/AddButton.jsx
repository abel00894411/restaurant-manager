import { Link } from 'react-router-dom';
import './AddButton.css';

const AddButton = ({ children, onClick = () => {}, disabled = false, redirect = '' }) => {
    let res = (
        <>
            <button type="button" disabled={disabled} onClick={onClick} className="addButton">
                {children}
                <span className="material-symbols-outlined">
                    add_box
                </span>
            </button>
        </>
    );

    if (redirect) {
        res = (
            <Link className="addButtonLink" to={redirect}>
                {res}
            </Link>
        );
    }

    return res;
};

export default AddButton;