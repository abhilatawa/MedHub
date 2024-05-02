import API from '../api'; // Importing API module

export const doctorSpecialization = async (data) => {

    // Making a GET request to '/auth/search-specializations' endpoint with an empty string in order to return all specializations

    const res = await API.get(`/auth/search-specializations?searchString=`+ data)
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

    return res; // Returning the response
};

export const doctorSpecializationInPatient = async (data) => {

    // Making a GET request to '/auth/search-specializations' endpoint with an empty string in order to return all specializations

    const res = await API.get(`/patient/search-specializations?searchString=`+ data)
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

    return res; // Returning the response
};