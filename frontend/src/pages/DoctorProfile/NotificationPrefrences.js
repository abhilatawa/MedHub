import * as React from "react";
import {useEffect, useState} from "react";
import Switch from "@mui/material/Switch";
import { useNavigate } from "react-router";
import Typography from "@mui/material/Typography";
import Grid from "@mui/material/Grid";
import SideNavBarDoctor from "../../components/SideNavBarDoctor";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import Paper from "@mui/material/Paper";
import {styled} from "@mui/material/styles";
import {patientSignUp} from "../../services/PatientSignupService";
import Cookies from "js-cookie";
import {setEmailNotificationPreference} from "../../services/DoctorNotificationService";
import {getDoctorProfile} from "../../services/DoctorProfileService";


export default function NotificationPreferences() {
    const [notificationEnabled, setNotificationEnabled] = useState(false);
    const navigate = useNavigate();
    const [show,setshow] =useState();
    const [image, setImage] = useState(null);
    const [doctorDetails, setDoctorDetails] = useState({
        id:0,
        firstName: "",
        lastName: "",
        username: "",
        contactNumber: "",
        addressLine1: "",
        addressLine2: "",
        postalCode: "",
        licenseNumber: "",
        jobExpTitle: "",
        jobDescription: "",
        specializationsOfDoctor: [],
        startTime: "",
        endTime: "",
        sunday: false,
        monday: false,
        tuesday: false,
        wednesday: false,
        thursday: false,
        friday: false,
        saturday: false,
        profilePictureBase64String: "",
        receiveEmailNotification:false
    });

    useEffect(() => {
        // call this function to fetch data
        fetchData();
        setNotificationEnabled(doctorDetails.receiveEmailNotification);
    }, []);

        // Update notificationEnabled state when doctorDetails is fetched
    useEffect(() => {

        setNotificationEnabled(doctorDetails.receiveEmailNotification);
    }, [doctorDetails]);

             const DrawerHeader = styled("div")(({theme}) => ({
                 display: "flex",
                 alignItems: "center",
                 justifyContent: "flex-end",
                 padding: theme.spacing(0, 1),
                 // necessary for content to be below app bar
                 ...theme.mixins.toolbar,
             }));
             const handleNotificationToggle = () => {
                 // event.preventDefault();
                 setNotificationEnabled((prev) => !prev);
                 const data = new FormData();
                 data.append("preference", !notificationEnabled)
                 setEmailNotificationPreference(data)
                     .then((res) => {
                         console.log(res);
                         console.log(Cookies.get('accessToken'));
                     })
                     .catch((err) => {
                         console.log(err);
                     });
             };



// api call to fetch the details of the doctor
             const fetchData = async () => {
                 getDoctorProfile()
                     .then((res) => {
                         const data = res.doctorProfileDetails;
                         console.log("Data from backend initial", data);
                         setDoctorDetails({
                             id:data["id"],
                             firstName: data["firstName"],
                             lastName: data["lastName"],
                             username: data["username"],
                             contactNumber: data["contactNumber"],
                             addressLine1: data["addressLine1"],
                             addressLine2: data["addressLine2"],
                             postalCode: data["postalCode"],
                             licenseNumber: data["licenseNumber"],
                             jobExpTitle: data["jobExpTitle"],
                             jobDescription: data["jobDescription"],
                             specializationsOfDoctor: data["specializationsOfDoctor"],
                             startTime: data["startTime"],
                             endTime: data["endTime"],
                             sunday: data["sunday"],
                             monday: data["monday"],
                             tuesday: data["tuesday"],
                             wednesday: data["wednesday"],
                             thursday: data["thursday"],
                             friday: data["friday"],
                             saturday: data["saturday"],
                             profilePictureBase64String: data["profilePictureBase64String"],
                             receiveEmailNotification: data["receiveEmailNotification"]

                         });
                     })
                     .catch((err) => {
                         console.log(err);
                     });
             }
             return (

                 <Box component="form" noValidate sx={{display: "flex"}}>
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
                             <Grid container spacing={2}>
                                 <Grid item xs={12}>
                                     <Paper sx={{p: 2, display: "flex", flexDirection: "column"}}>
                                         <Typography variant="h6" gutterBottom>
                                             Notification Preferences
                                         </Typography>
                                         <Switch
                                             //checked={doctorDetails["receiveEmailNotification"]}
                                             checked={notificationEnabled}
                                             onChange={handleNotificationToggle}
                                             inputProps={{ 'aria-label': 'notification-preferences-switch' }}
                                         />

                                     </Paper>
                                 </Grid>
                             </Grid>
                         </Container>
                     </Box>
                 </Box>
             );
     }
