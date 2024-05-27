import IActivityLog from "../interfaces/IActivityLog";
import IAssignedOrderEvent from "../interfaces/IAssignedOrderEvent";

/**
 * Stores activity history for a waiter user
 */
class ActivityHistory {
    #activityList: IActivityLog[] = [];

    constructor() {
        // TODO: Listen to more types of events and add format to messages
        
        document.addEventListener('assignedOrder', (event) => {
            const { order } = (event as IAssignedOrderEvent).detail;
            this.#activityList.push({ log: `Se te ha asignado una nueva orden #${order.id}`, date: new Date() });
        });

    }

    getRecent(): IActivityLog[] {
        return [ ...this.#activityList ].reverse();
    }

    push(activity: IActivityLog) {
        this.#activityList.push(activity);
    }
}

const activityHistory = new ActivityHistory();

export { activityHistory };