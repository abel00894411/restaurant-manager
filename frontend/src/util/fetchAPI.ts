/// <reference types="vite/client" />

import devLog from "./devLog";

const base = import.meta.env.VITE_API_URL;
const token = localStorage.getItem('token');

if (!token) {
    devLog('fetchAPI module has been initialized with no token');
}

/**
 * Fetches resources from the REST API and parses JSON responses, it includes the
 * access token on the request's Authorization header
 * @param path The path for the resource (excluding the base URL)
 * @param method The HTTP method
 * @returns The response as an object
 */
const fetchAPI = async (path: string, method: string = 'GET', body: object | undefined = undefined, options = {}): Promise<object> => {
    if (!base) {
        throw new Error("Can't use fetchAPI() because the env variable VITE_API_URL is not defined");
    }

    const cleanPath = path.replace(/^\//, '');

    const headers = {
        'Content-Type': 'application/json'
    };

    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }

    const completeOptions = { 
        ...options,
        method: method.toUpperCase(),
        headers
    };
    
    if (body) {
        completeOptions['body'] = JSON.stringify(body);
    }

    const res = await fetch(`${base}/${cleanPath}`, completeOptions);

    if (res.ok) {
        const json = await res.json();
        return json;
    }

    throw new Error(`Unsuccessful request: ${res.status} ${res.statusText}`);
}

export default fetchAPI;