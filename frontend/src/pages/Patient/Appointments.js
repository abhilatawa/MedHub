import * as React from "react";
import {styled} from "@mui/material/styles";
import Box from "@mui/material/Box";
import {useEffect, useState} from "react";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import Paper from "@mui/material/Paper";
import CssBaseline from "@mui/material/CssBaseline";
import SideNavBarPatient from "../../components/SideNavBarPatient";
import {getAppointmentDetails} from "../../services/PatientBookAppoinmentService";
import DataGridComponent from "../../components/DataGridComponet";
import Button from "@mui/material/Button";
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import Rating from '@mui/material/Rating';
import TextField from '@mui/material/TextField';
import {provideFeedback} from "../../services/ProvideFeedbackService";
import AlertMessage from "../../components/AlertMessage";
import {useNavigate} from "react-router-dom";


const DrawerHeader = styled("div")(({theme}) => ({
    display: "flex",
    alignItems: "center",
    justifyContent: "flex-end",
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
}));

export default function Appointments() {

    const navigate = useNavigate();

    // State to store the full list of appointment details
    const [appoinmentDetail, setAppoinmentDetail] = useState([]);

    // State to store appointment detail to be used for feedback
    const [feedbackDetail, setFeedbackDetail] = useState(null);

    // State to control the Dialog popup
    const [openDialog, setOpenDialog] = useState(false);

    // States for storing feedback from the user
    const [rating, setRating] = useState(0);
    const [feedback, setFeedback] = useState('');

    // States used to display alert message to user
    const [isAlert, setIsAlert] = useState(false);
    const [alertMessage, setAlertMessage] = useState("");
    const [alertStatus, setAlertStatus] = useState("");

    // Function to store the appointment details when the "Give feedback" button is clicked, to be used in the ProvideFeedback API
    const handleClick = (rowData) => {
        const appointment = appoinmentDetail.find(appointment =>
            appointment.time === rowData.time &&
            appointment.date === rowData.date
        );
        setFeedbackDetail(appointment);
        if (appointment) {
            const rowData = appointment.id;
            console.log('Appointment ID:', rowData);
        } else {
            console.log('Appointment not found.');
        }
        console.log(appointment);
        setOpenDialog(true);
    };

    // Function to handle the "cancel" option when user opens the feedback popup
    const handleCloseDialog = () => {
        setOpenDialog(false);
    };

    // Function to handle updating the feedback for the appointment when the user clicks "submit" in the feedback popup
    const handleProvideFeedback = (event) => {
        event.preventDefault();
        const feedbackData = {
            createdAt: feedbackDetail.createdAt,
            date: feedbackDetail.date,
            day: feedbackDetail.day,
            doctor: feedbackDetail.doctor,
            feedbackMessage:feedback,
            id: feedbackDetail.id,
            patient: feedbackDetail.id.patient,
            rating:rating,
            remark: feedbackDetail.remark,
            status: feedbackDetail.status,
            time: feedbackDetail.time
        };
        console.log(feedbackData);

        if (
            feedbackData.feedbackMessage !== "" && feedbackData.rating !== null
        ) {
            provideFeedback(feedbackData)
                .then((res) => {
                    // Modify alert to say feedback was created successfully
                    setIsAlert(true);
                    setAlertMessage("Feedback submitted successfully");
                    setAlertStatus("success");

                    // If the submit is successful then close the popup
                    setOpenDialog(false)

                    // Fetch the appointment data again to refresh the information
                    fetchData();
                })
                .catch((err) => {
                    console.log(err);
                });
        }
    };

    const columns = [
        {field: 'date', headerName: 'DATE', width: 130},
        {field: 'time', headerName: 'TIME', width: 130},
        {field: 'doctor', headerName: 'DOCTOR NAME', width: 130},
        {field: 'status', headerName: 'APPOINTMENT STATUS', width: 250},
        {field: 'remark', headerName: 'REMARKS', width: 250},
        {field: 'feedback', headerName: '', width: 175,
            // Render a "Provide feedback" button if the appointment has passed and there is no feedback already stored
            renderCell: (params) => {
                if (params.row.status === 'COMPLETED') {
                    if (params.row.feedbackMessage === null || params.row.feedbackMessage === "") {
                        return (
                            <Button
                                variant="contained"
                                size="small"
                                onClick={() => handleClick(params.row)}
                            >
                                Provide feedback
                            </Button>
                        );
                    } else {
                        // Otherwise, display the message that feedback has already been provided
                        return <span>Feedback submitted</span>;
                    }
                } else {
                    return null;
                }
            },
        },

    ];
    const fetchData = async () => {
        getAppointmentDetails(false)
            .then((res) => {
                // temp object to store doctor pending requests
                let data = [];

                for (let i = 0; i < res.appointmentHistory.length; i++) {
                    data.push({
                        id: res.appointmentHistory[i]["id"],
                        date: res.appointmentHistory[i]["appointmentDate"],
                        time: res.appointmentHistory[i]["timeSlot"],
                        status:res.appointmentHistory[i]["status"],
                        remark:res.appointmentHistory[i]["remarksFromPatient"],
                        day:res.appointmentHistory[i]["dayOfWeek"],
                        patient:res.appointmentHistory[i]["patientName"],
                        doctor: res.appointmentHistory[i]["doctorName"],
                        feedbackMessage:res.appointmentHistory[i]["feedbackMessage"],
                        rating:res.appointmentHistory[i]["rating"],
                        createdAt:res.appointmentHistory[i]["createdAt"]
                    });
                }
                setAppoinmentDetail(data);
            })
            .catch((err) => {
                console.log(err);
            });
    };

    useEffect(() => {
        fetchData();
    }, []);
    const handleSubmit = (event) => {
        event.preventDefault();
    };

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
                                <Grid item xs={12}>
                                    <Paper sx={{p: 2, display: "flex", flexDirection: "column"}}>
                                        <div style={{height: 400, width: '100%'}}>
                                            <DataGridComponent columns={columns} rows={appoinmentDetail} />
                                        </div>
                                    </Paper>
                                </Grid>
                            </Paper>
                        </Grid>
                    </Grid>
                </Container>
            </Box>
            <Dialog open={openDialog} onClose={handleCloseDialog}>
                <DialogTitle>Provide Feedback</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Please provide feedback for this appointment.
                    </DialogContentText>
                    <Rating
                        name="rating"
                        value={rating}
                        onChange={(event, newValue) => setRating(newValue)}
                    />
                    <TextField
                        autoFocus
                        required
                        margin="dense"
                        id="feedback"
                        label="Feedback"
                        type="text"
                        fullWidth
                        value={feedback}
                        onChange={(e) => setFeedback(e.target.value)}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseDialog}>Cancel</Button>
                    <Button onClick={handleProvideFeedback} variant="contained" color="primary">
                        Submit
                    </Button>
                </DialogActions>
            </Dialog>
        </Box>
    );
}
