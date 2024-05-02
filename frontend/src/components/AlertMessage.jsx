import * as React from "react";
import Alert from "@mui/material/Alert";
import Snackbar from "@mui/material/Snackbar";

// Usage:
// Call this component with 2 props.
// 1) message: a message you want to display to a user.
// 2) status: a status could be success, error, info, warning depending upon the action.

export default function AlertMessage({ message, status }) {

  const [state, setState] = React.useState({
    open: true,
    vertical: "top",
    horizontal: "center",
  });
  const { vertical, horizontal, open } = state;

  const handleClose = () => {
    setState({ ...state, open: false });
  };

  return (
    <Snackbar
      anchorOrigin={{ vertical, horizontal }}
      open={open}
      autoHideDuration={3000}
      onClose={handleClose}
      key={vertical + horizontal}
    >
      <Alert
        onClose={handleClose}
        severity={status}
        variant="filled"
        sx={{ width: "100%" }}
      >
        {message}
      </Alert>
    </Snackbar>
  );
}
