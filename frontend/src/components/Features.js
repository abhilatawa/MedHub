import * as React from 'react';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import Container from '@mui/material/Container';
import Grid from '@mui/material/Grid';
import Stack from '@mui/material/Stack';
import Typography from '@mui/material/Typography';
import doctorImage from "../Assets/doctor-patient-image.jpg";
import LocalPharmacyIcon from "@mui/icons-material/LocalPharmacy";
import EditCalendarIcon from "@mui/icons-material/EditCalendar";
import LibraryBooksIcon from "@mui/icons-material/LibraryBooks";

// Array of the key features to display
const features = [
    {
        icon: <EditCalendarIcon/>,
        title: 'Same-day appointment bookings',
        description:
            'Book appointments as soon as you need them and avoid the stress of long wait times.',
    },
    {
        icon: <LocalPharmacyIcon/>,
        title: 'Prescription information',
        description:
            'Get real-time information on all your prescriptions and send orders out to pharmacist easily.',
    },
    {
        icon: <LibraryBooksIcon/>,
        title: 'Online health resources',
        description:
            'View articles written by your doctors relating to important health information.',
    },
];

export default function Features() {
    return (
        <Container sx={{py: {xs: 8, sm: 16}}}>
            <Grid container spacing={6}>
                <Grid item xs={12} md={6}>
                    <div>
                        <Typography component="h2" variant="h4" gutterBottom>
                            What we offer
                        </Typography>
                    </div>
                    <Box
                        component={Card}
                        variant="outlined"
                        sx={{
                            display: {xs: 'auto', sm: 'none'},
                            mt: 4,
                        }}
                    >
                    </Box>
                    {/*Stack to display the list of features*/}
                    <Stack
                        direction="column"
                        justifyContent="center"
                        alignItems="flex-start"
                        spacing={2}
                        useFlexGap
                        sx={{width: '100%', display: {xs: 'none', sm: 'flex'}}}
                    >
                        {/*Map to put each feature into a Card with the icon, title and description*/}
                        {features.map(({icon, title, description}) => (
                            <Card
                                variant="outlined"
                                sx={{
                                    p: 3,
                                    height: 'fit-content',
                                    width: '100%',
                                }}
                            >
                                <Box
                                    sx={{
                                        width: '100%',
                                        display: 'flex',
                                        textAlign: 'left',
                                        flexDirection: {xs: 'column', md: 'row'},
                                        alignItems: {md: 'center'},
                                        gap: 2.5,
                                    }}
                                >
                                    <Box
                                    >
                                        {icon}
                                    </Box>
                                    <Box sx={{textTransform: 'none'}}>
                                        <Typography
                                            color="text.primary"
                                            variant="body2"
                                            fontWeight="bold"
                                        >
                                            {title}
                                        </Typography>
                                        <Typography
                                            color="text.secondary"
                                            variant="body2"
                                            sx={{my: 0.5}}
                                        >
                                            {description}
                                        </Typography>
                                    </Box>
                                </Box>
                            </Card>
                        ))}
                    </Stack>
                </Grid>
                <Grid
                    item
                    xs={12}
                    md={6}
                    sx={{display: {xs: 'none', sm: 'flex'}, width: '100%'}}
                >
                    <Card
                        variant="outlined"
                        sx={{
                            height: '100%',
                            width: '100%',
                            display: {xs: 'none', sm: 'flex'},
                            pointerEvents: 'none',
                        }}
                    >
                        {/*Box to display the stock image for the homepage*/}
                        <Box
                            sx={{
                                position: 'relative',
                                m: 'auto',
                                width: 550,
                                height: 500,
                                backgroundSize: 'cover',
                                backgroundImage: `url(${doctorImage})`,
                            }}
                        >
                            <Typography
                                variant="caption"
                                sx={{
                                    position: 'absolute',
                                    bottom: 0,
                                    left: 0,
                                    right: 0,
                                    textAlign: 'center',
                                    backgroundColor: 'rgba(0, 0, 0, 0.5)',
                                    color: '#ffffff',
                                    padding: '1px',
                                }}
                            >
                                Image Source: www.freepik.com
                            </Typography>
                        </Box>
                    </Card>
                </Grid>
            </Grid>
        </Container>
    );
}