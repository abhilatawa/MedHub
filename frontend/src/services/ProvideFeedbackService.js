import API from "../api";

export const provideFeedback = async (data) => {
    const res = await API.patch(`/patient/appointment/savefeedback`,data)
        .then((res) => {
            // fetch response.
            const response = {
                token: res.data.responseData,
                message: res.data.message,
                isSuccess: res.data.isSuccess,
                success: res.data.success,
            };
            console.log(response);
            return response;
        })
        .catch((err) => {
            console.log(err);
            return err;
        });
    return res;
};