import * as React from "react";
import { styled, useTheme } from "@mui/material/styles";
import MuiDrawer from "@mui/material/Drawer";
import MuiAppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import List from "@mui/material/List";
import CssBaseline from "@mui/material/CssBaseline";
import Typography from "@mui/material/Typography";
import Divider from "@mui/material/Divider";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";
import ChevronLeftIcon from "@mui/icons-material/ChevronLeft";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import DashboardIcon from "@mui/icons-material/Dashboard";
import EditCalendarIcon from "@mui/icons-material/EditCalendar";
import UserIcon from "@mui/icons-material/Person";
import Tooltip from "@mui/material/Tooltip";
import MenuItem from "@mui/material/MenuItem";
import Menu from "@mui/material/Menu";
import NoteAltIcon from "@mui/icons-material/NoteAlt";
import LocalPharmacyIcon from "@mui/icons-material/LocalPharmacy";
import { useNavigate } from "react-router";
import { ListItem } from "@mui/material";
import Avatar from "@mui/material/Avatar";
import PendingActionsIcon from "@mui/icons-material/PendingActions";
import { Logout } from "../services/LoginService";
import EditNotificationsIcon from "@mui/icons-material/EditNotifications";
import ChatIcon from '@mui/icons-material/Chat';
import LockResetIcon from '@mui/icons-material/LockReset';
import LibraryBooksIcon from '@mui/icons-material/LibraryBooks';
import RecommendIcon from '@mui/icons-material/Recommend';
const drawerWidth = 250;

const openedMixin = (theme) => ({
  width: drawerWidth,
  transition: theme.transitions.create("width", {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.enteringScreen,
  }),
  overflowX: "hidden",
});

const DrawerHeader = styled("div")(({ theme }) => ({
  display: "flex",
  alignItems: "center",
  justifyContent: "flex-end",
  padding: theme.spacing(0, 1),
  // necessary for content to be below app bar
  ...theme.mixins.toolbar,
}));

const closedMixin = (theme) => ({
  transition: theme.transitions.create("width", {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.leavingScreen,
  }),
  overflowX: "hidden",
  width: `calc(${theme.spacing(7)} + 1px)`,
  [theme.breakpoints.up("sm")]: {
    width: `calc(${theme.spacing(8)} + 1px)`,
  },
});

