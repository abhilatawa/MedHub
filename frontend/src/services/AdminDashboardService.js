import API from "../api";

// get all pending requests of doctors from backend
export const getAllDoctorPendingRequest = async () => {
  const res = await API.get(`/admin/dashboard/unverified_doctors`)
    .then((res) => {
      // fecth response.
      const response = {
        doctorPendingRequests: res.data.responseData,
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

// get all pending requests of doctors from backend
export const getAllPharmacistPendingRequest = async () => {
  const res = await API.get(`/admin/dashboard/unverified_pharmacists`)
    .then((res) => {
      // fecth response.
      const response = {
        pharmacistPendingRequests: res.data.responseData,
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

// get single unverified doctor details 
export const getSingleUnverifiedDoctor = async (data) => {
  
  const res = await API.post(`/admin/dashboard/unverified_doctors/details`, data)
    .then((res) => {
      // fecth response.
      const response = {
        doctorData: res.data.responseData,
        status: res.data.isSuccess
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

}

// get single unverified doctor details 
export const getSingleUnverifiedPharmacist = async (data) => {
  
  const res = await API.post(`/admin/dashboard/unverified_pharmacists/details`, data)
    .then((res) => {
      // fecth response.
      const response = {
        pharmacistData: res.data.responseData,
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

}

// admin approve the pending request
export const approvePendingRequest = async (data) => {
  const res = await API.post(`/admin/dashboard/approve`, data)
    .then((res) => {
      // fecth response.
      const response = {
        message: res.data.message,
        status: res.data.isSuccess ? "success" : "error"
      };
      return response;
    })
    .catch((err) => {
      console.log(err);
      return err;
    });

  return res;
}

// admin approve the pending request
export const rejectPendingRequest = async (data) => {
  const res = await API.post(`/admin/dashboard/reject`, data)
    .then((res) => {
      // fecth response.
      const response = {
        message: res.data.message,
        status: res.data.isSuccess ? "success" : "error"
      };
      return response;
    })
    .catch((err) => {
      console.log(err);
      return err;
    });

  return res;
}