import * as React from "react";
import {styled} from "@mui/material/styles";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import Paper from "@mui/material/Paper";
import SideNavBarDoctor from "../../components/SideNavBarDoctor";
import Button from "@mui/material/Button";
import {useEffect, useState} from "react";
import {LocalizationProvider} from "@mui/x-date-pickers/LocalizationProvider";
import {AdapterDayjs} from "@mui/x-date-pickers/AdapterDayjs";
import {TimePicker} from '@mui/x-date-pickers/TimePicker';
import FormGroup from '@mui/material/FormGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import {editDoctorDetails, getDoctorProfile} from "../../services/DoctorProfileService";
import dayjs from 'dayjs';

const DrawerHeader = styled("div")(({theme}) => ({
    display: "flex",
    alignItems: "center",
    justifyContent: "flex-end",
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
}));


export default function AvailabilityDetails() {
    const [value, setValue] = useState();
    const [endValue, setEndValue] = useState();
    const [trgDate, setTrgDate] = useState();
    const [availability, setAvailability] = useState("");
    const [availabilityError, setAvailabilityError] = useState("");
    const [isAlert, setIsAlert] = useState(false);
    const [alertMessage, setAlertMessage] = useState("");
    const [alertStatus, setAlertStatus] = useState("");
    const handleAvailabilityChange = (event) => {
        setAvailability(event.target.value);
    };
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
    });
    const fetchData = async () => {
        getDoctorProfile()
            .then((res) => {
                const data = res.doctorProfileDetails;
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
        editDoctorDetails(doctorDetails).then((res) => {
            setIsAlert(true);
            setAlertMessage(res["message"]);
            setAlertStatus(res["status"]);
        });
    };

    const handleCheckboxChange=(checkBoxName)=> {
        setDoctorDetails((previous)=> ({
            ...previous,[checkBoxName]:!previous[checkBoxName]
        }));
    }
    useEffect(() => {
        // call this function to fetch data
        fetchData();
    }, []);
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
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <Paper sx={{p: 2, display: "flex", flexDirection: "column"}}>
                                <Grid container spacing={2}>
                                    <Grid item xs={12} sm={3}>
                                        <LocalizationProvider dateAdapter={AdapterDayjs} fullWidth>
                                            <TimePicker
                                                label="Start Time"
                                                value={dayjs(doctorDetails["startTime"])}
                                                // onChange={value=>setValue(value)}
                                                onChange={(e) => {
                                                    setDoctorDetails({ ...doctorDetails, startTime: e });
                                                }}
                                            />
                                        </LocalizationProvider>
                                    </Grid>
                                    <Grid item xs={12} sm={3}>
                                        <LocalizationProvider dateAdapter={AdapterDayjs} fullWidth>
                                            <TimePicker
                                                label="End Time"
                                                value={dayjs(doctorDetails["endTime"])}
                                                // onChange={
                                                //     endValue=>setEndValue(endValue)
                                                // }
                                                onChange={(e) => {
                                                    setDoctorDetails({ ...doctorDetails, endTime: e });
                                                }}
                                            />
                                        </LocalizationProvider>
                                    </Grid>
                                    <Grid item xs={12}>
                                        <FormGroup>
                                            {/*<FormControlLabel enabled defaultChecked={doctorDetails["monday"]}  control={<Checkbox/>} label="Monday" />*/}
                                            {/*<FormControlLabel enabled defaultChecked={doctorDetails["tuesday"]} control={<Checkbox/>} label="Tuesday" onSelect={doctorDetails["monday"]=true}/>*/}
                                            {/*<FormControlLabel enabled defaultChecked={doctorDetails["wednesday"]} control={<Checkbox/>} label="Wednesday" onSelect={doctorDetails["monday"]=true}/>*/}
                                            {/*<FormControlLabel enabled defaultChecked={doctorDetails["thursday"]} control={<Checkbox/>} label="Thursday" onSelect={doctorDetails["monday"]=true}/>*/}
                                            {/*<FormControlLabel enabled defaultChecked={doctorDetails["friday"]} control={<Checkbox/>} label="Friday" onSelect={doctorDetails["monday"]=true}/>*/}
                                            {/*<FormControlLabel enabled defaultChecked={doctorDetails["saturday"]} control={<Checkbox/>} label="Saturday" onSelect={doctorDetails["monday"]=true}/>*/}
                                            {/*<FormControlLabel enabled defaultChecked={doctorDetails["sunday"]} control={<Checkbox/>} label="Sunday" onSelect={doctorDetails["monday"]=true}/>*/}
                                            <FormControlLabel enabled checked={doctorDetails["monday"]} onChange={()=>handleCheckboxChange("monday")} control={<Checkbox/>} label="Monday" />
                                            <FormControlLabel enabled checked={doctorDetails["tuesday"]} onChange={()=>handleCheckboxChange("tuesday")}  control={<Checkbox/>} label="Tuesday" />
                                            <FormControlLabel enabled checked={doctorDetails["wednesday"]} onChange={()=>handleCheckboxChange("wednesday")}  control={<Checkbox/>} label="Wednesday" />
                                            <FormControlLabel enabled checked={doctorDetails["thursday"]} onChange={()=>handleCheckboxChange("thursday")}  control={<Checkbox/>} label="Thursday" />
                                            <FormControlLabel enabled checked={doctorDetails["friday"]} onChange={()=>handleCheckboxChange("friday")}  control={<Checkbox/>} label="Friday" />
                                            <FormControlLabel enabled checked={doctorDetails["saturday"]} onChange={()=>handleCheckboxChange("saturday")}  control={<Checkbox/>} label="Saturday"/>
                                            <FormControlLabel enabled checked={doctorDetails["sunday"]} onChange={()=>handleCheckboxChange("sunday")}  control={<Checkbox/>} label="Sunday" />
                                        </FormGroup>
                                    </Grid>
                                    <Grid item md={2} sx={4}>
                                    <Button
                                        type="submit"
                                        variant="contained" tart
                                        sx={{mt: 3, mb: 2}}
                                    >
                                        Save
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
