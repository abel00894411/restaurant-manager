/**
 * @returns { object | false }
 */
const getTokenData = () => {
    try {
        const encodedToken = sessionStorage.getItem('token');
        const payload = encodedToken.split('.')[1];
        const decodedPayload = atob(payload);
        const tokenData = JSON.parse(decodedPayload);
        return tokenData;
    } catch(err) {
        return false;
    }
};

export default getTokenData;