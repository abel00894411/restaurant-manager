import { useState, useEffect } from 'react';
import './Count.css';

/**
 * @param { { title: string, children: *, onMount: function(setContent, setCountTitle)} }
 */
const Count = ({ title, children, onMount }) => {
    const [content, setContent] = useState(children);
    const [countTitle, setCountTitle] = useState(title);

    useEffect(
        () => {
            if (typeof onMount == 'function') {
                onMount(setContent, setCountTitle);
            }
        },
        []
    );

    return (
        <div className="count">
            {countTitle && <div className="count__title">{countTitle}</div>}
            <div className="count__content">
                {content}
            </div>
        </div>
    );
}

export default Count;