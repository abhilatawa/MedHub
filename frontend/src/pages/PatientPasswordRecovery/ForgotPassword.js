import * as React from "react";
import {
  Button,
  CssBaseline,
  TextField,
  Box,
  Typography,
  Container
} from "@mui/material";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { useState } from "react";
import validator from "validator";
import { patientForgotPassword } from "../../services/ForgotPasswordService";
import AlertMessage from "../../components/AlertMessage";

const defaultTheme = createTheme();

export default function EmailInputForm() {
  // used to display alert message to user.
  const [isAlert, setIsAlert] = useState(false);
  const [alertMessage, setAlertMessage] = useState("");
  const [alertStatus, setAlertStatus] = useState("");

  // used to validate the email.
  const [emailError, setEmailError] = useState("");

  // function which validates the email
  const validateEmail = (value) => {
    if (validator.isEmail(value)) {
      setEmailError("");
    } else {
      setEmailError("Invalid Email");
    }
  };

  // called when submit button clicked.
  const onEmailSubmit = (event) => {
    event.preventDefault();

    const data = new FormData(event.currentTarget);

    // call api only if there is no error in email input.
    if (!emailError) {
      const emailData = { email: data.get("email") };

      // set alert to false
      setIsAlert(false);

      // call service which eventually calls the API.
      patientForgotPassword(emailData)
        .then((res) => {
          setIsAlert(true);
          setAlertMessage(res["message"]);
          setAlertStatus(res["status"]);
        })
        .catch((err) => {
          console.log(err)
        });
    }
  };

  return (
    <ThemeProvider theme={defaultTheme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        {isAlert && (
          <AlertMessage
            message={alertMessage}
            status={alertStatus}
          />
        )}
        <Box
          sx={{
            marginTop: 12,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Typography component="h1" variant="h5">
            Forgot Password
          </Typography>
          <Box
            component="form"
            noValidate
            sx={{ mt: 1, alignItems: "center" }}
            onSubmit={onEmailSubmit}
          >
            <TextField
              margin="normal"
              fullWidth
              id="email"
              label="Email Address"
              name="email"
              autoComplete="email"
              onChange={(e) => {
                validateEmail(e.target.value);
              }}
              error={!!emailError}
              helperText={emailError}
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Send Verification Link
            </Button>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
}
