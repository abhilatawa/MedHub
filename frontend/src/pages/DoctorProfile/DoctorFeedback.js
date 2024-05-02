import * as React from "react";
import {styled} from "@mui/material/styles";
import Box from "@mui/material/Box";
import SideNavBarDoctor from "../../components/SideNavBarDoctor";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import {useState} from "react";
import {useEffect} from "react";
import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";
import Rating from "@mui/material/Rating";
import {getDoctorFeedback} from "../../services/DoctorFeedbackService";

const DrawerHeader = styled("div")(({theme}) => ({
    display: "flex",
    alignItems: "center",
    justifyContent: "flex-end",
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
}));

export default function DoctorFeedback() {

    const [doctorFeedback, setDoctorFeedback] = useState([]);

    // Get all the feedback for the doctor
    useEffect( () => {
        getDoctorFeedback()
            .then((res) => {
                console.log(res);
                if (res["isSuccess"]) {
                    setDoctorFeedback(res.token.feedbackDetails)
                }
            })
            .catch((err) => {
                console.log(err);
            });
    }, []);

    // Filter the feedback for only those which have a non-null message
    const filteredFeedback = doctorFeedback.filter(entry => entry.feedbackMessage !== null);

    // Calculate the average rating to display on the doctor's feedback page
    const averageRating = filteredFeedback.reduce((total, entry) => total + entry.rating, 0) / filteredFeedback.length;

    // Format the feedback items into the paper objects
    const listItems = filteredFeedback?.map((entry, index) =>
        <Grid item xs={12}>
            <Paper sx={{p: 2, display: "flex", flexDirection: "column"}}>
                <Grid container spacing={3} sx={{p: 2, display: "flex", flexDirection: "row", alignItems: "center"}}>
                    <Grid item xs={12}>
                        <Rating name="read-only" value={entry.rating} readOnly/>
                    </Grid>
                    <Grid item xs={12}>
                        <Typography variant='body1'>
                            {entry.feedbackMessage}
                        </Typography>
                    </Grid>
                </Grid>
            </Paper>
        </Grid>
    );

    // Render the page of feedback for the doctor
    return (
        <Box sx={{display: "flex"}}>
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
                    <Grid container spacing={3}>
                        <Grid item xs={12}>
                            <Typography component="h2" variant="h6" color="primary" gutterBottom>Your Average Rating: {averageRating}</Typography>
                            <Rating name="read-only" value={averageRating} precision={0.5} readOnly/>
                        </Grid>
                        <Grid item xs={12}>
                            <Typography component="h2" variant="h6" color="primary" gutterBottom>Feedback From Your Patients</Typography>
                        </Grid>
                        <Grid item xs={12}>
                            <Grid container spacing={2}>
                                {listItems.length === 0 ? (
                                    <Grid item xs={12}>
                                        <Typography component="h2" variant="body1" color="black" gutterBottom>No feedback to display</Typography>
                                    </Grid>
                                ) : (
                                    listItems
                                )}
                            </Grid>
                        </Grid>
                    </Grid>
                </Container>
            </Box>
        </Box>
    );
}
