import API from '../api'; // Importing API module

export const doctorCatalog = async (data) => {

    // Making a POST request to '/patient/get-filtered-doctor-list' endpoint with the provided data

    const res = await API.post(`/patient/get-filtered-doctor-list`, data)
        .then((res) => { // Handling successful response

            // Creating a response object with message and status extracted from the response data

            const response = {
                token: res.data.responseData,
                message: res.data.message,
                isSuccess: res.data.isSuccess
            };
            console.log(response);
            return response; // Returning the response object
        })
        // Handling errors
        .catch((err) => {
            console.log(err);
            return err;
        });

    return res; // Returning the response
};