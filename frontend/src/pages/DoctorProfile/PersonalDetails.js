import * as React from "react";
import {styled} from "@mui/material/styles";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import Paper from "@mui/material/Paper";
import SideNavBarDoctor from "../../components/SideNavBarDoctor";
import TextField from '@mui/material/TextField';
import Button from "@mui/material/Button";
import {useState, useEffect} from "react";
import {useNavigate} from "react-router-dom";
import {Theme, useTheme} from '@mui/material/styles';
import OutlinedInput from '@mui/material/OutlinedInput';
import MenuItem from '@mui/material/MenuItem';
import Select, {SelectChangeEvent} from '@mui/material/Select';
import {getDoctorProfile,editDoctorDetails, uploadProfilePicture} from "../../services/DoctorProfileService";
import Typography from "@mui/material/Typography";
import Cookies from "js-cookie";

const DrawerHeader = styled("div")(({theme}) => ({
    display: "flex",
    alignItems: "center",
    justifyContent: "flex-end",
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
}));

const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
    PaperProps: {
        style: {
            maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
            width: 250,
        },
    },
};

const titles = [
    'M.B.B.S.',
    'M.S.',
    'M.D.',
    'B.D.S',
];
const specializations = [
    'Cardiology',
    'Dermatology',
    'Pediatrics',
];

function getStyles(name: string, personName: string[], theme: Theme) {
    return {
        fontWeight:
            personName.indexOf(name) === -1
                ? theme.typography.fontWeightRegular
                : theme.typography.fontWeightMedium,
    };
}

