import API from "../api";

// get all pending requests of doctors from backend
export const getAllPartners = async () => {
  const res = await API.get(`/chat/partners`)
    .then((res) => {
      // fecth response.
      const response = {
        listOfPartners: res.data.responseData,
      };
      return response;
    })
    .catch((err) => {
      console.log(err);
      return err;
    });

  //   console.log(res);
  return res;
};


export const getCurrentUser = async () => {
  const res = await API.get(`/chat/get_username`)
    .then((res) => {
      // fecth response.
      const response = {
        senderUsername: res.data.responseData,
      };
      return response;
    })
    .catch((err) => {
      console.log(err);
      return err;
    });

  //   console.log(res);
  return res;
}


export const getAllChats = async (receiverId) => {
  const res = await API.get(`/chat/conversation?receiverUserId=${receiverId}`)
    .then((res) => {
      // fecth response.
      const response = {
        chats: res.data.responseData,
      };
      return response;
    })
    .catch((err) => {
      console.log(err);
      return err;
    });

  //   console.log(res);
  return res;
}

