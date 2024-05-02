import * as React from "react";
import { styled } from "@mui/material/styles";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import Paper from "@mui/material/Paper";
import SideNavBar from "../../components/SideNavBar";
import DoctorPendingRequest from "./DoctorPendingRequest";
import PharmacistPendingRequest from "./PharmacistPendingRequest";

const DrawerHeader = styled("div")(({ theme }) => ({
  display: "flex",
  alignItems: "center",
  justifyContent: "flex-end",
  padding: theme.spacing(0, 1),
  // necessary for content to be below app bar
  ...theme.mixins.toolbar,
}));

export default function AdminDashboard() {
  return (
    <>
      <Box sx={{ display: "flex" }}>
        <SideNavBar />
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
            <Grid container spacing={3}>
              <Grid item xs={12}>
                <Paper sx={{ p: 2, display: "flex", flexDirection: "column" }}>
                  <DoctorPendingRequest />
                </Paper>
              </Grid>
              <Grid item xs={12}>
                <Paper sx={{ p: 2, display: "flex", flexDirection: "column" }}>
                  <PharmacistPendingRequest />
                </Paper>
              </Grid>
            </Grid>
          </Container>
        </Box>
      </Box>
    </>
  );
}
