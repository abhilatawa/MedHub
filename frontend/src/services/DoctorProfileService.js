import API from "../api";

// get all details of the patient
export const getDoctorProfile = async () => {
  const res = await API.get(`/doctor/details`)
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

  //   console.log(res);
  return res;
};
export const getAppointments = async (data) => {
    const res = await API.get(`doctor/appointments?active_appointment=${data}`)
        .then((res) => {
            // fecth response.
            const response = {
                appoinmentDetails: res.data.responseData,
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
export const getPharmacy = async (data) => {
    const res = await API.get(`doctor/get-filtered-pharmacist-list?searchString=${data}`)
        .then((res) => {
            // fecth response.
            const response = {
                pharmacyDetails: res.data.responseData,
                status: res.data.isSuccess,
            };
            //console.log(response);
            return response;
        })
        .catch((err) => {
            console.log(err);
            return err;
        });

    //   console.log(res);
    return res;
};
export const editDoctorDetails = async (data) => {
  const res = await API.patch(
    `/doctor/details/edit`,
    data
  )
    .then((res) => {
      // fecth response.
      const response = {
        message: res.data.responseData,
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

export const updateAppoinment = async (data) => {
    const res = await API.patch(
        `/doctor/appointment/update`,
        data
    )
        .then((res) => {
            // fecth response.
            const response = {
                message: res.data.responseData,
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

export const uploadProfilePicture = async (data) => {
  console.log("into the service");
  console.log(data);
  const res = await API.post(`/doctor/details/upload_profile_picture`, data, {
    headers: {
        'Content-Type': 'multipart/form-data',
    }
  })
    .then((res) => {
      // fecth response.
      const response = {
        // message: res.data.responseData,
        status: res.data.isSuccess
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
