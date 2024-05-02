import API from "../api"; // Importing API module

export const getDoctorBlogs = async (data) => {

    // Making a GET request to '/blogs/get-blogs' endpoint to return all blogs from the doctor
    const res = await API.get(`/blogs/get-blogs`)
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

export const getPatientBlogs = async (data) => {

    // Making a GET request to '/blogs/get-patient-blogs' endpoint to return all blogs for the selected doctor
    const res = await API.get(`/blogs/get-patient-blogs?doctorId=` + data)
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

// Making a POST request to '/blogs/create-blog' endpoint to return all blogs from the doctor
export const createBlog = async (data) => {
    const res = await API.post(`/blogs/create-blog`, data)
        .then((res) => {
            // Fetching the response.
            const response = {
                isSuccess: res.data.isSuccess,
                message: res.data.message,
                status: "error",
            };

            if (response["isSuccess"]) {
                response["message"] = "Blog posted successfully";
                response["status"] = "success";
            }
            return response;
        })
        //Handling errors
        .catch((err) => {
            console.log(err);
            return err;
        });
    console.log(res);
    return res; // Returning the response
}