const AppBar = styled(MuiAppBar, {
  shouldForwardProp: (prop) => prop !== "open",
})(({ theme, open }) => ({
  zIndex: theme.zIndex.drawer + 1,
  transition: theme.transitions.create(["width", "margin"], {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.leavingScreen,
  }),
  ...(open && {
    marginLeft: drawerWidth,
    width: `calc(100% - ${drawerWidth}px)`,
    transition: theme.transitions.create(["width", "margin"], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  }),
}));

const Drawer = styled(MuiDrawer, {
  shouldForwardProp: (prop) => prop !== "open",
})(({ theme, open }) => ({
  width: drawerWidth,
  flexShrink: 0,
  whiteSpace: "nowrap",
  boxSizing: "border-box",
  ...(open && {
    ...openedMixin(theme),
    "& .MuiDrawer-paper": openedMixin(theme),
  }),
  ...(!open && {
    ...closedMixin(theme),
    "& .MuiDrawer-paper": closedMixin(theme),
  }),
}));

export default function SideNavBarDoctor() {
  const theme = useTheme();
  const [open, setOpen] = React.useState(true);
  const [anchorEl, setAnchorEl] = React.useState(null);
  const openMenu = Boolean(anchorEl);

  const handleMenuClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
  };

  const navigate = useNavigate();

  const handleDrawerOpen = () => {
    setOpen(true);
  };

  const handleDrawerClose = () => {
    setOpen(false);
  };

  const handleLogout = () => {
    console.log("yes");

    // call logout function from the LoginService
    const response = Logout();
    console.log(response);
    if (response) {
      navigate("/sign-in");
    }
  };

  return (
      <>
        <CssBaseline />
        <AppBar position="fixed" open={open} style={{ background: "primary" }}>
          <Toolbar>
            <IconButton
                color="inherit"
                aria-label="open drawer"
                onClick={handleDrawerOpen}
                edge="start"
                sx={{
                  marginRight: 5,
                  ...(open && { display: "none" }),
                }}
            >
              <MenuIcon />
            </IconButton>
            <Typography variant="h6" noWrap component="div" sx={{ flexGrow: 1 }}>
              Doctor Dashboard
            </Typography>
            <Tooltip title="Account settings">
              <IconButton
                  onClick={handleMenuClick}
                  size="small"
                  sx={{ ml: 2 }}
                  aria-controls={openMenu ? "account-menu" : undefined}
                  aria-haspopup="true"
                  aria-expanded={openMenu ? "true" : undefined}
              >
                <Avatar sx={{ width: 42, height: 42 }}>D </Avatar>
                {/* <KeyboardArrowDownIcon /> */}
              </IconButton>
            </Tooltip>
            <Menu
                anchorEl={anchorEl}
                id="account-menu"
                open={openMenu}
                onClose={handleMenuClose}
                onClick={handleMenuClose}
                PaperProps={{
                  elevation: 0,
                  sx: {
                    overflow: "visible",
                    filter: "drop-shadow(0px 2px 8px rgba(0,0,0,0.32))",
                    mt: 1.5,
                    "& .MuiAvatar-root": {
                      width: 32,
                      height: 32,
                      ml: -0.5,
                      mr: 1,
                    },
                    "&::before": {
                      content: '""',
                      display: "block",
                      position: "absolute",
                      top: 0,
                      right: 14,
                      width: 10,
                      height: 10,
                      bgcolor: "background.paper",
                      transform: "translateY(-50%) rotate(45deg)",
                      zIndex: 0,
                    },
                  },
                }}
                transformOrigin={{ horizontal: "right", vertical: "top" }}
                anchorOrigin={{ horizontal: "right", vertical: "bottom" }}
            >
              <MenuItem onClick={handleLogout}>Sign out</MenuItem>
            </Menu>
          </Toolbar>
        </AppBar>
        <Drawer variant="permanent" open={open}>
          <DrawerHeader>
            <IconButton onClick={handleDrawerClose}>
              {theme.direction === "rtl" ? (
                  <ChevronRightIcon />
              ) : (
                  <ChevronLeftIcon />
              )}
            </IconButton>
          </DrawerHeader>
          <Divider />

          <List>
            <ListItem disablePadding onClick={() => navigate("/doctor-home")}>
              <ListItemButton>
                <ListItemIcon>
                  <DashboardIcon />
                </ListItemIcon>
                <ListItemText primary="Home" />
              </ListItemButton>
            </ListItem>

          <ListItem
            disablePadding
            onClick={() => navigate("/doctor-personal-details")}
          >
            <ListItemButton>
              <ListItemIcon>
                <UserIcon />
              </ListItemIcon>
              <ListItemText primary="Personal Details" />
            </ListItemButton>
          </ListItem>
          <ListItem
            disablePadding
            onClick={() => navigate("/doctor-availability")}
          >
            <ListItemButton>
              <ListItemIcon>
                <EditCalendarIcon />
              </ListItemIcon>
              <ListItemText primary="Availability" />
            </ListItemButton>
          </ListItem>
          <ListItem
            disablePadding
            onClick={() => navigate("/doctor-appointment")}
          >
            <ListItemButton>
              <ListItemIcon>
                <PendingActionsIcon />
              </ListItemIcon>
              <ListItemText primary="Appoinment" />
            </ListItemButton>
          </ListItem>
          <ListItem
            disablePadding
            onClick={() => navigate("/doctor-consultation")}
          >
            <ListItemButton>
              <ListItemIcon>
                <NoteAltIcon />
              </ListItemIcon>
              <ListItemText primary="Consultation" />
            </ListItemButton>
          </ListItem>
          <ListItem disablePadding onClick={() => navigate("/doctor-pharmacy")}>
            <ListItemButton>
              <ListItemIcon>
                <LocalPharmacyIcon />
              </ListItemIcon>
              <ListItemText primary="Pharmacy" />
            </ListItemButton>
          </ListItem>
          <ListItem
              disablePadding
              onClick={() => navigate("/blog-page-doctor")}
          >
            <ListItemButton>
              <ListItemIcon>
                <LibraryBooksIcon />
              </ListItemIcon>
              <ListItemText primary="Blog" />
            </ListItemButton>
          </ListItem>
          <ListItem
                disablePadding
                onClick={() => navigate("/doctor/chat")}
            >
              <ListItemButton>
                <ListItemIcon>
                  <ChatIcon/>
                </ListItemIcon>
                <ListItemText primary="Chat" />
              </ListItemButton>
            </ListItem>
          <ListItem
              disablePadding
              onClick={() => navigate("/feedback")}
          >
            <ListItemButton>
              <ListItemIcon>
                <RecommendIcon />
              </ListItemIcon>
              <ListItemText primary="Feedback" />
            </ListItemButton>
          </ListItem>
          <ListItem disablePadding onClick={() => navigate("/doctor-notification")}>
                        <ListItemButton>
                            <ListItemIcon>
                                <EditNotificationsIcon />
                            </ListItemIcon>
                            <ListItemText primary="Notification Preferences" />
                        </ListItemButton>
                    </ListItem>
                    <ListItem disablePadding onClick={() => navigate("/doctor-change-password")}>
                        <ListItemButton>
                            <ListItemIcon>
                                <LockResetIcon />
                            </ListItemIcon>
                            <ListItemText primary="Change Password" />
                        </ListItemButton>
                    </ListItem>
        </List>
      </Drawer>
    </>
  );
}
