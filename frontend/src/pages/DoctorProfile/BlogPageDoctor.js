import * as React from "react";
import {styled} from "@mui/material/styles";
import Box from "@mui/material/Box";
import SideNavBarDoctor from "../../components/SideNavBarDoctor";
import Button from "@mui/material/Button";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import {useNavigate} from "react-router-dom";
import {getDoctorBlogs} from "../../services/BlogsService";
import {useState} from "react";
import {useEffect} from "react";
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

export default function BlogPageDoctor() {

    const navigate = useNavigate();

    // Handle when the user clicks on "create a blog"
    const handleCreate = () => {
        navigate("/create-blog");
    }

    // State to hold the list of past blogs from the doctor
    const [blogs, setBlogs] = useState([]);

    // Get the list of blogs that the doctor has written
    useEffect( () => {
        getDoctorBlogs()
            .then((res) => {
        console.log(res);
        if (res["isSuccess"]) {
            setBlogs(res.token)
        }
    })
        .catch((err) => {
            console.log(err);
        });
    }, []);

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
                        Posted on {entry.createdAt.slice(0,10)}
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
                            <Button
                                variant="contained"
                                onClick={handleCreate}
                            >
                                Create a blog post
                            </Button>
                        </Grid>
                        <Grid item xs={12}>
                            <Typography component="h2" variant="h6" color="primary" gutterBottom>Your Past Blog Posts</Typography>
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
