import API from "../api";

export const patientForgotPassword = async (data) => {
  const res = await API.post(`/auth/forgot-password`, data)
    .then((res) => {
      // fecth response.
      const response = {
        message: res.data.responseData,
        status: res.data.message === "Success." ? "success" : "error",
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

export const resetPassword = async (data) => {

    const res = await API.post(`/auth/reset-password`, data)
    .then((res) => {
      // fecth response.
      const response = {
        message: res.data.responseData,
        status: res.data.message === "Success." ? "success" : "error",
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
