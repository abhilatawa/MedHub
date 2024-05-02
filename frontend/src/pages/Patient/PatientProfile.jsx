import React from "react";
import { useState, useEffect } from "react";
import { styled } from "@mui/material/styles";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import SideNavBarPatient from "../../components/SideNavBarPatient";
import AlertMessage from "../../components/AlertMessage";
import DataGridComponent from "../../components/DataGridComponet";
import {
  editPatientDetails,
  getPatientProfile,
} from "../../services/PatientProfileService";

const DrawerHeader = styled("div")(({ theme }) => ({
  display: "flex",
  alignItems: "center",
  justifyContent: "flex-end",
  padding: theme.spacing(0, 1),
  // necessary for content to be below app bar
  ...theme.mixins.toolbar,
}));

export default function PatientProfile() {
  // state is used to manage patient details
  const [patientDetails, setPatientDetails] = useState({
    firstName: "",
    lastName: "",
    medicalHistory: "",
  });

  const [appointmentDetails, setAppointmentDetails] = useState({
    appointmentDate: "",
    doctor: "",
    reason: "",
  });

  // state used to store doctor pending requests fetched from backend
  const [list, setList] = useState([]);

  // used to display alert message to user.
  const [isAlert, setIsAlert] = useState(false);
  const [alertMessage, setAlertMessage] = useState("");
  const [alertStatus, setAlertStatus] = useState("");

  // used to manage edit and save button
  const [isEditable, setIsEditable] = useState(false);

  // decide which fields to disply to admin
  const columns = [
    {
      field: "appointmentDate",
      headerName: "Appointment Date",
      width: 250,
    },
    {
      field: "doctor",
      headerName: "Doctor",
      width: 250,
    },
    {
      field: "reason",
      headerName: "reason",
      width: 250,
    },
  ];

  // fetch details of the patient
  const fetchData = async () => {
    getPatientProfile()
      .then((res) => {
        const data = res.patientProfileDetails;
        // console.log(data);
        setPatientDetails({
          firstName: data["firstName"],
          lastName: data["lastName"],
          medicalHistory: data["medicalHistory"],
        });
      })
      .catch((err) => {
        console.log(err);
      });
  };

  // console.log(appointmentDetails);
  // function to save the details of patient to backend
  const handleSubmit = (event) => {
    event.preventDefault();

    // set alert to false
    setIsAlert(false);
    // reverse the value of the isEditable
    setIsEditable(!isEditable);

    if (isEditable) {
      // make api call to edit the details
      editPatientDetails(patientDetails).then((res) => {
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
    <Box sx={{ display: "flex" }}>
      <SideNavBarPatient />
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
                <Typography
                  component="h2"
                  variant="h6"
                  color="primary"
                  gutterBottom
                >
                  Patient Profile
                </Typography>
                <Grid container spacing={2} sx={{ mt: 1 }}>
                  <Grid item md={6} xs={12}>
                    <TextField
                      fullWidth
                      label="First Name"
                      InputProps={{
                        readOnly: !isEditable,
                      }}
                      value={patientDetails["firstName"]}
                      onChange={(e) => {
                        setPatientDetails({
                          ...patientDetails,
                          firstName: e.target.value,
                        });
                      }}
                    />
                  </Grid>
                  <Grid item md={6} xs={12}>
                    <TextField
                      fullWidth
                      label="Last Name"
                      InputProps={{
                        readOnly: !isEditable,
                      }}
                      value={patientDetails["lastName"]}
                      onChange={(e) => {
                        setPatientDetails({
                          ...patientDetails,
                          lastName: e.target.value,
                        });
                      }}
                    />
                  </Grid>
                  <Grid item md={12} xs={12}>
                    <TextField
                      fullWidth
                      label="Medical History"
                      InputProps={{
                        readOnly: !isEditable,
                      }}
                      value={patientDetails["medicalHistory"]}
                      onChange={(e) => {
                        setPatientDetails({
                          ...patientDetails,
                          medicalHistory: e.target.value,
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
