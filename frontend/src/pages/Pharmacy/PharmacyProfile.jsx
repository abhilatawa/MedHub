import * as React from "react";
import {styled} from "@mui/material/styles";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import Paper from "@mui/material/Paper";
import TextField from '@mui/material/TextField';
import Button from "@mui/material/Button";
import {useState, useEffect} from "react";
import SideNavBarPharmacy from "../../components/SideNavBarPharmacy";
import {editPharmacyDetails, getPharmacyProfile} from "../../services/PharmacyProfileService";
import AlertMessage from "../../components/AlertMessage";

const DrawerHeader = styled("div")(({theme}) => ({
    display: "flex",
    alignItems: "center",
    justifyContent: "flex-end",
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
}));
export default function PharmacyProfile() {
    const [file, setFile] = useState();
    // used to display alert message to user.
    const [isAlert, setIsAlert] = useState(false);
    const [alertMessage, setAlertMessage] = useState("");
    const [alertStatus, setAlertStatus] = useState("");
    const [personSpecialization, setPersonSpecialization] = useState([]);
    const [isEditable, setIsEditable] = useState(false);
    const [pharmacyDetails, setPharmacyDetails] = useState({
        id:0,
        firstName: "",
        lastName: "",
        username: "",
        pharmacyName:"",
        contactNumber: "",
        addressLine1: "",
        addressLine2: "",
        postalCode: "",
        licenseNumber: ""
    });

    // api call to fetch the details of the doctor
    const fetchData = async () => {
        getPharmacyProfile()
            .then((res) => {
                const data = res.pharmacyDetails;
                // console.log(data);
                setPharmacyDetails({
                    id:data["id"],
                    firstName: data["firstName"],
                    lastName: data["lastName"],
                    username: data["username"],
                    pharmacyName: data["pharmacyName"],
                    contactNumber: data["contactNumber"],
                    addressLine1: data["addressLine1"],
                    addressLine2: data["addressLine2"],
                    postalCode: data["postalCode"],
                    licenseNumber: data["licenseNumber"],
                });
            })
            .catch((err) => {
                console.log(err);
            });
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        // set alert to false
        setIsAlert(false);
        // reverse the value of the isEditable
        setIsEditable(!isEditable);

        if (isEditable) {
            // make api call to edit the details
            editPharmacyDetails(pharmacyDetails).then((res) => {
                setIsAlert(true);
                setAlertMessage(res["message"]);
                setAlertStatus(res["status"]);
            });
        }
    };

    useEffect(() => {
        // call this function to fetch data
        fetchData();
    }, []);


    return (
        <Box component="form" noValidate onSubmit={handleSubmit} sx={{display: "flex"}}>
            <SideNavBarPharmacy/>
            {isAlert && <AlertMessage message={alertMessage} status={alertStatus}/>}
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
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <Paper sx={{p: 2, display: "flex", flexDirection: "column"}}>
                                <Grid container spacing={2}>
                                    <Grid item xs={12}>
                                        <label style={{color: 'black', fontSize: '20px'}}>Personal Details</label>
                                    </Grid>
                                    <Grid item xs={12} sm={6}>
                                        <TextField
                                            fullWidth
                                            autoComplete="given-name"
                                            name="firstName"
                                            required
                                            id="firstName"
                                            label="First Name"
                                            autoFocus
                                            InputProps={{
                                                readOnly: !isEditable,
                                            }}
                                            value={pharmacyDetails["firstName"]}
                                            onChange={(e) => {
                                                let value = e.target.value
                                                value = value.replace(/[^A-Za-z]/ig, '')
                                                e.target.value = value
                                                setPharmacyDetails({
                                                    ...pharmacyDetails,
                                                    firstName: e.target.value,
                                                });
                                            }}
                                        />
                                    </Grid>
                                    <Grid item xs={12} sm={6}>
                                        <TextField
                                            fullWidth
                                            required
                                            id="lastName"
                                            label="Last Name"
                                            name="lastName"
                                            autoComplete="family-name"
                                            InputProps={{
                                                readOnly: !isEditable,
                                            }}
                                            value={pharmacyDetails["lastName"]}
                                            onChange={(e) => {
                                                let value = e.target.value
                                                value = value.replace(/[^A-Za-z]/ig, '')
                                                e.target.value = value
                                                setPharmacyDetails({
                                                    ...pharmacyDetails,
                                                    lastName: e.target.value,
                                                });
                                            }}
                                        />
                                    </Grid>
                                    <Grid item xs={12} sm={6}>
                                        <TextField
                                            fullWidth
                                            required
                                            id="email"
                                            label="Email Address"
                                            name="email"
                                            inputProps={
                                                {readOnly: true,}
                                            }
                                            InputProps={{
                                                readOnly: !isEditable,
                                            }}
                                            value={pharmacyDetails["username"]}
                                            onChange={(e) => {
                                                let value = e.target.value
                                                value = value.replace(/[^A-Za-z]/ig, '')
                                                e.target.value = value
                                                setPharmacyDetails({
                                                    ...pharmacyDetails,
                                                    username: e.target.value,
                                                });
                                            }}
                                        />
                                    </Grid>
                                    <Grid item xs={12} sm={6}>
                                        <TextField
                                            fullWidth
                                            required
                                            name="contactNumber"
                                            label="Phone Number"
                                            type="text"
                                            id="contactNumber"
                                            InputProps={{
                                                readOnly: !isEditable,
                                            }}
                                            value={pharmacyDetails["contactNumber"]}
                                            onChange={(e) => {
                                                setPharmacyDetails({
                                                    ...pharmacyDetails,
                                                    contactNumber: e.target.value,
                                                });
                                            }}
                                        />
                                    </Grid>
                                    <Grid item xs={12} sm={4}>
                                        <TextField
                                            fullWidth
                                            required
                                            name="addressLine1"
                                            label="Address Line 1"
                                            type="text"
                                            id="addressLine1"
                                            InputProps={{
                                                readOnly: !isEditable,
                                            }}
                                            value={pharmacyDetails["addressLine1"]}
                                            onChange={(e) => {
                                                setPharmacyDetails({
                                                    ...pharmacyDetails,
                                                    addressLine1: e.target.value,
                                                });
                                            }}
                                        />
                                    </Grid>
                                    <Grid item xs={12} sm={4}>
                                        <TextField
                                            fullWidth
                                            required
                                            name="addressLine2"
                                            label="Address Line 2"
                                            type="text"
                                            id="addressLine2"
                                            InputProps={{
                                                readOnly: !isEditable,
                                            }}
                                            value={pharmacyDetails["addressLine2"]}
                                            onChange={(e) => {
                                                setPharmacyDetails({
                                                    ...pharmacyDetails,
                                                    addressLine2: e.target.value,
                                                });
                                            }}
                                        />
                                    </Grid>
                                    <Grid item xs={12} sm={4}>
                                        <TextField
                                            fullWidth
                                            required
                                            name="postalCode"
                                            label="Postal Code"
                                            type="text"
                                            id="postalCode"
                                            InputProps={{
                                                readOnly: !isEditable,
                                            }}
                                            value={pharmacyDetails["postalCode"]}
                                            onChange={(e) => {
                                                setPharmacyDetails({
                                                    ...pharmacyDetails,
                                                    postalCode: e.target.value,
                                                });
                                            }}
                                        />
                                    </Grid>
                                    <Grid item xs={12}>
                                        <label style={{color: 'black', fontSize: '20px'}}>Pharmacy Details</label>
                                    </Grid>
                                    <Grid item xs={12} sm={6}>
                                        <TextField
                                            fullWidth
                                            name="licenseNumber"
                                            label="License Number"
                                            type="text"
                                            id="licenseNumber"
                                            InputProps={{
                                                readOnly: !isEditable,
                                            }}
                                            inputProps={
                                                {readOnly: true,}
                                            }
                                            value={pharmacyDetails["licenseNumber"]}
                                        />
                                    </Grid>
                                    <Grid item xs={12} sm={6}>
                                        <TextField
                                            fullWidth
                                            name="pharmacyName"
                                            label="Pharmacy Name"
                                            type="text"
                                            id="pharmacyName"
                                            InputProps={{
                                                readOnly: !isEditable,
                                            }}
                                            inputProps={
                                                {readOnly: true,}
                                            }
                                            value={pharmacyDetails["pharmacyName"]}
                                        />
                                    </Grid>
                                    <Grid item md={2} sx={4}>
                                        <Button variant="contained" onClick={handleSubmit}>
                                            {isEditable ? "Save" : "Edit"}
                                        </Button>
                                    </Grid>
                                </Grid>
                            </Paper>
                        </Grid>
                    </Grid>
                </Container>
            </Box>
        </Box>
    );
}
