const getTokenData = () => {
    try {
        return JSON.parse(localStorage.getItem('token'));
    } catch(err) {
        return false;
    }
};

export default getTokenData;