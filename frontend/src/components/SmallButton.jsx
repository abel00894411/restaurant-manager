import './SmallButton.css';

const SmallButton = ({ icon, onClick = ()=>{}, size = 24 }) => {
    const buttonStyle = {
        width: `${size}px`,
        height: `${size}px`
    }

    const iconSize = size * 0.7;

    const iconStyle = {
        fontSize: `${iconSize}px`,
    };

    let iconCode;
    switch (icon) {
        case 'check':
            iconCode = 'check';
            iconStyle.color = 'var(--primary)';
            iconStyle.fontWeight = 'bold';
            break;
        case 'cooking':
            iconCode = 'skillet';
            iconStyle.color = 'var(--gray)';
            break;
        default:
            iconCode = '?';
    }

    return (
        <button onClick={onClick} style={buttonStyle} className='smallButton'>
            <span className="material-symbols-outlined" style={iconStyle}>{iconCode}</span>
        </button>
    );
};

export default SmallButton;