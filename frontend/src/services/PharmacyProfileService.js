import API from "../api";
export const getPharmacyProfile = async () => {
    const res = await API.get(`/pharmacist/profile`)
        .then((res) => {
            // fecth response.
            const response = {
                pharmacyDetails: res.data.responseData,
                status: res.data.isSuccess,
            };
            // console.log(response);
            return response;
        })
        .catch((err) => {
            console.log(err);
            return err;
        });

    //   console.log(res);
    return res;
};
export const getHistory = async (data) => {
    const res = await API.get(`/pharmacist/filtered-appointments?nameSearchString=${data}`)
        .then((res) => {
            // fecth response.
            const response = {
                historyDetails: res.data.responseData,
                status: res.data.isSuccess,
            };
            // console.log(response);
            return response;
        })
        .catch((err) => {
            console.log(err);
            return err;
        });

    //   console.log(res);
    return res;
};

export const editPharmacyDetails = async (data) => {
    const res = await API.patch(
        `/pharmacist/profile`,
        data
    )
        .then((res) => {
            // fecth response.
            const response = {
                message: res.data.message,
                status: res.data.isSuccess ? "success" : "error",
            };
            console.log(response);
            return response;
        })
        .catch((err) => {
            console.log(err);
            return err;
        });

    //   console.log(res);
    return res;
};

export const changePassword = async (data) => {
    const res = await API.post(`/pharmacist/change-password`,data
    )
        .then((res) => {

            const response = {
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