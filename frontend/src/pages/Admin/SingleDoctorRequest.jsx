import * as React from "react";
import { useState, useEffect } from "react";
import { styled } from "@mui/material/styles";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import Paper from "@mui/material/Paper";
import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import {
  approvePendingRequest,
  getSingleUnverifiedDoctor,
  rejectPendingRequest,
} from "../../services/AdminDashboardService";
import { useParams } from "react-router";
import SideNavBar from "../../components/SideNavBar";
import TableGrid from "../../components/TableGrid";
import AlertMessage from "../../components/AlertMessage";

const DrawerHeader = styled("div")(({ theme }) => ({
  display: "flex",
  alignItems: "center",
  justifyContent: "flex-end",
  padding: theme.spacing(0, 1),
  // necessary for content to be below app bar
  ...theme.mixins.toolbar,
}));

export default function SingleDoctorRequest() {
  // state which is used to store doctor details
  const [doctorDetails, setDoctorDetails] = useState([]);

  // state to track request is approved or not
  const [isApproved, setIsApproved] = useState(false);

  // state to track request is rejected or not
  const [isRejected, setIsRejected] = useState(false);

  // used to display alert message to user.
  const [isAlert, setIsAlert] = useState(false);
  const [alertMessage, setAlertMessage] = useState("");
  const [alertStatus, setAlertStatus] = useState("");

  // function that define table structure
  function createData(key, value) {
    if (value === undefined) {
      value = "Not defined!";
    }
    return { key, value };
  }

  // fetch username from the url
  let { username } = useParams();

  // create a request data
  const reqData = { email: username };

  // fetch data of unverified doctor
  const fetchData = async () => {
    getSingleUnverifiedDoctor(reqData)
      .then((res) => {
        const data = res.doctorData;
        const rows = [
          createData("First Name", data["firstName"]),
          createData("Last Name", data["lastName"]),
          createData("Contact Number", data["contactNumber"]),
          createData("License Number", data["licenseNumber"]),
          createData("Address Line 1", data["addressLine1"]),
          createData("Address Line 1", data["addressLine2"]),
          createData("Postal Code", data["postalCode"]),
        ];
        setDoctorDetails(rows);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  // function that handle when admin approve the pending request
  const handleApprove = async () => {
    // set alert to false
    setIsAlert(false);

    approvePendingRequest(reqData)
      .then((res) => {
        setIsApproved(true);
        setIsAlert(true);
        setAlertMessage(res["message"]);
        setAlertStatus(res["status"]);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  // function that handle when admin reject the pending request
  const handleReject = async () => {
    // set alert to false
    setIsAlert(false);

    rejectPendingRequest(reqData)
      .then((res) => {
        setIsRejected(true);
        setIsAlert(true);
        setAlertMessage(res["message"]);
        setAlertStatus(res["status"]);
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
      <SideNavBar />
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
                  Doctor Details
                </Typography>
                <TableGrid rows={doctorDetails} />
                {isApproved ? (
                  <Stack direction="row" sx={{ mt: 2 }} spacing={2}>
                    <Button variant="outlined" color="success">
                      Approved
                    </Button>
                  </Stack>
                ) : isRejected ? (
                  <Stack direction="row" sx={{ mt: 2 }} spacing={2}>
                    <Button variant="outlined" color="error">
                      Rejected
                    </Button>
                  </Stack>
                ) : (
                  <>
                    <Stack direction="row" sx={{ mt: 2 }} spacing={2}>
                      <Button
                        variant="contained"
                        color="success"
                        onClick={() => handleApprove()}
                      >
                        Approve
                      </Button>
                      <Button
                        variant="contained"
                        color="error"
                        onClick={() => handleReject()}
                      >
                        Reject
                      </Button>
                    </Stack>
                  </>
                )}
              </Paper>
            </Grid>
          </Grid>
        </Container>
      </Box>
    </Box>
  );
}
