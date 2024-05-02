import API from "../api";
import Cookies from "js-cookie";

// get all details of the patient
export const getPatientProfile = async () => {
  const res = await API.get(`/patient/profile`, 
  // {
  //   headers: {
  //     Authorization: `Bearer ${Cookies.get("accessToken")}`,
  //   },
  // }
  )
    .then((res) => {
      // fecth response.
      const response = {
        patientProfileDetails: res.data.responseData,
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


// edit the details of the patient
export const editPatientDetails = async (data) => {
  const res = await API.patch(`/patient/profile/edit`, data,
  // {
  //   headers: {
  //     Authorization: `Bearer ${Cookies.get("accessToken")}`,
  //   },
  // }
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

