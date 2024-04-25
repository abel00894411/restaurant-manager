/**
 * Clears token data
 * @param { { redirect: boolean | string } } options
 */
const logout = (options) => {
    if (localStorage.getItem('token')) {
        localStorage.removeItem('token');
        console.log('TOKEN CLEARED');
    }

    if (options.redirect) {
        console.log('LOGOUT REDIRECT');
        location.assign(options.redirect);
    }
};

export default logout;