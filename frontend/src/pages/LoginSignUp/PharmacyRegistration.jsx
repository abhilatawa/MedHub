import React, { useState } from "react";
import validator from "validator";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import Link from "@mui/material/Link";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { pharmacyRegistration } from "../../services/PharmacyRegistrationService";
import { useNavigate } from "react-router-dom";
import AlertMessage from "../../components/AlertMessage";

const defaultTheme = createTheme();
const PharmacyRegistration = () => {
  const [emailError, setEmailError] = useState("");
  const [passwordError, setPasswordError] = useState("");
  const [consentChecked, setConsentChecked] = useState(false);

  // used to display alert message to user.
  const [isAlert, setIsAlert] = useState(false);
  const [alertMessage, setAlertMessage] = useState("");
  const [alertStatus, setAlertStatus] = useState("");

  const navigate = useNavigate();

  // Function to handle the user checking the consent box
  const handleConsentChange = (event) => {
    setConsentChecked(event.target.checked);
  };

  // Function to handle the submission of the form to the registration API
  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const signUpData = {
      firstName: data.get("firstName"),
      lastName: data.get("lastName"),
      username: data.get("email"),
      password: data.get("password"),
      addressLine1: data.get("addressLine1"),
      addressLine2: data.get("addressLine2"),
      postalCode: data.get("postalCode"),
      contactNumber: data.get("contactNumber"),
      licenseNumber: data.get("licenseNumber"),
      pharmacyName: data.get("pharmacyName"),
      userRole: "PHARMACIST",
    };

    // Send the form only if no fields are left empty
    if (
      emailError === "" &&
      passwordError === "" &&
      signUpData.firstName !== "" &&
      signUpData.lastName !== "" &&
      signUpData.username !== "" &&
      signUpData.password !== "" &&
      signUpData.addressLine1 !== "" &&
      signUpData.addressLine2 !== "" &&
      signUpData.postalCode !== "" &&
      signUpData.contactNumber !== "" &&
      signUpData.licenseNumber !== "" &&
      consentChecked !== false
    )
      // set alert to false
      setIsAlert(false);

    // api call
    pharmacyRegistration(signUpData)
      .then((res) => {
        console.log(res);

        // console.log(res);
        setIsAlert(true);
        setAlertMessage(res["message"]);
        setAlertStatus(res["status"]);

        if (res["isSuccess"]) {
          setTimeout(() => {
            navigate("/sign-in");
          }, 2500);
        }

        //   navigate("/sign-in");
      })
      .catch((err) => {
        console.log(err);
      });
  };

  // Function to validate email
  const validateEmail = (e) => {
    const email = e.target.value;
    if (validator.isEmail(email)) {
      setEmailError("");
    } else {
      setEmailError("Invalid Email");
    }
  };

  // Function to validate email
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
      setPasswordError(
        "Password is not strong. It must contain at least one letter and one digit, and be at least 8 characters long."
      );
    }
  };
  
  return (
    <ThemeProvider theme={defaultTheme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        {isAlert && (
          <AlertMessage message={alertMessage} status={alertStatus} />
        )}
        <Box
          sx={{
            marginTop: 8,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Pharmacy Registration
          </Typography>
          <Box
            component="form"
            noValidate
            onSubmit={handleSubmit}
            sx={{ mt: 3 }}
          >
            <Grid container spacing={2}>
              <Grid item xs={12} sm={6}>
                <TextField
                  autoComplete="given-name"
                  name="firstName"
                  required
                  fullWidth
                  id="firstName"
                  label="First Name"
                  autoFocus
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  required
                  fullWidth
                  id="lastName"
                  label="Last Name"
                  name="lastName"
                  autoComplete="family-name"
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  id="email"
                  label="Email Address"
                  name="email"
                  autoComplete="email"
                  onChange={(e) => validateEmail(e)}
                  error={emailError}
                  helperText={emailError}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  name="password"
                  label="Password"
                  type="password"
                  id="password"
                  autoComplete="new-password"
                  onChange={(e) => validatePassword(e.target.value)}
                  error={passwordError}
                  helperText={passwordError}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  name="addressLine1"
                  label="Address Line 1"
                  type="text"
                  id="addressLine1"
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  name="addressLine2"
                  label="Address Line 2"
                  type="text"
                  id="addressLine2"
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  name="postalCode"
                  label="Postal Code"
                  type="text"
                  id="postalCode"
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  name="contactNumber"
                  label="Phone Number"
                  type="text"
                  id="contactNumber"
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  name="licenseNumber"
                  label="License Number"
                  type="text"
                  id="licenseNumber"
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  name="pharmacyName"
                  label="Pharmacy Name"
                  type="text"
                  id="pharmacyName"
                />
              </Grid>
              <Grid item xs={12}>
                <FormControlLabel
                  required
                  control={
                    <Checkbox
                      checked={consentChecked}
                      onChange={handleConsentChange}
                    />
                  }
                  label="I consent to MedHub confirming my medical license in order to register my account"
                />
              </Grid>
            </Grid>
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Register
            </Button>
            <Grid container justifyContent="flex-end">
              <Grid item>
                <Link
                  component="button"
                  variant="body2"
                  onClick={() => {
                    navigate("/sign-in");
                  }}
                >
                  Already have an account? Sign in
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
};

export default PharmacyRegistration;