export default function PersonalDetails() {
    const [fname, setFname] = useState('');
    const [lnameError, setLnameError] = useState('');
    const navigate = useNavigate();
    const [file, setFile] = useState();
    const theme = useTheme();
    // used to display alert message to user.
    const [isAlert, setIsAlert] = useState(false);
    const [alertMessage, setAlertMessage] = useState("");
    const [alertStatus, setAlertStatus] = useState("");
    const [persontitle, setPersonTitle] = useState("");
    const [personSpecialization, setPersonSpecialization] = useState([]);
    const [isEditable, setIsEditable] = useState(false);
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
        profilePictureBase64String: ""
    });
   
    const [image, setImage] = useState(null);

    // api call to fetch the details of the doctor
    const fetchData = async () => {
        getDoctorProfile()
            .then((res) => {
                const data = res.doctorProfileDetails;
                // console.log(data);
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
                    profilePictureBase64String: data["profilePictureBase64String"]
                });
            })
            .catch((err) => {
                console.log(err);
            });
    }
    const handleChangeSpecialization = (event: SelectChangeEvent<typeof personSpecialization>) => {
        console.log(event.target.value)
        const {
            target: {value},
        } = event;
        setPersonSpecialization(
            // On autofill we get a stringified value.
            typeof value === 'string' ? value.split(',') : value,
        );
        setDoctorDetails({
            ...doctorDetails,
            specializationsOfDoctor: event.target.value,
        })
    };

    function getFile(e) {

        const image = e.target.files[0];
        setFile(image);
        // console.log(e.target.files[0]["name"]);
        // setFile(URL.createObjectURL(e.target.files[0]));
        // setDoctorDetails["profilePictureBase64String"] = e.target.files[0]["name"];
    }

    const handleSubmit = (event) => {
        event.preventDefault();

        // set alert to false
        setIsAlert(false);
        // reverse the value of the isEditable
        setIsEditable(!isEditable);

        if (isEditable) {
            // make api call to edit the details
            editDoctorDetails(doctorDetails).then((res) => {
                setIsAlert(true);
                setAlertMessage(res["message"]);
                setAlertStatus(res["status"]);
            });
        }
    };

    const uploadImage = (e) => {
        e.preventDefault();

        if(file) {
            const formData = new FormData();
            formData.append('profilePicture', file);
            formData.append('doctorId', doctorDetails["id"])
            console.log(formData);

            uploadProfilePicture(formData)
            .then((res) => {
                console.log(res);
            })

        }
    }

    // const handleUpload = (e) => {
    //     e.preventDefault();


    // }

    // const handleFileChange = () => {

    // }

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
                                <Typography
                                    component="h2"
                                    variant="h6"
                                    color="primary"
                                    gutterBottom
                                >
                                    Doctor Profile
                                </Typography>
                                <Grid item xs={12} sm={3}>
                                    <img src={"data:image/jpeg;base64, " + doctorDetails["profilePictureBase64String"]}
                                         style={{width: 200, height: 210, borderRadius: "50%", border: "solid black"}}/>
                                    {/* <form onSubmit={handleUpload}>
                                        <input type="file" onChange={handleFileChange}/>
                                        <Button type="submit" variant="contained">Upload</Button>
                                    </form> */}

                                    <input type="file" accept="image/jpeg" onChange={getFile}/>
                                    <Button onClick={uploadImage} variant="contained">Upload</Button>
                                </Grid>
                                <p/>
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
                                            value={doctorDetails["firstName"]}
                                            onChange={(e) => {
                                                let value = e.target.value
                                                value = value.replace(/[^A-Za-z]/ig, '')
                                                e.target.value = value
                                                setDoctorDetails({
                                                    ...doctorDetails,
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
                                            value={doctorDetails["lastName"]}
                                            onChange={(e) => {
                                                let value = e.target.value
                                                value = value.replace(/[^A-Za-z]/ig, '')
                                                e.target.value = value
                                                setDoctorDetails({
                                                    ...doctorDetails,
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
                                            value={doctorDetails["username"]}
                                            onChange={(e) => {
                                                let value = e.target.value
                                                value = value.replace(/[^A-Za-z]/ig, '')
                                                e.target.value = value
                                                setDoctorDetails({
                                                    ...doctorDetails,
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
                                            value={doctorDetails["contactNumber"]}
                                            onChange={(e) => {
                                                setDoctorDetails({
                                                    ...doctorDetails,
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
                                            value={doctorDetails["addressLine1"]}
                                            onChange={(e) => {
                                                setDoctorDetails({
                                                    ...doctorDetails,
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
                                            value={doctorDetails["addressLine2"]}
                                            onChange={(e) => {
                                                setDoctorDetails({
                                                    ...doctorDetails,
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
                                            value={doctorDetails["postalCode"]}
                                            onChange={(e) => {
                                                setDoctorDetails({
                                                    ...doctorDetails,
                                                    postalCode: e.target.value,
                                                });
                                            }}
                                        />
                                    </Grid>
                                    <Grid item xs={12}>
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
                                            value={doctorDetails["licenseNumber"]}
                                        />
                                    </Grid>
                                <Grid item xs={12}>
                                    <label style={{color: 'black', fontSize: '20px'}}>Specialization</label>
                                </Grid>
                                <Grid item xs={12}>
                                        <Select
                                            labelId="multiple-specialization-label"
                                            id="multiple-specialization"
                                            multiple
                                            fullWidth
                                            defaultValue={doctorDetails["specializationsOfDoctor"]}
                                            value={personSpecialization}
                                            onChange={handleChangeSpecialization}
                                            input={<OutlinedInput label="Name"/>}
                                            MenuProps={MenuProps}
                                        >
                                            {specializations.map((specializations) => (
                                                <MenuItem
                                                    key={specializations}
                                                    value={specializations}
                                                    style={getStyles(specializations, personSpecialization, theme)}
                                                >
                                                    {specializations}
                                                </MenuItem>
                                            ))}
                                        </Select>
                                    </Grid>
                                    <Grid item xs={12}>
                                        <label style={{color: 'black', fontSize: '20px'}}>Work Experience </label>
                                    </Grid>
                                    <Grid item xs={12} sm={6}>
                                        <TextField
                                            required
                                            name="title"
                                            label="Title"
                                            type="text"
                                            id="title"
                                            InputProps={{
                                                readOnly: !isEditable,
                                            }}
                                            fullWidth
                                            value={doctorDetails["jobExpTitle"]}
                                            onChange={(e) => {
                                                setDoctorDetails({
                                                    ...doctorDetails,
                                                    jobExpTitle: e.target.value,
                                                });
                                            }}
                                        />
                                    </Grid>
                                    <Grid item xs={12} sm={6} >
                                        <TextField
                                            required
                                            id="description"
                                            label="Job description"
                                            name="description"
                                            fullWidth
                                            multiline
                                            InputProps={{
                                                readOnly: !isEditable,
                                            }}
                                            value={doctorDetails["jobDescription"]}
                                            onChange={(e) => {
                                                setDoctorDetails({
                                                    ...doctorDetails,
                                                    jobDescription: e.target.value,
                                                });
                                            }}
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
