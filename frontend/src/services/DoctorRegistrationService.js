import API from '../api';

export const doctorRegistration=async (data)=>{

    const res = await API.post(`/auth/register/doctor`, data)
        .then((res) => {
            // fetch response.
            const response = {
                isSuccess: res.data.isSuccess,
                message: res.data.message,
                status: "error",
            };

            if(response["isSuccess"]) {
                response["message"] = "Registerd successfully. Yet to be verified by admin.";
                response["status"] = "success";
            }
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