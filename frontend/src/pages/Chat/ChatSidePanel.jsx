import React, { useState, useEffect } from "react";
import Box from "@mui/material/Box";
import ChatUserHeader from "./ChatUserHeader";
import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import { styled } from "@mui/material/styles";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import Divider from "@mui/material/Divider";
import ListItemText from "@mui/material/ListItemText";
import ListItemAvatar from "@mui/material/ListItemAvatar";
import Avatar from "@mui/material/Avatar";
import ChatIcon from "@mui/icons-material/Chat";
import PersonIcon from "@mui/icons-material/Person";
import { getAllPartners } from "../../services/ChatService";

export default function ChatSidePanel({ roomData, setRoomData }) {
  const [value, setValue] = React.useState(0);

  const [partnerList, setPartnerList] = useState([]);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  const handleSingleUserClick = (user) => {
    console.log(user)
    setRoomData({
      ...roomData,
      room: "test",
      receiver: user,
    });
  };

  const fetchData = async () => {
    getAllPartners()
      .then((res) => {
        let data = [];

        for (let i = 0; i < res.listOfPartners.length; i++) {
          data.push({
            id: i,
            username: res.listOfPartners[i]["username"],
            firstName: res.listOfPartners[i]["firstName"],
            lastName: res.listOfPartners[i]["lastName"],
            userRole: res.listOfPartners[i]["userRole"]
          });
        }
        setPartnerList(data);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <Box
      sx={{
        width: "20vw",
        display: "flex",
        flexDirection: "column",
        height: "100%",
      }}
    >
      {/* get the current user's first name, last name, and user role and pass it to chatuserheader */}
      {/* <ChatUserHeader /> */}
      <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
        <Tabs value={value} onChange={handleChange} aria-label="chat options">
          <Tab icon={<ChatIcon />} iconPosition="start" label="Chats" />
          {/* <Tab icon={<PersonIcon />} iconPosition="start" label="Users" /> */}
        </Tabs>
        {value === 0 && (
          <List
            sx={{
              p: 0,
              overflowY: "scroll",
              flex: "1 0 0",
              maxHeight: "100%",
              height: "70vh",
            }}
          >
            {partnerList.map((item) => (
              <>
                <ListItem
                  key={item}
                  style={{ cursor: "pointer" }}
                  alignItems="flex-start"
                  onClick={() => handleSingleUserClick(item)}
                >
                  <ListItemAvatar>
                    <Avatar>
                      <PersonIcon />
                    </Avatar>
                  </ListItemAvatar>
                  <ListItemText
                    primary={item.firstName + " " + item.lastName}
                    secondary={item.userRole}
                  />
                </ListItem>
                <Divider component="li" />
              </>
            ))}
          </List>
        )}
        {value === 1 && <></>}
      </Box>
    </Box>
  );
}
