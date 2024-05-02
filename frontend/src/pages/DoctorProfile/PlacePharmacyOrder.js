import * as React from "react";
import {styled} from "@mui/material/styles";
import Box from "@mui/material/Box";
import SideNavBarDoctor from "../../components/SideNavBarDoctor";
import Button from "@mui/material/Button";
import {doctorRegistration} from "../../services/DoctorRegistrationService";
import Cookies from "js-cookie";
import {useState} from "react";
import {LocalizationProvider} from "@mui/x-date-pickers/LocalizationProvider";
import {AdapterDayjs} from "@mui/x-date-pickers/AdapterDayjs";
import {DatePicker} from "@mui/x-date-pickers/DatePicker";
import Container from "@mui/material/Container";
import TextField from "@mui/material/TextField";
import Grid from "@mui/material/Grid";
import Paper from "@mui/material/Paper";

const DrawerHeader = styled("div")(({theme}) => ({
    display: "flex",
    alignItems: "center",
    justifyContent: "flex-end",
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
}));


export default function PlacePharmacyOrder() {
    const [value, setValue] = useState();
    const handleSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        const signUpData = {
            firstName: data.get('firstName'),
            lastName: data.get('lastName'),
            username: data.get('email'),
            password: data.get('password'),
            addressLine1: data.get('addressLine1'),
            addressLine2: data.get('addressLine2'),
            postalCode: data.get('postalCode'),
            contactNumber: data.get('contactNumber'),
            licenseNumber: data.get('licenseNumber'),
            userRole: 'DOCTOR'
        };
        doctorRegistration(signUpData)
            .then((res) => {
                console.log(res)
                Cookies.set('accessToken', res.token, {path: '/'});
                console.log(Cookies.get('accessToken'));
                //navigate('/sign-in');
            })
            .catch((err) => {
                console.log(err)
            });

    };

    return (
        <Box component="form" noValidate onSubmit={handleSubmit} sx={{display: "flex"}}>
            <SideNavBarDoctor/>
            <Box
                component="main"
                sx={{
                    backgroundColor: (theme) =>
                        theme.palette.mode === "light"
                            ? theme.palette.grey[100]
                            : theme.palette.grey[900],
                    flexGrow: 1,
                    height: "100vh",
                    overflow: "auto",
                }}
            >
                <DrawerHeader/>
                <Container maxWidth="lg" sx={{mt: 4, mb: 4}}>
                    <Grid container spacing={3}>
                        <Grid item xs={12}>
                            <Paper sx={{p: 2, display: "flex", flexDirection: "column"}}>
                                <Grid item xs={12}>
                                    <label style={{color: 'black', fontSize: '20px'}}>Place order to Pharmacy</label>
                                </Grid>
                                <p/>
                                <Grid container spacing={2}>
                                    <Grid item xs={12} sm={6}>
                                        <LocalizationProvider dateAdapter={AdapterDayjs}>
                                            <DatePicker
                                                label="Date"
                                                value={value}
                                                onChange={(newValue) => setValue(newValue)}
                                            />
                                        </LocalizationProvider>
                                    </Grid>
                                </Grid>
                                <p/>
                                <Grid container spacing={2}>
                                    <Grid item xs={12} sm={6}>
                                        <TextField
                                            name="patientName"
                                            required
                                            id="patientName"
                                            label="Patient Name"
                                            fullWidth
                                            onChange={(e) => {
                                                let value = e.target.value
                                                value = value.replace(/[^A-Za-z]/ig, '')
                                                e.target.value = value
                                            }}
                                        />
                                    </Grid>
                                    <Grid item xs={12} sm={6}>
                                        <TextField
                                            required
                                            id="pharmacyName"
                                            label="Pharmacy Name"
                                            name="pharmacyName"
                                            fullWidth
                                            onChange={(e) => {
                                                let value = e.target.value
                                                value = value.replace(/[^A-Za-z]/ig, '')
                                                e.target.value = value
                                            }}
                                        />
                                    </Grid>
                                </Grid>
                                <p/>
                                <Grid container spacing={2}>
                                    <Grid item xs={12}>
                                        <TextField
                                            name="prescription"
                                            required
                                            id="prescription"
                                            label="Prescription"
                                            fullWidth
                                            multiline
                                        />
                                    </Grid>
                                </Grid>
                                <Grid item md={2} sx={4}>
                                    <Button
                                        type="submit"
                                        variant="contained" tart
                                        sx={{mt: 3, mb: 2}}
                                    >
                                        Place Order
                                    </Button>
                                </Grid>
                            </Paper>
                        </Grid>
                    </Grid>
                </Container>
            </Box>
        </Box>
    );
}
