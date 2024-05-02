import * as React from "react";
import {styled} from "@mui/material/styles";
import Box from "@mui/material/Box";
import SideNavBarPatient from "../../components/SideNavBarPatient";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import {MenuItem, Select} from "@mui/material";
import {useEffect, useState} from "react";
import {doctorCatalog} from "../../services/DoctorCatalogService";
import {getPatientBlogs} from "../../services/BlogsService";
import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";


const DrawerHeader = styled("div")(({theme}) => ({
    display: "flex",
    alignItems: "center",
    justifyContent: "flex-end",
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
}));

export default function BlogPagePatient() {

    // State to hold the list of available doctors
    const [availableDoctors, setAvailableDoctors] = useState([]);
    // State to hold the doctor the patient filters on
    const [doctorFilter, setDoctorFilter] = useState(0);
    // State to hold the list of blogs
    const [blogs, setBlogs] = useState([]);
    // Function to handle the doctor filter change
    const handleFilterChange = (event) => {
        setDoctorFilter(event.target.value);
    };

    // Get the list of doctors for the patient to filter on
    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await doctorCatalog({specializations:[]});
                if (response.isSuccess) {
                    console.log(response.token[0].firstName);
                    const doctorInfo = response.token.map(doctor => [{id: doctor.id, name: doctor.firstName + " " + doctor.lastName}]);
                    setAvailableDoctors(doctorInfo);
                } else {
                    console.error('Error fetching doctor data');
                }
            } catch (error) {
                console.error('Error fetching doctor data:', error);
            }
        };
        fetchData();
    }, []);

    // Get the list of blogs that the doctor has written depending on the filter chosen by the patient
    useEffect( () => {
        getPatientBlogs(doctorFilter)
            .then((res) => {
                console.log(res);
                if (res["isSuccess"]) {
                    setBlogs(res.token)
                }
            })
            .catch((err) => {
                console.log(err);
            });
    }, [doctorFilter]);

    // Reverse the order of the blogs to show newest ones first
    blogs.sort((a,b)=>(a.createdAt > b.createdAt ? -1:1));

    // Expand the blog text when user clicks on "see more"
    const toggleSeeMore = (index) => {
        setBlogs((prevBlogs) =>
            prevBlogs.map((blog, i) =>
                i === index ? {...blog, expanded: !blog.expanded} : blog
            )
        );
    };

    // Format the blogs into the paper objects
    const listItems = blogs?.map((entry, index) =>
        <Grid item xs={12}>
            <Paper sx={{p: 2, display: "flex", flexDirection: "column"}}>
                <Grid item><Typography variant='h4'>{entry.title}</Typography></Grid>
                <Grid item>
                    <Typography variant='body2' color="text.secondary" gutterBottom>
                        Posted on {entry.createdAt.slice(0,10)} by <b>Dr. {entry.doctorName}</b>
                    </Typography>
                </Grid>
                <Grid container spacing={3} sx={{p: 2, display: "flex", flexDirection: "row", alignItems: "center"}}>
                    <Grid item xs={12}>
                        <Typography variant='body1'>
                            <div style={{
                                whiteSpace: "pre-line",
                                maxHeight: entry.expanded ? "none" : "25px", // Set max height when not expanded
                                overflow: "hidden",
                            }}>{entry.description}</div>
                        </Typography>
                    </Grid>
                    <Grid item xs={12}>
                        <Button size="small" onClick={() => toggleSeeMore(index)}>
                            {entry.expanded ? "See Less" : "See More"}
                        </Button>
                    </Grid>
                </Grid>
            </Paper>
        </Grid>
    );

    // Render the page of blog posts
    return (
        <Box sx={{display: "flex"}}>
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
                    <Grid container spacing={3}>
                        <Grid item xs={12}>
                        <div>
                            <label htmlFor="doctorFilter">Please select a doctor to view their blog posts: </label>
                            <Select
                                value={doctorFilter}
                                onChange={handleFilterChange}
                            >
                                <MenuItem value={0}>All posts</MenuItem>
                                {availableDoctors.map(doctor => (
                                    <MenuItem key={doctor[0].id} value={doctor[0].id}>
                                        {doctor[0].name}
                                    </MenuItem>
                                ))}
                            </Select>
                        </div>
                        </Grid>
                        <Grid item xs={12}>
                            <Typography component="h2" variant="h6" color="primary" gutterBottom>Featured Blogs</Typography>
                        </Grid>
                        <Grid item xs={12}>
                            <Grid container spacing={2}>
                                {listItems.length === 0 ? (
                                    <Grid item xs={12}>
                                    <Typography component="h2" variant="body1" color="black" gutterBottom>No blogs to display</Typography>
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
