import './RecentActivityBox.css';

const ActivityHistory = ({ historyList = [], height = 240 }) => {
    if (historyList.length == 0) {
        return <>Sin actividad para mostrar</>;
    }

    return (
        <div className="activityHistory" style={ { height: `${height}px` } }>
            {historyList.map((log, i) => {
                const { date } = log;
                const hour = date.getHours();
                const min = date.getMinutes();
                const sec = date.getSeconds();
        
                const fHour = (hour == 0) ? 12
                            : (hour > 12) ? hour - 12
                            : hour;
                const fMin = (min < 10) ? `0${min}` : min;
                const fSec = (sec < 10) ? `0${sec}` : sec;
                const amPm = (hour > 11) ? 'p.m.' : 'a.m.';
                const ftime = `${fHour}:${fMin}${amPm}`;

                return (
                    <div className="activityHistory__row" key={i}>
                        <div className="activityHistory__row__text">{log.log}</div>
                        <div className="activityHistory__row__time">{ftime}</div>
                    </div>
                );
            })}
        </div>
    );
};

export default ActivityHistory;