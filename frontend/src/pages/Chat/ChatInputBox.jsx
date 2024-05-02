import { Box, Button, InputBase, TextField } from "@mui/material";
import React, { useState, useRef } from "react";
import { S3 } from "aws-sdk";
import Paper from "@mui/material/Paper";
import AddIcon from "@mui/icons-material/Add";
import SendIcon from "@mui/icons-material/Send";
import IconButton from "@mui/material/IconButton";
import Divider from "@mui/material/Divider";

export default function ChatInputBox({ handleSendMessage }) {
  const [message, setMessage] = useState("");
  const [selectedFile, setSelectedFile] = useState(null);
  const fileInputRef = useRef(null);

  // create an object of s3
  const s3 = new S3({
    region: "us-east-2",
    accessKeyId: "AKIA47CRWPBC5DXP6GPQ",
    secretAccessKey: "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",
  });

  // when user uploads the file call this function
  const handleFileChange = (e) => {
    setSelectedFile(e.target.files[0]);
    setMessage(e.target.files[0].name);
  };

  // upload files to s3
  const uploadFileToS3 = (file) => {
    return new Promise((resolve, reject) => {
      const uploadParams = {
        Bucket: "medhubchat",
        Key: `chat-uploads/${file.name}`,
        Body: file,
        ACL: "public-read",
      };

      s3.upload(uploadParams, (err, data) => {
        if (err) {
          console.error("Error uploading to S3:", err);
          reject(err);
        } else {
          console.log("Upload successful:", data);
          resolve(data.Location); // Resolve with the URL of the uploaded file
        }
      });
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (selectedFile) {
      // If a file is selected, upload it to S3, then send the file URL as a message
      try {
        const fileUrl = await uploadFileToS3(selectedFile);
        // Send the S3 file URL as a message
        handleSendMessage(fileUrl);
      } catch (error) {
        console.error("Failed to upload file:", error);
      }
    } else if (message) {
      // If there's a message, send it
      handleSendMessage(message);
    }

    // Reset state
    setMessage("");
    setSelectedFile(null);
  };

  const handleAddIconClick = () => {
    fileInputRef.current.click();
  };

  return (
    <Paper
      component="form"
      sx={{
        p: "2px 4px",
        display: "flex",
        alignItems: "center",
        width: "100%",
      }}
      onSubmit={handleSubmit}
    >
      <IconButton
        onClick={handleAddIconClick}
        sx={{ p: "10px" }}
        aria-label="menu"
      >
        <AddIcon />
      </IconButton>
      <input
        ref={fileInputRef}
        id="file-input"
        type="file"
        onChange={handleFileChange}
        style={{ display: "none" }}
      />
      <Divider sx={{ height: 28, m: 0.5 }} orientation="vertical" />
      <InputBase
        sx={{ ml: 1, flex: 1 }}
        placeholder="Type message"
        fullWidth
        value={message}
        onChange={(e) => {
          setMessage(e.target.value);
        }}
      />
      <Divider sx={{ height: 28, m: 0.5 }} orientation="vertical" />
      <IconButton
        type="submit"
        color="primary"
        sx={{ p: "10px" }}
        aria-label="send"
      >
        <SendIcon />
      </IconButton>
    </Paper>
  );
}
