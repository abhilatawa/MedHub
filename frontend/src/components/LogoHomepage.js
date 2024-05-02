import * as React from 'react';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import Typography from '@mui/material/Typography';
import Grid from "@mui/material/Grid";
import {useNavigate} from "react-router-dom";

export default function LogoHomepage() {

    const navigate = useNavigate();

    return (
        <Box
            component="main"
            sx={{
                backgroundColor: (theme) =>
                    theme.palette.mode === "light"
                        ? theme.palette.grey[100]
                        : theme.palette.grey[900],
                flexGrow: 1,
                // height: "100vh",
                overflow: "auto",
                alignItems: 'center',
                justifyContent: 'center',
                backgroundRepeat: 'no-repeat',
            }}
        >
            <Container
                sx={{
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                    justifyContent: 'center',
                    pt: {xs: 8, sm: 12},
                    pb: {xs: 8, sm: 12},
                }}
            >
                <Grid container spacing={2}>
                    {/*Left side container to hold the Welcome message and info about Medhub*/}
                    <Grid item xs={8}>
                        <Grid container direction='row' spacing={2} alignItems='center' justifyContent='center'>
                            <Grid item>
                                <Grid container direction='row' spacing={2} alignItems='center'>
                                    <Grid item> <img src={require("../Assets/medhub_logo.png")} alt="logo" width={100}
                                                     height={100}/> </Grid>
                                    <Grid item>
                                        <Typography variant='h2'>
                                            Welcome to MedHub
                                        </Typography>
                                    </Grid>
                                </Grid>
                            </Grid>
                            <Grid item>
                                <Typography variant='h4' gutterBottom sx={{alignSelf: 'center'}}>
                                    Where care comes to you
                                </Typography>
                            </Grid>
                            <Grid item xs={8}>
                                <Typography
                                    textAlign="center"
                                    color="text.secondary"
                                >
                                    MedHub is a healthcare platform aimed at making our user's lives easier. By
                                    providing you
                                    with everything you need to meet your needs in one place, we take the headache out
                                    of
                                    accessing quality healthcare.
                                </Typography>
                            </Grid>
                        </Grid>
                    </Grid>
                    {/*Right side container to hold the links to register and sign in*/}
                    <Grid item xs={4}>
                        <Grid container direction='row' spacing={2} alignItems='center' justifyContent='center'>
                            <Grid item xs={12} sx={{display: 'flex'}}>
                                <Typography
                                    variant='h5'
                                    textAlign="center"
                                    sx={{alignSelf: 'center', width: {sm: '100%', md: '100%'}}}
                                >
                                    Register today
                                </Typography>
                            </Grid>
                            <Grid item xs={8} sx={{display: 'flex'}}>
                                <Button
                                    fullWidth
                                    variant="contained"
                                    onClick={() => {
                                        navigate('/doctor-registration');
                                    }}
                                >
                                    DOCTOR REGISTRATION
                                </Button>
                            </Grid>
                            <Grid item xs={8} sx={{display: 'flex'}}>
                                <Button
                                    fullWidth
                                    variant="contained"
                                    onClick={() => {
                                        navigate('/pharmacy-registration');
                                    }}
                                >
                                    PHARMACY REGISTRATION
                                </Button>
                            </Grid>
                            <Grid item xs={8} sx={{display: 'flex'}}>
                                <Button
                                    fullWidth
                                    variant="contained"
                                    onClick={() => {
                                        navigate('/sign-up');
                                    }}
                                >
                                    PATIENT SIGN UP
                                </Button>
                            </Grid>
                            <Grid item xs={12} sx={{display: 'flex'}}>
                                <Typography
                                    variant='h5'
                                    textAlign="center"
                                    sx={{alignSelf: 'center', width: {sm: '100%', md: '100%'}}}
                                >
                                    Already a member?
                                </Typography>
                            </Grid>
                            <Grid item xs={8} sx={{display: 'flex'}}>
                                <Button
                                    fullWidth
                                    variant="contained"
                                    onClick={() => {
                                        navigate('/sign-in');
                                    }}
                                >
                                    SIGN IN
                                </Button>
                            </Grid>
                        </Grid>
                    </Grid>
                </Grid>
            </Container>
        </Box>
    );
}