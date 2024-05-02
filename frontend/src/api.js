import axios from "axios";
import Cookies from "js-cookie";

// set-up the basic API call with axios.
const api = axios.create({
    baseURL: `http://172.17.0.96:8080/`,
});

// Request interceptor for adding the bearer token
api.interceptors.request.use(
    (config) => {
        // if api url contains the auth do not set authorization headers
        if(!config.url.includes('/auth/')) {
            let token = Cookies.get("accessToken");
            if(token !== undefined && token !== "" && token !== null && token !== "null" ) {
                config.headers.Authorization = `Bearer ${token}`;
            }
            else {
                window.location.href = '/sign-in';
            }
        }
        
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export default api;
