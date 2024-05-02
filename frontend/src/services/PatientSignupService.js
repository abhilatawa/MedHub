import API from '../api';

    export const patientSignUp = async (data) => {
        // try
        // {
        //      await API.post(`/auth/register/patient`,data)
        //          .then(res=>{
        //              console.log(res);
        //              console.log(res.data);
        //              return res.data;
        //              }
        //          )
        // }
        // catch (err)
        // {
        //     console.error({err});
        //     return err;
        // }
        const res = await API.post(`/auth/register/patient`, data)
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

//   console.log(res);
        return res;

    };
