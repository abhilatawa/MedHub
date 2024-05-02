import * as React from "react";
import {styled, Theme, useTheme} from "@mui/material/styles";
import Box from "@mui/material/Box";
import SideNavBarDoctor from "../../components/SideNavBarDoctor";
import { useNavigate } from "react-router";
import Button from "@mui/material/Button";
import Container from "@mui/material/Container";
import Modal from '@mui/material/Modal';
import Grid from "@mui/material/Grid";
import TextField from "@mui/material/TextField";
import Paper from "@mui/material/Paper";
import {useEffect, useState} from "react";
import {getAppointments, getPharmacy, updateAppoinment} from "../../services/DoctorProfileService";
import {MenuItem, Select} from "@mui/material";
import OutlinedInput from "@mui/material/OutlinedInput";
import {DataGrid} from "@mui/x-data-grid";
import InputLabel from '@mui/material/InputLabel';



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
    width: 400,
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
            width: 250,
        },
    },
};
export default function AppoinmentDetails() {
    const [newPrescription, setNewPrescription] = useState("");
    const [appoinmentDetail, setAppoinmentDetail] = useState([]);
    const [prescriptionDetail, setPrescriptionDetail] = useState([]);
    const [pharmacy, setPharmacy] = useState([]);
    const [rowId, setRowId] = useState();
    const [pharmacySelection, setPharmacySelection] = useState([]);
    const [pharmacyDetail, setPharmacyDetail] = useState([]);
    const [open, setOpen] = React.useState(false);
    const handleOpen = () => setOpen(true);
    const theme = useTheme();
    const handleClose = () => setOpen(false);



    const columns = [
        { field: 'id', headerName: 'ID', width: 0 },
        { field: 'date', headerName: 'DATE', width: 130 },
        { field: 'time', headerName: 'TIME', width: 130 },
        { field: 'status', headerName: 'STATUS', width: 130 },
        { field: 'dayOfWeek', headerName: 'dayOfWeek', width: 130 },
        { field: 'patientName', headerName: 'patientName', width: 130 },
        { field: 'doctorName', headerName: 'doctorName', width: 130 },
        { field: 'pharmacistId', headerName: 'pharmacistId', width: 130 },
        { field: 'PharmacyName', headerName: 'PharmacyName', width: 130 },
        { field: 'createdAt', headerName: 'createdAt', width: 130 },
        { field: 'appoinmentDetails', headerName: 'APPOINMENT DETAILS', width: 300 },
        {
            field: "prescription",
            width: 190,
            headerName: "PRESCRIPTION",
            renderCell: (params) => {
                return (
                    <div>
                        <Button
                            variant="contained"
                            size="small"
                            style={{background: "primary"}}
                            onClick={()=>{
                                handleOpen();
                                setRowId(params.row.id);
                            }
                            }
                        >
                            Add Prescription
                        </Button>
                        <Modal
                            open={open}
                            onClose={handleClose}
                            aria-labelledby="modal-modal-title"
                            aria-describedby="modal-modal-description"
                        >
                            <Box sx={style}>
                                <Grid container spacing={3}>
                                    <Grid item xs={12}>
                                        <InputLabel id="label">Select Pharmacy</InputLabel>
                                        <Select
                                            labelId="pharmacy"
                                            id="pharmacy"
                                            label="Pharmacy"
                                            fullWidth
                                            defaultValue=""
                                            value={pharmacySelection}
                                            onChange={handlePharmacySelection}
                                            input={<OutlinedInput label="Pharmacy"/>}
                                            MenuProps={MenuProps}
                                        >
                                            {pharmacy.map((pharmacy) => (
                                                <MenuItem
                                                    key={pharmacy}
                                                    value={pharmacy}
                                                    // style={getStyles(pharmacy, pharmacySelection, theme)}
                                                >
                                                    {pharmacy}
                                                </MenuItem>
                                            ))}
                                        </Select>
                                    </Grid>
                                    <Grid item xs={12}>
                                        <TextField
                                            required
                                            name="prescription"
                                            label="Prescription"
                                            type="text"
                                            id="prescription"
                                            onChange={(e) => {
                                                setNewPrescription(e.target.value)
                                            }}
                                            multiline
                                            fullWidth
                                            rows={4}
                                        />
                                    </Grid>
                                </Grid>
                                <p/>
                                <Button
                                    onClick={updateAppoinmentDetails}
                                    type="submit"
                                    variant="contained" tart
                                    sx={{mt: 3, mb: 2}}
                                >
                                    Save
                                </Button>
                            </Box>
                        </Modal>
                    </div>
            )
                ;
            },
        },

    ];

    const handlePharmacySelection = (event) => {
        const {
            target: {value},
        } = event;
        setPharmacySelection(
            // On autofill we get a stringified value.
            typeof value === 'string' ? value.split(',') : value,
        );

    };
    const fetchData = async () => {
        getAppointments(true)
            .then((res) => {
                // temp object to store doctor pending requests
                let data = [];
                for (let i = 0; i < res.appoinmentDetails.length; i++) {
                    data.push({
                        id: res.appoinmentDetails[i]["id"],
                        date: res.appoinmentDetails[i]["appointmentDate"],
                        time: res.appoinmentDetails[i]["timeSlot"],
                        patientName: res.appoinmentDetails[i]["patientName"],
                        status: res.appoinmentDetails[i]["status"],
                        appoinmentDetails: res.appoinmentDetails[i]["remarksFromPatient"],
                        dayOfWeek: res.appoinmentDetails[i]["dayOfWeek"],
                        doctorName: res.appoinmentDetails[i]["doctorName"],
                        prescription: res.appoinmentDetails[i]["prescription"],
                        pharmacistId: res.appoinmentDetails[i]["pharmacistId"],
                        pharmacyName: res.appoinmentDetails[i]["pharmacyName"],
                        createdAt: res.appoinmentDetails[i]["createdAt"],
                    });
                }
                setAppoinmentDetail(data);
            })
            .catch((err) => {
                console.log(err);
            });
    };
    const fetchPharmacy = async () => {
        getPharmacy("")
            .then((res) => {
                let pharmacyNames = [];
                let pharmacyDetails = [];

                // Extract pharmacy names and details
                for (let i = 0; i < res.pharmacyDetails.length; i++) {
                    const pharmacy = res.pharmacyDetails[i];
                    pharmacyNames.push(pharmacy.pharmacyName);
                    pharmacyDetails.push({ id: pharmacy.id, name: pharmacy.pharmacyName });
                }
                setPharmacy(pharmacyNames);
                setPharmacyDetail(pharmacyDetails);
            })
            .catch((err) => {
                console.log(err);
            });
    };

    const updateAppoinmentDetails = (event) => {
        const result=appoinmentDetail.find(obj => obj.id === rowId);
        const pharmacistName=pharmacySelection[0];
        const pharmacyId=pharmacyDetail.find(obj => obj.name === pharmacistName).id;
        setPrescriptionDetail({
            "id": result.id,
            "appointmentDate":result.date,
            "timeSlot": result.time,
            "status": result.status,
            "remarksFromPatient": result.appoinmentDetails,
            "dayOfWeek": result.dayOfWeek,
            "patientName": result.patientName,
            "doctorName": result.doctorName,
            "prescription": newPrescription,
            "pharmacistId": pharmacyId,
            "pharmacyName": pharmacistName,
            "createdAt": result.createdAt
        });
    };
    useEffect(() => {
        // call this function to fetch data
        fetchData();
        fetchPharmacy();
        if (prescriptionDetail) {
            updateAppoinment(prescriptionDetail)
                .then((res) => {
                    handleClose();
                })
                .catch((err) => {
                    console.error("Error updating appointment:", err);
                });
        }
    }, [prescriptionDetail]);
    const navigate = useNavigate();
    return (
        <Box component="form" noValidate sx={{ display: "flex" }}>
            <SideNavBarDoctor />
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
                <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
                    <Grid container spacing={3}>
                            <Grid item xs={12}>
                                <Paper sx={{ p: 2, display: "flex", flexDirection: "column" }}>
                                    <div style={{height: 400, width: '100%'}}>
                                        <DataGrid columns={columns} rows={appoinmentDetail} columnVisibilityModel={{id:false,status:false,dayOfWeek:false,remarksFromPatient:false,doctorName:false,pharmacistId:false,PharmacyName:false,createdAt:false}} />
                                    </div>
                                </Paper>
                            </Grid>
                    </Grid>
                </Container>
            </Box>
        </Box>
    );
}
