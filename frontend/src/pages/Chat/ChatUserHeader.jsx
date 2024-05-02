import { Avatar, Card, CardHeader } from '@mui/material'
import React from 'react'

export default function ChatUserHeader() {
  return (
    <Card sx={{
        bgcolor: "primary.main",
        borderRadius: 0,
        color: "primary.contrastText",
    }}>
        <CardHeader avatar={<Avatar>P</Avatar>} title="Patient" >

        </CardHeader>

    </Card>
  )
}
