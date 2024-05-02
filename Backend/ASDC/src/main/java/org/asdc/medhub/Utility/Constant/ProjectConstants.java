package org.asdc.medhub.Utility.Constant;

/**
 * Constants related to the entire project
 */
public class ProjectConstants {


    /**
     * Backend root URL constant
     */
    public static final String backendRootURL = "http://localhost:3000";

    /**
     * Routed of all endpoints
     */
    public static class Routes {
        /**
         * Routes of auth controller
         */
        public static class Auth {

            /**
             * Controller parent route
             */
            public static final String mainRoute = "/auth";

            /**
             * Registers doctor
             */
            public static final String registerDoctor = "/register/doctor";

            /**
             * Register patient
             */
            public static final String registerPatient = "/register/patient";

            /**
             * Register pharmacist
             */
            public static final String registerPharmacist = "/register/pharmacist";

            /**
             * Sign in user
             */
            public static final String signInUser = "/signin";

            /**
             * Forgot password
             */
            public static final String forgotPassword = "/forgot-password";

            /**
             * Reset password
             */
            public static final String resetPassword = "/reset-password";

            /**
             * Verify email of user
             */
            public static final String verifyEmail = "/verify-email";

            /**
             * Search specialization of doctor
             */
            public static final String searchSpecializations = "/search-specializations";
        }

        /**
         * Routes of admin controller
         */
        public static class Admin {

            /**
             * Parent route of controller
             */
            public static final String mainRoute = "/admin";

            /**
             * Get admin unverified doctors list
             */
            public static final String getUnverifiedDoctors = "/dashboard/unverified_doctors";

            /**
             * Get admin unverified doctor's details
             */
            public static final String getAdminUnverifiedDoctorDetails = "/dashboard/unverified_doctors/details";

            /**
             * Get admin unverified pharmacist list
             */
            public static final String getAdminUnverifiedPharmacists = "/dashboard/unverified_pharmacists";

            /**
             * Get admin unverified pharmacist details
             */
            public static final String getAdminUnverifiedPharmacistDetails = "/dashboard/unverified_pharmacists/details";

            /**
             * Approve user
             */
            public static final String approveUser = "/dashboard/approve";

            /**
             * Reject user
             */
            public static final String rejectUser = "/dashboard/reject";
        }

        /**
         * Routes of doctor controller
         */
        public static class Doctor {

            /**
             * Controller root route
             */
            public static final String mainRoute = "/doctor";

            /**
             * Get doctor's details
             */
            public static final String details = "/details";

            /**
             * Edit doctor's details
             */
            public static final String editDetails = "/details/edit";

            /**
             * Upload profile picture of doctor
             */
            public static final String uploadProfilePicture = "/details/upload_profile_picture";

            /**
             * Set notification preference of user
             */
            public static final String setNotificationPreference = "/set_notification_preference";

            /**
             * Change password of user
             */
            public static final String changePassword = "/change_password";

            /**
             * Fetches all appointments for doctor
             */
            public static final String getAppointments="/appointments";
            
            /**
             * Fetches feedback details for the doctor
             */
            public static final String getDoctorFeedbackDetails= "/feedback";

            /**
             * Fetches all verified pharmacist filtered by pharmacy name
             */
            public static final String getVerifiedFilteredPharmacists = "/get-filtered-pharmacist-list";

            /**
             * Updates appointment
             */
            public static final String updatedAppointment = "/appointment/update";
        }

        /**
         * Routes of home controller
         */
        public static class Home {

            /**
             * Controller main route
             */
            public static final String mainRoute = "/home";

            /**
             * Test method route
             */
            public static final String test = "/test";
        }

        /**
         * Routes of patient controller
         */
        public static class Patient {

            /**
             * Controller main route
             */
            public static final String mainRoute = "/patient";

            /**
             * Get patient profile
             */
            public static final String getProfile = "/profile";

            /**
             * Update patient profile
             */
            public static final String editProfile = "/profile/edit";

            /**
             * Get doctors unverified filtered list
             */
            public static final String getFilteredDoctorList = "/get-filtered-doctor-list";

            /**
             * Search medical specializations
             */
            public static final String searchSpecialization = "/search-specializations";

            /**
             * To book appointment with doctor
             */
            public static final String bookAppointment = "/appointment/create";

            /**
             * Gets doctor's availability data for the week
             */
            public static final String getDoctorAvailability = "/get-doctor-availability";

            /**
             * Gets doctor's details
             */
            public static final String getDoctorDetails = "/get-doctor-details";

            /**
             * Gets appointments for patients
             */
            public static final String getPatientAppointments = "/appointments";

            /**
             * Updates the patientFeedback
             */
            public static final String updatePatientFeedback="/appointment/saveFeedback";
        }

        /**
         * Routes for Blog Controller
         */
        public static class Blog {

            /**
             * Controller main route
             */
            public static final String mainRoute = "/blogs";

            /**
             * Create Blogs for the patients and Doctors
             */
            public static final String createBlog = "/create-blog";

            /**
             * Get all the blogs for the Doctors
             */
            public static final String getBlogsList = "/get-blogs";

            /**
             * Get all the Blogs for the Patients by DoctorId
             */
            public static final String getBlogsListforPatients = "get-patient-blogs";

        }

        /**
         * Routes of websocket controller
         */
        public static class WebSocketChat {
            /**
             * Controller queue endpoint
             */
            public static final String queueRoute = "/chat/send";
        }

        /**
         * Routes of chat controller
         */
        public static class Chat {
            /**
             * Controller conversation endpoint
             */
            public static final String conversation = "/chat/conversation";
            /**
             * Controller queue endpoint
             */
            public static final String partners = "/chat/partners";
            /**
             * Controller queue endpoint
             */
            public static final String username = "/chat/get_username";
        }
        /**
         * Routes for pharmacist controller
         */
        public static class Pharmacist {

            /**
             * Controller main route
             */
            public static final String mainRoute = "/pharmacist";

            /**
             * Gets pharmacist profile data
             */
            public static final String GetProfile = "/profile";

            /**
             * Update pharmacist profile with Patch api
             */
            public static final String updateProfile = "/profile";

            /**
             * Gets filtered prescription list for pharmacist based on patient name
             */
            public static final String GetFilteredAppointments = "/filtered-appointments";

            /**
             * Change password for pharmacist
             */
            public static final String changePassword = "change-password";
        }
    }
}
