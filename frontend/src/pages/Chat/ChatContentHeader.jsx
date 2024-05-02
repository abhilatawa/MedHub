import React from 'react'
import { Avatar, Card, CardHeader } from "@mui/material";

export default function ChatContentHeader({roomData}) {

  return (
    <Card
      sx={{
        borderRadius: 0,
      }}
    >
      <CardHeader avatar={<Avatar>{roomData.receiver.userRole.charAt(0).toUpperCase()}</Avatar>} title={roomData.receiver.firstName + " " + roomData.receiver.lastName}></CardHeader>
    </Card>
  )
}
