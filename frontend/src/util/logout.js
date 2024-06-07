import devLog from "./devLog";

/**
 * Clears token data
 * @param { { redirect: boolean | string } } options
 */
const logout = (options) => {
    if (sessionStorage.getItem('token')) {
        sessionStorage.removeItem('token');
        devLog('TOKEN CLEARED');
    }

    if (options.redirect) {
        devLog('LOGOUT REDIRECT');
        location.assign(options.redirect);
    }
};

export default logout;