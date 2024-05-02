import React from 'react'
import Box from "@mui/material/Box";
import ChatContentHeader from './ChatContentHeader'
import ChatArea from './ChatArea';
import ChatInputBox from './ChatInputBox';
import { Typography } from '@mui/material';

export default function ChatContent({roomData, handleSendMessage, chats, senderId}) {
  return (
    <Box sx={{width: "60vw", height: "100%", flexDirection: "column"}}>
      {roomData.room === null ?
      (
        <Typography align='center'>Please select a user</Typography>
      ) 
      :
      (
        <>

        <ChatContentHeader roomData={roomData}/>
        <ChatArea chats={chats} senderId={senderId}/>
        <ChatInputBox handleSendMessage={handleSendMessage}/>
        </>
      )
      }
    </Box>
  )
}
