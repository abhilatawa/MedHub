import React, { useState, useEffect } from "react";
import { Paper } from "@mui/material";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import { styled } from "@mui/material/styles";
import { over } from "stompjs";
import SockJS from "sockjs-client";
import SideNavBarPatient from "../../components/SideNavBarPatient";
import ChatSidePanel from "./ChatSidePanel";
import ChatContent from "./ChatContent";
import SideNavBarDoctor from "../../components/SideNavBarDoctor";
import { getAllChats, getCurrentUser } from "../../services/ChatService";

// create object of stompClient
var stompClient = null;

const DrawerHeader = styled("div")(({ theme }) => ({
  display: "flex",
  alignItems: "center",
  justifyContent: "flex-end",
  padding: theme.spacing(0, 1),
  ...theme.mixins.toolbar,
}));

export default function ChatRoot({ isPatient }) {

  // state which manages the all chats
  const [chats, setChats] = useState([]);

  // state which holds the username of the current user
  const [senderId, setSenderId] = useState("");

  // state which manages the connection and receiver user information
  const [roomData, setRoomData] = useState({
    connected: false,
    room: null,
    receiver: {},
  });

  // make a connection with web socket
  const connect = () => {
    let Sock = new SockJS("http://172.17.0.96:8080/ws");
    stompClient = over(Sock);
    stompClient.connect({}, onConnected, onError);
  };

  // if connection with web socket got successful
  const onConnected = () => {

    // make connected to true as connection is established
    setRoomData((prevRoomData) => ({ ...prevRoomData, connected: true }));

    // if username of the current user exist
    if (senderId) {

      // subscribe to common channel to listen for real time message
      stompClient.subscribe(`/user/${senderId}/private`, onPrivateMessage);
      console.log("connection is successful!!");
    }
  };

  // once message is broadcasted to common channel, fetch it and update the chats state
  const onPrivateMessage = (payload) => {
    const payloadData = JSON.parse(payload.body);
    setChats((prevChats) => [...prevChats, payloadData]);
  };

  // if error while making connection with web socket
  const onError = (err) => {
    console.error(err);
  };

  // get the username of the current user from the backend
  useEffect(() => {
    const initChat = async () => {
      try {
        const res = await getCurrentUser();
        setSenderId(res.senderUsername);
      } catch (err) {
        console.error(err);
      }
    };
    initChat();
  }, []);

  // call method to make connection with websocket when username of the current user is fetched from the backend
  useEffect(() => {
    if (senderId) {
      connect();
    }
  }, [senderId]);


  // if connection is successful and we have receiver user information fetch the previous chats of the between sender and receiver
  useEffect(() => {
    if (roomData.connected && senderId && roomData.receiver.username) {
      fetchAllChats();
    }
  }, [roomData.connected, senderId, roomData.receiver.username]);

  // function to fetch the all chats from the backend between current username and receiver and update the chats state
  const fetchAllChats = async () => {
    try {
      const res = await getAllChats(roomData.receiver.username);
      setChats(res.chats);
    } catch (err) {
      console.error(err);
    }
  };

  const handleSendMessage = (msg) => {
    if (stompClient && roomData.receiver.username) {
      // const currentDate = new Date();
      // const currentTimestamp = currentDate.getTime();
      const chatMessage = {
        senderId: senderId,
        receiverId: roomData.receiver.username,
        content: msg,
        // timestamp: currentTimestamp,
      };
      setChats((prevChats) => [...prevChats, chatMessage]);
      stompClient.send('/app/chat/send', {}, JSON.stringify(chatMessage));
      // fetchAllChats();
    }
  };

  return (
    <Box sx={{ display: "flex" }}>
      {isPatient ? <SideNavBarPatient /> : <SideNavBarDoctor />}
      <Box
        component="main"
        sx={{
          backgroundColor: (theme) =>
            theme.palette.mode === "light"
              ? theme.palette.grey[100]
              : theme.palette.grey[900],
          flexGrow: 1,
          height: "100vh",
          overflow: "auto",
        }}
      >
        <DrawerHeader />
        <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
          <Paper elevation={0} sx={{ display: "flex" }}>
            <ChatSidePanel roomData={roomData} setRoomData={setRoomData} />
            <ChatContent
              roomData={roomData}
              handleSendMessage={handleSendMessage}
              chats={chats}
              senderId={senderId}
            />
          </Paper>
        </Container>
      </Box>
    </Box>
  );
}
