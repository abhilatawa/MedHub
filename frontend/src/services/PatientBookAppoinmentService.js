import API from "../api";
export const getAppoinmentSlot = async (data) => {
    const res = await API.get(`/patient/get-doctor-availability?doctorId=${data}`,
    )
        .then((res) => {
            // fecth response.
            const response = {
                appointmentDetails: res.data.responseData,
                status: res.data.isSuccess,
            };
            // console.log(response);
            return response;
        })
        .catch((err) => {
            console.log(err);
            return err;
        });
    return res;
};

export const getDoctorProfile= async (data) => {
    const res = await API.get(`/patient/get-doctor-details?doctorId=${data}`,
    )
        .then((res) => {
            // fecth response.
            const response = {
                doctorProfileDetails: res.data.responseData,
                status: res.data.isSuccess,
            };
            // console.log(response);
            return response;
        })
        .catch((err) => {
            console.log(err);
            return err;
        });
    return res;
};

export const addAppoinmentDetails = async (data) => {
    const res = await API.post(`/patient/appointment/create`, data)
        .then((res) => {
            // fecth response.
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

export const getAppointmentDetails = async () => {
    const res = await API.get(`/patient/appointments`,
    )
        .then((res) => {
            // fecth response.
            const response = {
                appointmentHistory: res.data.responseData,
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
