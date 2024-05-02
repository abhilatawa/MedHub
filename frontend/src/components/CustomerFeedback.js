import * as React from 'react';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import Container from '@mui/material/Container';
import Grid from '@mui/material/Grid';

// Array of customer feedback to display on the homepage
const feedback = [
    {
        name: 'Sarah Johnson',
        location: 'Halifax, NS',
        feedback:
            "Medhub has made managing my healthcare needs so much easier. The platform is user-friendly, and I can access everything I need in one place. Highly recommended!",
    },
    {
        name: 'Dr. Michael Patel',
        location: 'Halifax, NS',
        feedback:
            "As a healthcare professional, I rely on tools like Medhub to streamline patient interactions and manage medical records efficiently. The features are comprehensive, and the support team is always responsive. Great experience overall!",
    },
    {
        name: 'Emily Davis',
        location: 'Halifax, NS',
        feedback:
            'Medhub has been a lifesaver for me and my family. From scheduling appointments to accessing lab results, it\'s convenient and secure. I appreciate the convenience it brings to our healthcare journey.',
    }
];


export default function CustomerFeedback() {
    return (
        <Box
            component="main"
            sx={{
                backgroundColor: (theme) =>
                    theme.palette.mode === "light"
                        ? theme.palette.grey[100]
                        : theme.palette.grey[900],
                flexGrow: 1,
                overflow: "auto",
                alignItems: 'center',
                justifyContent: 'center',
                backgroundRepeat: 'no-repeat',
            }}
        >
            <Container
                sx={{
                    pt: {xs: 4, sm: 12},
                    pb: {xs: 8, sm: 16},
                    position: 'relative',
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                    gap: {xs: 3, sm: 6},
                }}
            >
                <Box
                    sx={{
                        width: {sm: '100%', md: '60%'},
                        textAlign: {sm: 'left', md: 'center'},
                    }}
                >
                    <Typography component="h2" variant="h4">
                        Customer Feedback
                    </Typography>
                    <Typography variant="body1" color="text.secondary">
                        See what our customers are saying about their MedHub experience
                    </Typography>
                </Box>
                <Grid container spacing={2}>
                    {/*Map each customer feedback to a card to display the feedback, name and location*/}
                    {feedback.map((testimonial) => (
                        <Grid item xs={12} sm={6} md={4} sx={{display: 'flex'}}>
                            <Card
                                sx={{
                                    display: 'flex',
                                    flexDirection: 'column',
                                    justifyContent: 'space-between',
                                    flexGrow: 1,
                                    p: 1,
                                }}
                            >
                                <CardContent>
                                    <Typography variant="body2" color="text.secondary">
                                        {testimonial.feedback}
                                    </Typography>
                                </CardContent>
                                <Box
                                    sx={{
                                        display: 'flex',
                                        flexDirection: 'row',
                                        justifyContent: 'space-between',
                                        pr: 2,
                                    }}
                                >
                                    <CardHeader
                                        title={testimonial.name}
                                        subheader={testimonial.location}
                                    />
                                </Box>
                            </Card>
                        </Grid>
                    ))}
                </Grid>
            </Container>
        </Box>
    );
}