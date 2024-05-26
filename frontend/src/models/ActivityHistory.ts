import IActivityLog from "../interfaces/IActivityLog";

/**
 * Stores activity history for a waiter user
 */
class ActivityHistory {
    #activityList: IActivityLog[] = [];

    constructor() {}

    getRecent(): IActivityLog[] {
        return [ ... this.#activityList ];
    }

    push(activity: IActivityLog) {
        this.#activityList.push(activity);
    }
}

const activityHistory = new ActivityHistory();

export { activityHistory };