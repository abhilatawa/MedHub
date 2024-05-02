import {
  Avatar,
  Box,
  List,
  ListItem,
  ListItemAvatar,
  ListItemText,
  Paper,
  Typography,
} from "@mui/material";
import React, { useEffect, useRef } from "react";

export default function ChatArea({ chats, senderId }) {
  const chatEndRef = useRef(null);

  // Function to scroll to the bottom of the chat area
  const scrollToBottom = () => {
    chatEndRef.current?.scrollIntoView({ behavior: "smooth" });
  };

  // Effect to scroll to the bottom whenever chats change
  useEffect(() => {
    scrollToBottom();
  }, [chats]);

  // function to format the timestamp to displayable date and time
  const getDateAndTime = (timeStamp) => {
    const date = new Date(timeStamp);

    const localDate = new Date(
      date.getTime() - date.getTimezoneOffset() * 60000
    );

    const option = {
      month: "short",
      day: "numeric",
      year: "numeric",
      hour12: true,
      hour: "numeric",
      minute: "2-digit",
    };

    const formattedDate = localDate.toLocaleDateString("en-US", option);

    return formattedDate;
  };

  return (
    <Box
      sx={{
        height: "62vh",
        overflowY: "scroll",
        backgroundColor: (theme) => theme.palette.grey[200],
      }}
    >
      <List>
        {chats === undefined
          ? ""
          : chats.map((item) =>
              senderId === item.senderId ? (
                <>
                  <ListItem key={item} sx={{ flexDirection: "row-reverse" }}>
                    <Paper
                      sx={{
                        width: "80%",
                        p: 1,
                        bgcolor: "#F7FFF7",
                        // color: "primary.contrastText",
                      }}
                    >
                      <ListItemText
                        primary={
                          <Typography>
                            {item.content.startsWith("https://") ? (
                              <a
                                href={item.content}
                                target="_blank"
                                rel="noopener noreferrer"
                              >
                                {item.content}
                              </a>
                            ) : (
                              item.content
                            )}
                          </Typography>
                        }
                        // secondary={
                        //   <Typography variant="caption">
                        //     {getDateAndTime(item.timestamp)}
                        //   </Typography>
                        // }
                      />
                    </Paper>
                  </ListItem>
                </>
              ) : (
                <>
                  <ListItem alignItems="flex-start">
                    <Paper sx={{ width: "80%", p: 1 }}>
                      <ListItemText
                        primary={
                          <Typography>
                            {item.content.startsWith("https://") ? (
                              <a
                                href={item.content}
                                target="_blank"
                                rel="noopener noreferrer"
                              >
                                {item.content}
                              </a>
                            ) : (
                              item.content
                            )}
                          </Typography>
                        }
                        // secondary={
                        //   <Typography variant="caption">
                        //     {getDateAndTime(item.timestamp)}
                        //   </Typography>
                        // }
                      />
                    </Paper>
                  </ListItem>
                </>
              )
            )}
        <div ref={chatEndRef} />
      </List>
    </Box>
  );
}
