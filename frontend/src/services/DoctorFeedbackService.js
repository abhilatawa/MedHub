import API from "../api"; // Importing API module

export const getDoctorFeedback = async (data) => {

    // Making a GET request to '/doctor/feedback' endpoint to return all feedback for the doctor
    const res = await API.get(`/doctor/feedback`)
        .then((res) => { // Handling successful response
            // Creating a response object with message and status extracted from the response data
            const response = {
                token: res.data.responseData,
                message: res.data.message,
                isSuccess: res.data.isSuccess
            };
            return response; // Returning the response object
        })
        // Handling errors
        .catch((err) => {
            console.log(err);
            return err;
        });
    console.log(res);
    return res; // Returning the response
};