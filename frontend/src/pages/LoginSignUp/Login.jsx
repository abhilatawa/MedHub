import * as React from "react";
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
import { useState } from "react";
import VisibilityIcon from "@mui/icons-material/Visibility";
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff";
import {
  IconButton,
  Select,
  MenuItem,
  FormControl,
  InputLabel,
} from "@mui/material";
import isEmpty from "validator/es/lib/isEmpty";
import { Login } from "../../services/LoginService";
import { patientSignUp } from "../../services/PatientSignupService";
import { useNavigate } from "react-router-dom";
import AlertMessage from "../../components/AlertMessage";
import Cookies from "js-cookie";

// Function to validate email format
function isValidEmail(email) {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

// Create a default theme for the application
const defaultTheme = createTheme();

export default function SignIn() {
  // used to display alert message to user.
  const [isAlert, setIsAlert] = useState(false);
  const [alertMessage, setAlertMessage] = useState("");
  const [alertStatus, setAlertStatus] = useState("");

  // State variables for managing form inputs and errors

  const [showPassword, setShowPassword] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [emailError, setEmailError] = useState(false);
  const [passwordError, setPasswordError] = useState(false);
  const [userRole, setUserRole] = useState("");
  const [userRoleerror, setUserRoleerror] = useState("");
  
  // the state to handle the remember me checkbox
  const [isRememberMeChecked, setIsRememberMeChecked] = useState(false);

  // Hook to handle navigation
  const navigate = useNavigate();

  // Function to handle form submission
  const handleSubmit = (event) => {
    event.preventDefault();
    setEmailError(false);
    setPasswordError(false);
    setUserRoleerror(false);

    // Validate email format
    if (!isValidEmail(email)) {
      setEmailError(true);
      return;
    }

    // Validate password presence
    if (isEmpty(password)) {
      setPasswordError(true);
      return;
    }

    // Validate password format using regex
    if (
      !/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/.test(
        password
      )
    ) {
      setPasswordError(true);
      return;
    }

    // Validate user role selection
    if (!userRole) {
      setUserRoleerror(true);
      return;
    }

    // Proceed with form submission logic
    const signindata = {
      username: email,
      password: password,
      userRole: userRole,
    };

    // set alert to false
    setIsAlert(false);

    // Call the login service
    Login(signindata)
      .then((res) => {
        
        // if remember me is selected set token expiration time to 1 month
        if(isRememberMeChecked) {
          Cookies.set("accessToken", res.token, { expires: getExpirationTime(), path: "/" });
        }
        else {
          Cookies.set("accessToken", res.token, { path: "/" });
        }
        // console.log(Cookies.get("accessToken"));
        // console.log(res);
        if (res.status) {

          if(userRole.toLowerCase().match("patient"))
          {
            navigate("/patient/dashboard");
          }
          else if (userRole.toLowerCase().match("doctor"))
          {
            navigate("/doctor-home");
          }
          else if (userRole.toLowerCase().match("admin"))
          {
            navigate("/admin/dashboard");
          }
          else if (userRole.toLowerCase().match("pharmacist"))
          {
            navigate("/pharmacy-home");
          }
        } else {
          // Handle the case where login failed
          console.log("Login failed");
          //   alert("Incorrect password or email");
          setIsAlert(true);
          setAlertMessage("Incorrect password or email");
          setAlertStatus("error");
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };

  // Function to toggle password visibility
  const handleTogglePasswordVisibility = () => {
    setShowPassword((prevShowPassword) => !prevShowPassword);
  };

  // Function to handle user role change
  const handleUserRoleChange = (event) => {
    setUserRole(event.target.value);
  };

  const handleCheckForRememberMe = (event) => {
    setIsRememberMeChecked(event.target.checked);
  }

  // calculate the expiratio time for 1 month
  const getExpirationTime = () => {
    const expirationTimeInDays = 30;
    const expirationTimeInMillis = expirationTimeInDays * 24 * 60 * 60 * 1000; 
    const currentTime = new Date().getTime();
    const expirationTime = new Date(currentTime + expirationTimeInMillis);
    return expirationTime;
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
            Sign in
          </Typography>
          <Box
            component="form"
            onSubmit={handleSubmit}
            noValidate
            sx={{ mt: 1 }}
          >
            {/* Input field for email */}
            <TextField
              margin="normal"
              required
              fullWidth
              id="email"
              label="Email Address"
              name="email"
              autoComplete="email"
              autoFocus
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              error={emailError}
              helperText={
                emailError ? "Please enter a valid email address" : ""
              }
            />
            {/* Input field for password */}
            <TextField
              margin="normal"
              required
              fullWidth
              name="password"
              label="Password"
              type={showPassword ? "text" : "password"}
              id="password"
              autoComplete="current-password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              helperText={
                passwordError
                  ? "Password is required and must contain one letter, one special character and one digit, and must be be at least 8 characters long."
                  : ""
              }
              error={passwordError}
              InputProps={{
                endAdornment: (
                  <VisibilityIconButton
                    onClick={handleTogglePasswordVisibility}
                    showPassword={showPassword}
                  />
                ),
              }}
            />
            {/* Dropdown for selecting user role */}
            <Select
              fullWidth
              id="userRole"
              value={userRole}
              onChange={handleUserRoleChange}
              displayEmpty
              inputProps={{ "aria-label": "user role" }}
              error={userRoleerror} // Add error prop to highlight the field when there's an error
            >
              <MenuItem value="">Select Role</MenuItem>
              <MenuItem value="PATIENT">Patients</MenuItem>
              <MenuItem value="DOCTOR">Doctors</MenuItem>
              <MenuItem value="ADMIN">Admin</MenuItem>
              <MenuItem value="PHARMACIST">Pharmacist</MenuItem>
            </Select>
            {/* Render helper text for user role field if there's an error */}
            {userRoleerror && (
              <Typography variant="caption" color="error">
                Please select a user role
              </Typography>
            )}

            {/* Checkbox for 'Remember me' */}
            <FormControlLabel
              control={<Checkbox checked={isRememberMeChecked} onChange={handleCheckForRememberMe} value="remember" color="primary" />}
              label="Remember me"
            />
            {/* Sign in button */}
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Sign In
            </Button>
            {/* Links for password recovery and sign up */}
            <Grid container>
              <Grid item xs>
                <Link
                  component="button"
                  variant="body2"
                  onClick={() => {
                    navigate("/forgot-password");
                  }}
                >
                  Forgot password?
                </Link>
              </Grid>
              <Grid item>
                <Link
                  component="button"
                  variant="body2"
                  onClick={() => {
                    navigate("/sign-up");
                  }}
                >
                  {"Don't have an account? Sign Up"}
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
}

// Component for toggling password visibility
function VisibilityIconButton({ onClick, showPassword }) {
  return (
    <IconButton
      aria-label="toggle password visibility"
      onClick={onClick}
      edge="end"
    >
      {showPassword ? <VisibilityIcon /> : <VisibilityOffIcon />}
    </IconButton>
  );
}
