import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import {ThemeProvider, createTheme} from '@mui/material/styles';
import Features from '../components/Features';
import LogoHomepage from "../components/LogoHomepage";
import CustomerFeedback from "../components/CustomerFeedback";

export default function WelcomePage2() {
    const defaultTheme = createTheme();

    return (
        <ThemeProvider theme={defaultTheme}>
            <CssBaseline/>
            <LogoHomepage/>
            <Features/>
            <CustomerFeedback/>
        </ThemeProvider>
    );
}