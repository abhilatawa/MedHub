import React from "react";
import { useState, useEffect } from "react";
import { styled } from "@mui/material/styles";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import SideNavBarPatient from "../../components/SideNavBarPatient";
import {doctorCatalog} from "../../services/DoctorCatalogService";
import {useNavigate} from "react-router-dom";
import {MenuItem, Select} from "@mui/material";
import Rating from '@mui/material/Rating';
import {doctorSpecializationInPatient} from "../../services/DoctorSpecializationService";

const DrawerHeader = styled("div")(({ theme }) => ({
    display: "flex",
    alignItems: "center",
    justifyContent: "flex-end",
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
}));

export default function DoctorCatalog() {

    const navigate = useNavigate();
    const [filteredDoctors, setFilteredDoctors] = useState([]);
    const [specializationFilter, setSpecializationFilter] = useState([]);
    const [medicalSpecializations, setMedicalSpecializations] = useState([]);
    const handleFilterChange = (event) => {
        setSpecializationFilter(event.target.value);
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await doctorCatalog({specializations: specializationFilter}); // Pass any necessary data here
                if (response.isSuccess) {
                    setFilteredDoctors(response.token);
                } else {
                    console.error('Error fetching doctor data');
                }
            } catch (error) {
                console.error('Error fetching doctor data:', error);
            }
        };
        fetchData();
    }, [specializationFilter]);

    useEffect(()=>{
        const fetchData = async () => {
            try {
                const response = await doctorSpecializationInPatient(''); // Pass any necessary data here
                if (response.isSuccess) {
                    setMedicalSpecializations(response.token);
                } else {
                    console.error('Error fetching doctor data');
                }
            } catch (error) {
                console.error('Error fetching doctor data:', error);
            }
        };
        fetchData();
    }, []);

    const listItems = filteredDoctors?.map(entry =>
        <Grid item xs={12}>
            <Paper sx={{p: 2, display: "flex", flexDirection: "column"}}>
                <Grid container spacing={3} sx={{p: 2, display: "flex", flexDirection: "row", alignItems: "center"}}>
                    <Grid item xs={9}>
                        <Typography variant ='h4'>{entry.firstName} {entry.lastName}</Typography>
                        <Typography variant='body1'>
                            {entry.addressLine1}<br></br>
                            {entry.addressLine2}<br></br><br></br>
                            Specializations: {entry.specializationsOfDoctor.join(', ')}
                        </Typography>
                    </Grid>
                    <Grid item xs={3}>
                        <Button
                            fullWidth
                            variant="contained"
                            sx={{mt: 1, mb: 1}}
                            onClick={() => {
                                navigate(`/patient/book-appointment/${entry.id}`);
                            }}
                        >
                            Book an appointment
                        </Button>
                    </Grid>
                    {/*<Grid item xs={3}>*/}
                    {/*    <Button*/}
                    {/*        fullWidth*/}
                    {/*        variant="contained"*/}
                    {/*        sx={{mt: 1, mb: 1}}*/}
                    {/*        //placeholder onClick until we have doctor profiles implemented*/}
                    {/*        onClick={() => {*/}
                    {/*            navigate('/sign-in');*/}
                    {/*        }}*/}
                    {/*    >*/}
                    {/*        View profile*/}
                    {/*    </Button>*/}
                    {/*</Grid>*/}
                </Grid>
            </Paper>
        </Grid>
    );


    return (
        <Box sx={{ display: "flex" }}>
            <SideNavBarPatient />
            {/*{isAlert && <AlertMessage message={alertMessage} status={alertStatus} />}*/}
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
                            <Grid item>
                                <Typography
                                    component="h2"
                                    variant="h6"
                                    color="primary"
                                    gutterBottom
                                >
                                    Doctor Catalog
                                </Typography>
                                <Box>
                                    <div>
                                        <label htmlFor="specializationFilter">Please select the specialization(s) you are looking for:  </label>
                                        <Select
                                            multiple
                                            value={specializationFilter}
                                            onChange={handleFilterChange}
                                        >
                                            {medicalSpecializations.map((name) => (
                                                <MenuItem key={name} value={name}>
                                                    {name}
                                                </MenuItem>
                                            ))}
                                        </Select>
                                    </div>
                                </Box>
                            </Grid>
                            <Grid container spacing={2}>
                            {listItems}
                            </Grid>
                        </Grid>
                    </Grid>
                </Container>
                <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
                </Container>
            </Box>
        </Box>
    );
}

