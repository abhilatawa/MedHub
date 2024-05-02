import * as React from "react";
import {styled} from "@mui/material/styles";
import Button from "@mui/material/Button";
import Grid from "@mui/material/Grid";
import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";

const DrawerHeader = styled("div")(({theme}) => ({
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

export default function FeaturedArticles() {

    const featuredArticles = [{
        author: "Jeremy Smith",
        title: "Tips for Staying Healthy in the Winter",
        body: "This article will give you useful tips on staying healthy during the long winter months. \nWe all know it's difficult to stay active when it's so cold outside."
    },
        {author: "Example Author2", title: "Blog Title2", body: "This is my 2nd blog post!"}];

    const testItems = featuredArticles?.map(entry =>
        <Grid item xs={12}>
            <Paper sx={{p: 2, display: "flex", flexDirection: "column"}}>
                <Grid item><Typography variant='h4'>{entry.title}</Typography></Grid>
                <Grid item><Typography variant='body1'>Written by: {entry.author}</Typography></Grid>
                <Grid item>
                    <Button
                        size="small" // Set the button size to small
                        variant="contained"
                        // placeholder onClick until we have doctor profiles implemented
                        // onClick={() => {
                        //     navigate('/sign-in');
                        // }}
                    >
                        View author's profile
                    </Button>
                </Grid>
                <Grid container spacing={3} sx={{p: 2, display: "flex", flexDirection: "row", alignItems: "center"}}>
                    <Grid item xs={12}>
                        <Typography variant='body1'>
                            <div style={{whiteSpace: 'pre-line'}}>{entry.body}</div>
                        </Typography>
                    </Grid>
                </Grid>
            </Paper>
        </Grid>
    );

    return (
        <div>
            <Grid container spacing={2}>
                <Grid item xs={12}>
                    <Typography component="h2" variant="h6" color="primary" gutterBottom>Featured Articles</Typography>
                </Grid>
                {testItems}
            </Grid>
        </div>
    );
}
