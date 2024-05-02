import * as React from "react";
import {
  Button,
  CssBaseline,
  TextField,
  Box,
  Typography,
  Container,
  Card,
  CardContent,
  Paper,
  FormControlLabel,
  Checkbox,
  CardHeader,
} from "@mui/material";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { useState } from "react";
import validator from "validator";
import { resetPassword } from "../../services/ForgotPasswordService";
import AlertMessage from "../../components/AlertMessage";
import { useNavigate } from "react-router-dom";

const defaultTheme = createTheme();

export default function ResetPasswordForm() {
  // state variable used to validate passwords
  const [passwordError, setPasswordError] = useState("");
  const [confirmPasswordError, setConfirmPasswordError] = useState("");

  // used to display alert message to user.
  const [isAlert, setIsAlert] = useState(false);
  const [alertMessage, setAlertMessage] = useState("");
  const [alertStatus, setAlertStatus] = useState("");

  // function to check password is strong or not
  const validatePassword = (value) => {
    if (
      validator.isStrongPassword(value, {
        minLength: 8,
        minLowercase: 1,
        minUppercase: 1,
        minNumbers: 1,
        minSymbols: 1,
      })
    ) {
      setPasswordError("");
    } else {
      setPasswordError("Is Not Strong Password");
    }
  };

  // function used to match 2 passwords
  const matchPassword = (new_password, confirm_password) => {
    if (new_password === confirm_password) {
      return true;
    } else {
      return false;
    }
  };

  // fetch token and email from query parameters
  const queryParameters = new URLSearchParams(window.location.search);
  const token = queryParameters.get("token");
  const email = queryParameters.get("email");

  // create navigate object
  const navigate = useNavigate();

  const handleSubmit = (event) => {
    event.preventDefault();

    const data = new FormData(event.currentTarget);
    const newPassword = data.get("new_password");
    const confirmPassword = data.get("confirm_password");

    if (matchPassword(newPassword, confirmPassword)) {
      // set confirmPasswordError to empty.
      setConfirmPasswordError("");
      // console.log("password matched!!")

      // create a request object
      const reqData = {
        email: email,
        token: token,
        newPassword: newPassword,
      };

      // set alert to false
      setIsAlert(false);

      // call to api
      resetPassword(reqData)
      .then((res) => {
        setIsAlert(true);
        setAlertMessage(res["message"]);
        setAlertStatus(res["status"]);
        navigate("/sign-in")
      })
      .catch((err) => {
        console.log(err)
      });
    } else {
      setConfirmPasswordError("Passwords does not match");
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
            Reset Password
          </Typography>
          <Box
            component="form"
            onSubmit={handleSubmit}
            noValidate
            sx={{ mt: 1 }}
          >
            <TextField
              margin="normal"
              fullWidth
              name="new_password"
              label="New Password"
              type="password"
              id="new_password"
              onChange={(e) => {
                validatePassword(e.target.value);
                setConfirmPasswordError("");
              }}
              error={!!passwordError}
              helperText={passwordError}
            />
            <TextField
              margin="normal"
              fullWidth
              name="confirm_password"
              label="Confirm Password"
              type="password"
              id="confirm_password"
              error={!!confirmPasswordError}
              helperText={confirmPasswordError}
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Change Password
            </Button>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
}
