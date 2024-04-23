const logout = () => {
    localStorage.removeItem('token');
    location.assign('/');
    console.log('LOGGED OUT');
};

export default logout;