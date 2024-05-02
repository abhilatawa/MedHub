import * as React from "react";
import {styled} from "@mui/material/styles";
import Box from "@mui/material/Box";
import SideNavBarDoctor from "../../components/SideNavBarDoctor";
import Button from "@mui/material/Button";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import {useNavigate} from "react-router-dom";
import TextField from "@mui/material/TextField";
import {createBlog} from "../../services/BlogsService";
import Paper from "@mui/material/Paper";
import {useState} from "react";
import CssBaseline from "@mui/material/CssBaseline";
import AlertMessage from "../../components/AlertMessage";
import Typography from "@mui/material/Typography";

const DrawerHeader = styled("div")(({theme}) => ({
    display: "flex",
    alignItems: "center",
    justifyContent: "flex-end",
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
}));

export default function CreateBlog() {

    const navigate = useNavigate();

    // Used to display alert message to user
    const [isAlert, setIsAlert] = useState(false);
    const [alertMessage, setAlertMessage] = useState("");
    const [alertStatus, setAlertStatus] = useState("");

    // Handle the form data that the user submits to create a blog
    const handleSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        const blogData = {
            title: data.get("blogTitle"),
            description: data.get("blogBody"),
        };
        console.log(blogData);

        // Send the form only if no fields are left empty
        if (
            blogData.title !== "" &&
            blogData.description !== ""
        ) {
            createBlog(blogData)
                .then((res) => {

                    // Modify alert to say blog was created successfully
                    setIsAlert(true);
                    setAlertMessage("Blog post created successfully");
                    setAlertStatus("success");

                    // If the post is successful then return to the blog page
                    if (res["isSuccess"]) {
                        setTimeout(() => {
                            navigate("/blog-page-doctor");
                        }, 2500);
                    }
                })
                .catch((err) => {
                    console.log(err);
                });
        }

    };

    // Render the form the doctor uses to create a blog post
    return (
        <Box component="form" noValidate onSubmit={handleSubmit} sx={{display: "flex"}}>
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
                    <CssBaseline />
                    {isAlert && (
                        <AlertMessage message={alertMessage} status={alertStatus} />
                    )}
                    <Grid container spacing={3}>
                        <Grid item xs={12}>
                            <Typography component="h2" variant="h6" color="primary" gutterBottom>Create a Blog Post</Typography>
                        </Grid>
                        <Grid item xs={12}>
                            <Paper sx={{p: 2, display: "flex", flexDirection: "column"}}>
                                <Grid container spacing={2}>
                                    <Grid item xs={12}>
                                        <TextField
                                            name="blogTitle"
                                            required
                                            fullWidth
                                            id="blogTitle"
                                            label="Blog Title"
                                            variant="outlined"
                                            autoFocus
                                        />
                                    </Grid>
                                    <Grid item xs={12}>
                                        <TextField
                                            name="blogBody"
                                            required
                                            fullWidth
                                            id="blogBody"
                                            label="Write your blog here..."
                                            variant="outlined"
                                            multiline
                                            rows={10}
                                        />
                                    </Grid>
                                    <Grid item xs={12}>
                                        <Button
                                            type="submit"
                                            variant="contained"
                                            color="primary">
                                            Submit
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
