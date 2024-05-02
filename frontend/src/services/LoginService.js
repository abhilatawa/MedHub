import Cookies from 'js-cookie';
import API from '../api'; // Importing API module

export const Login = async (data) => {

    // Making a POST request to '/auth/signin' endpoint with the provided data

    const res = await API.post(`/auth/signin`, data)
        .then((res) => { // Handling successful response

            // Creating a response object with message and status extracted from the response data

            const response = {
                token: res.data.responseData,
                message: res.data.message,
                status: res.data.isSuccess
            };
            // console.log(response);
            return response; // Returning the response object
        })

        // Handling errors
        .catch((err) => {
            console.log(err);
            return err;
        });

    return res; // Returning the response
};


export const Logout = () => {

    // remove accessToken from the cookie
    const token = Cookies.get("accessToken");
    console.log(token);
    Cookies.remove("accessToken");
    if(Cookies.get("accessToken") === undefined) {
        return true;
    }
    return false;
}