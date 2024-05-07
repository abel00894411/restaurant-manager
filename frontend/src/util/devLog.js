const { DEV } = import.meta.env;

/**
 * Logs a data to console only under development mode
 * @param {*} data The data to log
 */
const devLog = (data) => {
    if (DEV) {
        console.log(data);
    }
};

export default devLog;