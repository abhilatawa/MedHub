import React from "react";
import { useState, useEffect } from "react";
import {styled, useTheme} from "@mui/material/styles";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import Paper from "@mui/material/Paper";
import IconButton from "@mui/material/IconButton";
import SearchIcon from "@mui/icons-material/Search";
import SideNavBarPharmacycy from "../../components/SideNavBarPharmacy";
import AlertMessage from "../../components/AlertMessage";
import DataGridComponent from "../../components/DataGridComponet";
import TextField from "@mui/material/TextField";
import {getHistory} from "../../services/PharmacyProfileService";
import Button from "@mui/material/Button";
import {useNavigate} from "react-router";
import Modal from "@mui/material/Modal";

const DrawerHeader = styled("div")(({ theme }) => ({
    display: "flex",
    alignItems: "center",
    justifyContent: "flex-end",
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
}));


const style = {
    position: "absolute",
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 600,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    p: 4,
};
const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
    PaperProps: {
        style: {
            maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
            width: 550,
        },
    },
};
export default function Prescriptions() {
    const [searchQuery, setSearchQuery] = useState("");
    const [isAlert, setIsAlert] = useState(false);
    const [alertMessage, setAlertMessage] = useState("");
    const [alertStatus, setAlertStatus] = useState("");
    const [patientDetails, setPatientDetails] = useState([]);
    const [isEditable, setIsEditable] = useState(false);
    const [open, setOpen] = React.useState(false);
    const handleOpen = () => setOpen(true);
    const theme = useTheme();
    const handleClose = () => setOpen(false);
    const columns = [
        {field: 'date', headerName: 'DATE', width: 130},
        {field: 'doctor', headerName: 'DOCTOR NAME', width: 130},
        {field: 'patient', headerName: 'PATIENT NAME', width: 130},
        {field: 'prescription', headerName: 'PRESCRIPTION', width: 300},
        {
            field: "details",
            headerName: "Details",
            width: 90,
            renderCell: (params) => {
            return (
                <div>
                <Button
                variant="contained"
                size="small"
                style={{ background: "black" }}
                onClick={handleOpen}
                >
                View
            </Button>
                <Modal
                    open={open}
                    onClose={handleClose}
                    aria-labelledby="modal-modal-title"
                    aria-describedby="modal-modal-description"
                >
                    <Box sx={style}>
                        <Grid container spacing={3}>
                            <Grid item xs={12} sm={4}>
                                <TextField
                                    name="doctorname"
                                    label="Prescribing Doctor"
                                    type="text"
                                    id="doctor"
                                    value={params.row.doctor}
                                    fullWidth
                                    inputProps={
                                        {readOnly: true,}
                                    }
                                    InputProps={{
                                        readOnly: !isEditable,
                                    }}
                                />
                            </Grid>
                            <Grid item xs={12} sm={4}>
                                <TextField
                                    name="dctorEmail"
                                    label="Doctor Email"
                                    type="text"
                                    id="doctorEmail"
                                    value={params.row.doctorEmail}
                                    fullWidth
                                    inputProps={
                                        {readOnly: true,}
                                    }
                                    InputProps={{
                                        readOnly: !isEditable,
                                    }}
                                />
                            </Grid>
                            <Grid item xs={12} sm={4}>
                                <TextField
                                    name="dctorNo"
                                    label="Doctor PhoneNo"
                                    type="text"
                                    id="doctorNo"
                                    value={params.row.doctorNo}
                                    fullWidth
                                    inputProps={
                                        {readOnly: true,}
                                    }
                                    InputProps={{
                                        readOnly: !isEditable,
                                    }}
                                />
                            </Grid>
                            <Grid item xs={12} sm={4}>
                                <TextField
                                    name="patientaName"
                                    label="Patient Name"
                                    type="text"
                                    id="patient"
                                    value={params.row.patient}
                                    fullWidth
                                    inputProps={
                                        {readOnly: true,}
                                    }
                                    InputProps={{
                                        readOnly: !isEditable,
                                    }}
                                />
                            </Grid>
                            <Grid item xs={12} sm={4}>
                                <TextField
                                    name="patientEmail"
                                    label="Patient Email"
                                    type="text"
                                    id="patientEmail"
                                    value={params.row.patientEmail}
                                    fullWidth
                                    inputProps={
                                        {readOnly: true,}
                                    }
                                    InputProps={{
                                        readOnly: !isEditable,
                                    }}
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    name="prescription"
                                    label="Prescription"
                                    type="text"
                                    id="prescription"
                                    value={params.row.prescription}
                                    fullWidth
                                    rows={4}
                                    multiline
                                    inputProps={
                                        {readOnly: true,}
                                    }
                                    InputProps={{
                                        readOnly: !isEditable,
                                    }}
                                />
                            </Grid>
                        </Grid>
                        <p/>
                        <Button
                            onClick={handleClose}
                            type="submit"
                            variant="contained" tart
                            sx={{mt: 3, mb: 2}}
                        >
                            OK
                        </Button>
                    </Box>
                </Modal>
                </div>
            );
            },
        },
    ];
    // fetch details of the patient
    const fetchData = async () => {
        getHistory("")
            .then((res) => {
                // temp object to store doctor pending requests
                let data = [];

                for (let i = 0; i < res.historyDetails.length; i++) {
                    data.push({
                        id: res.historyDetails[i]["id"],
                        date: res.historyDetails[i]["appointmentDate"],
                        time: res.historyDetails[i]["timeSlot"],
                        dayOfWeek: res.historyDetails[i]["dayOfWeek"],
                        status:res.historyDetails[i]["status"],
                        remark:res.historyDetails[i]["remarksFromPatient"],
                        patient:res.historyDetails[i]["patientName"],
                        doctor:res.historyDetails[i]["doctorName"],
                        prescription:res.historyDetails[i]["prescription"],
                        pharmacistId:res.historyDetails[i]["pharmacistId"],
                        pharmacyName:res.historyDetails[i]["pharmacyName"],
                        createdAt:res.historyDetails[i]["createdAt"],
                        patientEmail:res.historyDetails[i]["patientEmail"],
                        doctorNo:res.historyDetails[i]["doctorContactNumber"],
                        doctorEmail:res.historyDetails[i]["doctorEmail"],
                    });
                }
                setPatientDetails(data);
            })
            .catch((err) => {
                console.log(err);
            });
    };
    const handleSearch = async () => {
        console.log(searchQuery);
        getHistory(searchQuery)
            .then((res) => {
                // temp object to store doctor pending requests
                let data = [];

                for (let i = 0; i < res.historyDetails.length; i++) {
                    data.push({
                        id: res.historyDetails[i]["id"],
                        date: res.historyDetails[i]["appointmentDate"],
                        time: res.historyDetails[i]["timeSlot"],
                        dayOfWeek: res.historyDetails[i]["dayOfWeek"],
                        status:res.historyDetails[i]["status"],
                        remark:res.historyDetails[i]["remarksFromPatient"],
                        patient:res.historyDetails[i]["patientName"],
                        doctor:res.historyDetails[i]["doctorName"],
                        prescription:res.historyDetails[i]["prescription"],
                        pharmacistId:res.historyDetails[i]["pharmacistId"],
                        pharmacyName:res.historyDetails[i]["pharmacyName"],
                        createdAt:res.historyDetails[i]["createdAt"],
                        patientEmail:res.historyDetails[i]["patientEmail"],
                        doctorNo:res.historyDetails[i]["doctorContactNumber"],
                        doctorEmail:res.historyDetails[i]["doctorEmail"],
                    });
                }
                setPatientDetails(data);
            })
            .catch((err) => {
                console.log(err);
            });
    };
    useEffect(() => {
        // call this function to fetch data
        fetchData();
    }, []);

    return (
        <Box sx={{ display: "flex" }}>
            <SideNavBarPharmacycy />
            {isAlert && <AlertMessage message={alertMessage} status={alertStatus} />}
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
                <DrawerHeader />
                <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <Paper sx={{ p: 2, display: "flex", flexDirection: "column" }}>
                                <Grid container spacing={2} sx={{ mt: 1 }}>
                                    <Grid item xs={12}>
                                        <TextField
                                            id="search-bar"
                                            className="text"
                                            onChange={(e) => {
                                                setSearchQuery(e.target.value);
                                            }}
                                            label="Enter a patient name"
                                            // variant="outlined"
                                            // placeholder="Search..."
                                            size="small"
                                        />
                                        <IconButton type="submit" aria-label="search" onClick={handleSearch}>
                                            <SearchIcon style={{ fill: "black" }} />
                                        </IconButton>
                                    </Grid>
                                    <p/>
                                    <Grid xs={12}>
                                        <DataGridComponent columns={columns} rows={patientDetails} />
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
