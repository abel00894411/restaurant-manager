import IActivityLog from "../interfaces/IActivityLog";

/**
 * Stores activity history for a waiter user
 */
class ActivityHistory {
    #activityList: IActivityLog[] = [
        { log: 'Hola', date: new Date()},
        { log: 'Holaa', date: new Date()},
        { log: 'Holaaa', date: new Date()},
        { log: 'Holaaaa', date: new Date()},
        { log: 'Holaaaaa', date: new Date()}
    ];

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