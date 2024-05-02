import * as React from "react";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import DashboardIcon from "@mui/icons-material/Dashboard";
import LocalHospitalIcon from "@mui/icons-material/LocalHospital";
import VaccinesIcon from "@mui/icons-material/Vaccines";
import { ListItem } from "@mui/material";


export const listItems = (
  <React.Fragment>
    <ListItem disablePadding>
      <ListItemButton>
        <ListItemIcon>
          <DashboardIcon />
        </ListItemIcon>
        <ListItemText primary="Dashboard" />
      </ListItemButton>
    </ListItem>
    <ListItemButton>
      <ListItemIcon>
        <LocalHospitalIcon />
      </ListItemIcon>
      <ListItemText primary="Doctor Registration" />
    </ListItemButton>
    <ListItemButton>
      <ListItemIcon>
        <VaccinesIcon />
      </ListItemIcon>
      <ListItemText primary="Pharmacy Registration" />
    </ListItemButton>
  </React.Fragment>
);
