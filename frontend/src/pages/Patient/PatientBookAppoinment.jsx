import * as React from "react";
import {styled} from "@mui/material/styles";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import Paper from "@mui/material/Paper";
import SideNavBarPatient from "../../components/SideNavBarPatient";
import Button from "@mui/material/Button";
import {useEffect, useState} from "react";
import {LocalizationProvider} from "@mui/x-date-pickers/LocalizationProvider";
import {AdapterDayjs} from "@mui/x-date-pickers/AdapterDayjs";
import dayjs, { Dayjs } from 'dayjs';
import {DatePicker} from "@mui/x-date-pickers/DatePicker";
import {addAppoinmentDetails, getAppoinmentSlot,getDoctorProfile} from "../../services/PatientBookAppoinmentService.js";
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select, { SelectChangeEvent } from '@mui/material/Select';
import Typography from "@mui/material/Typography";
import {useParams} from "react-router";
import TextField from "@mui/material/TextField";
import CssBaseline from "@mui/material/CssBaseline";
import AlertMessage from "../../components/AlertMessage";

const DrawerHeader = styled("div")(({theme}) => ({
    display: "flex",
    alignItems: "center",
    justifyContent: "flex-end",
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
}));


export default function PatientBookAppinment() {
    const [value, setValue] = useState();
    const [isAlert, setIsAlert] = useState(false);
    const [alertMessage, setAlertMessage] = useState("");
    const [alertStatus, setAlertStatus] = useState("");
    const tomorrow = dayjs().add(1, 'day');
    const endDate=dayjs().endOf('week');
    const [time, setTime] = React.useState('');
    let { id } = useParams();
    const [dayOfWeek, setDayOfWeek] = useState('');


    // create a request data
    const reqData = { id: id };
    const handleChange = (event) => {
        setTime(event.target.value);
        setAppoinmentDetails({
            ...appoinmentDetails,
            doctorId: parseInt(id),
            timeSlot: event.target.value
        });
    };
    const [appoinmentDetails, setAppoinmentDetails] = useState({
        doctorId:0,
        appointmentDate: "",
        remarksFromPatient: "",
        timeSlot: ""});
    const handleBookAppoinment = (event) => {
        addAppoinmentDetails(appoinmentDetails).then((res) => {
            setIsAlert(true);
            setAlertMessage(res["message"]);
            setAlertStatus(res["status"]);
        });
    };
    const [filteredDoctors, setFilteredDoctors] = useState([]);
    const [specializationFilter, setSpecializationFilter] = useState([]);
    const [doctorDetails, setDoctorDetails] = useState({
        id: 0,
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
        receiveEmailNotification: false,
        specializationsOfDoctor: []
    });
    const [appoinmentSlot, setAppoinmentSlot] = useState({
        sunday: [],
        monday: [],
        tuesday: [],
        wednesday: [],
        thursday: [],
        friday: [],
        saturday: [],
    });
    const [appointmentSlots, setAppointmentSlots] = useState({});

    const fetchData = () => {
        getAppoinmentSlot(id)
            .then((res) => {
                if (res.status) {
                    setAppointmentSlots(res.appointmentDetails);
                } else {
                    console.error("Failed to fetch appointment slots");
                }
            })
            .catch((err) => {
                console.log(err);
            });
    };


    const fetchDoctorData = async () => {
        getDoctorProfile(id)
            .then((res) => {
                const data = res.doctorProfileDetails;
                console.log(data);

                // console.log(data);
                setDoctorDetails({
                    id: data["id"],
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
                    receiveEmailNotification:data["receiveEmailNotification"],
                    specializationsOfDoctor: data["specializationsOfDoctor"],
                });
            })
            .catch((err) => {
                console.log(err);
            });
    };
    const handleSubmit = (event) => {
        event.preventDefault();
        // set alert to false
        setIsAlert(false)
        setAppoinmentDetails({
            ...appoinmentDetails,
            doctorId: parseInt(id),
            timeSlot: time
        });
        addAppoinmentDetails(appoinmentDetails).then((res) => {
            setIsAlert(true);
            setAlertMessage(res["message"]);
            setAlertStatus(res["status"]);
        });
    };

    const identifyDay = (event) => {
     // Extract day of the week using Day.js
        const dayOfWeek = dayjs(event.$d).format('dddd').toLowerCase();
        setAppoinmentDetails({
            ...appoinmentDetails,
            appointmentDate: dayjs(event.$d).format('YYYY-MM-DD'),
        });
        setDayOfWeek(dayOfWeek);
        console.log(dayOfWeek);
        fetchData();
    };

    useEffect(() => {
        // call this function to fetch data
        fetchDoctorData();

    }, []);
    return (
        <Box component="form" noValidate onSubmit={handleSubmit} sx={{display: "flex"}}>
            <CssBaseline />
            {isAlert && (
                <AlertMessage message={alertMessage} status={alertStatus} />
            )}
            <SideNavBarPatient/>
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
                                        <Typography
                                            variant='h4'>{doctorDetails["firstName"]} {doctorDetails["lastName"]}</Typography>
                                        <Typography variant='body1'>
                                            {doctorDetails["addressLine1"]}<br></br>
                                            {doctorDetails["addressLine2"]}<br></br><br></br>
                                            Specializations: {doctorDetails["specializationsOfDoctor"]}
                                        </Typography>
                                    </Grid>
                                    <Grid item xs={12} sm={6}>
                                        <LocalizationProvider dateAdapter={AdapterDayjs}>
                                            <DatePicker
                                                label="Date"
                                                minDate={tomorrow}
                                                maxDate={endDate}
                                                value={value}
                                                onChange={(newValue) => {
                                                    setValue(newValue);
                                                    identifyDay(newValue);
                                                }
                                                }
                                                fullWidth
                                            />

                                        </LocalizationProvider>
                                    </Grid>
                                </Grid>
                                <p/>
                                <Grid container spacing={2}>
                                    <Grid item xs={12} sm={3}>
                                        <FormControl fullWidth>
                                            <InputLabel id="time">Time</InputLabel>
                                            <Select
                                                displayEmpty
                                                labelId="appoinmentTime"
                                                id="appoinmentTime"
                                                value={time}
                                                label="Time"
                                                onChange={handleChange}
                                                sx={{width: '100%'}}
                                            >
                                                {appointmentSlots[dayOfWeek] &&
                                                    appointmentSlots[dayOfWeek].map((slot, index) => (
                                                        <MenuItem key={index} value={slot}>
                                                            {slot}
                                                        </MenuItem>
                                                    ))}
                                            </Select>
                                        </FormControl>
                                    </Grid>
                                </Grid>
                                <p/>
                                <Grid container spacing={2}>
                                    <Grid item xs={12} sm={6}>
                                        <TextField
                                            name="remarks"
                                            id="remarks"
                                            label="Remarks"
                                            fullWidth
                                            multiline
                                            onChange={(e) => {
                                                setAppoinmentDetails({
                                                    ...appoinmentDetails,
                                                    doctorId: parseInt(id),
                                                    remarksFromPatient: e.target.value,
                                                    timeSlot: time
                                                });
                                            }}
                                        />
                                    </Grid>
                                </Grid>
                                    <Grid container spacing={2}>
                                        <Grid item md={4} sx={4}>
                                            <Button
                                                type="submit"
                                                variant="contained" tart
                                                sx={{mt: 3, mb: 2}}
                                                onClick={handleSubmit}
                                            >
                                                Book Appoinment
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
