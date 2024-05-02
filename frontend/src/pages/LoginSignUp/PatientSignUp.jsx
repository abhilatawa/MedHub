import React, {useState} from "react";
import validator from "validator";
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import Cookies from 'js-cookie';
import {Navigate, useNavigate} from "react-router-dom";


import user_icon from './Assets/person.png'
import email_icon from './Assets/email.png'
import password_icon from './Assets/password.png'
import {getValue} from "@testing-library/user-event/dist/utils";
import isEmpty from "validator/es/lib/isEmpty";
import {patientSignUp} from "../../services/PatientSignupService";

const defaultTheme = createTheme();
const PatientSignUp = () => {
    const [emailError, setEmailError] = useState('')
    const [passwordError, setPasswordError] = useState('')

    const navigate = useNavigate();
    const handleSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        const signUpData= {
            firstName:data.get('firstName'),
            lastName:data.get('lastName'),
            username: data.get('email'),
            password: data.get('password'),
            userRole:'PATIENT'
        };
        if(emailError=== "" && passwordError === "" && signUpData.firstName!== "" && signUpData.lastName!== "" && signUpData.username!== "" && signUpData.password!== "" ) {

            // Calling backend service for signup for to integrate it with backend
            patientSignUp(signUpData)
                .then((res) => {
                    console.log(res);
                    Cookies.set('accessToken', res.token, {path: '/'});
                    console.log(Cookies.get('accessToken'));
                    navigate('/sign-in');
                })
                .catch((err) => {
                    console.log(err);
                });
        }
    };


    const validateEmail = (e) => {
        const email = e.target.value
        if (validator.isEmail(email)) {
            setEmailError('')
        } else {
            setEmailError('Invalid Email')
        }
    }
    const validatePassword = (value) => {
        if (validator.isStrongPassword(value, {
            minLength: 8, minLowercase: 1,
            minUppercase: 1, minNumbers: 1, minSymbols: 1
        })) {
            setPasswordError('')
        } else {
            setPasswordError('Password is not strong. It must contain at least one letter and one digit, and be at least 8 characters long.')
        }
    }

    const[action,setAction]=useState("Sign Up");
    return(
        <ThemeProvider theme={defaultTheme}>
            <Container component="main" maxWidth="xs">
                <CssBaseline />
                <Box
                    sx={{
                        marginTop: 8,
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                    }}
                >
                    <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
                        <LockOutlinedIcon />
                    </Avatar>
                    <Typography component="h1" variant="h5">
                        Patient Sign up
                    </Typography>
                    <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 3 }}>
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
                                    error={!!emailError}
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
                                    error={!!passwordError}
                                    helperText={passwordError}
                                />

                            </Grid>

                        </Grid>
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            sx={{ mt: 3, mb: 2 }}
                        >
                            Sign Up
                        </Button>
                        <Grid container justifyContent="flex-end">
                            <Grid item>
                                <Link component="button" variant="body2" onClick={() => {
                                    navigate('/sign-in');
                                }}>
                                    Already have an account? Sign in
                                </Link>
                            </Grid>
                        </Grid>
                    </Box>
                </Box>
            </Container>
        </ThemeProvider>
    )
}

export default PatientSignUp;