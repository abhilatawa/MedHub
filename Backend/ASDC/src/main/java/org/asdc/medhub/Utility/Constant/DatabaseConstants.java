package org.asdc.medhub.Utility.Constant;

import lombok.Getter;

/**
 * Constants defined related to database
 */
@Getter
public class DatabaseConstants {

    /**
     * Query constants (HQL)
     */
    public static class Queries {

        /**
         * select all the doctors who have specialization in given parameter list
         */
        public static final String getVerifiedDoctorsWithSpecializationsQuery = "SELECT DISTINCT td FROM tbl_doctors td LEFT JOIN td.specializationsOfDoctor tsod WHERE tsod.specialization IN :specializationList and td.user.adminVerificationStatus='VERIFIED'";

        /**
         * select all the doctors who are verified
         */
        public static final String getAllVerifiedDoctors = "select td from tbl_doctors td where td.user.adminVerificationStatus='VERIFIED'";

        /**
         * select all chat messages between two users
         */
        public static final String findChatMessagesBetweenTwoUsers = "SELECT c FROM tbl_chat_messages c WHERE (c.senderId = :userId1 AND c.receiverId = :userId2) OR (c.senderId = :userId2 AND c.receiverId = :userId1) ORDER BY c.createdAt ASC";

        /**
         * select all chat partners of a user
         */
        public static final String findAllChatPartnersByUserId = "SELECT DISTINCT CASE WHEN c.senderId = :userId THEN c.receiverId ELSE c.senderId END FROM tbl_chat_messages c WHERE c.senderId = :userId OR c.receiverId = :userId";

    }

    /**
     * Constants related to MedicalSpecializationTable
     */
    public static class SpecializationOfDoctorTable {

        /**
         * name of table
         */
        public static final String tableName = "tbl_specialization_of_doctor";

        /**
         * Constants related to columns names
         */
        public static class Columns {

            /**
             * Column name
             */
            public static final String doctorId = "doctor_id";

            /**
             * Column name
             */
            public static final String specialization = "specialization";

            /**
             * Column name
             */
            public static final String createdAt = "created_at";

            /**
             * Column name
             */
            public static final String updatedAt = "updated_at";
        }
    }

    /**
     * Constants related to MedicalSpecializationTable
     */
    public static class MedicalSpecializationTable {

        /**
         * name of table
         */
        public static final String tableName = "tbl_medical_specializations";

        /**
         * Constants related to columns names
         */
        public static class Columns {

            /**
             * Column name
             */
            public static final String specialization = "specialization";

            /**
             * Column name
             */
            public static final String createdAt = "created_at";

            /**
             * Column name
             */
            public static final String updatedAt = "updated_at";
        }
    }

    /**
     * Constants related to doctor table
     */
    public static class DoctorTable {

        /**
         * name of table
         */
        public static final String tableName = "tbl_doctors";

        /**
         * Constants related to columns names
         */
        public static class Columns {
            /**
             * Column name
             */
            public static final String firstName = "first_name";

            /**
             * Column name
             */
            public static final String lastName = "last_name";

            /**
             * Column name
             */
            public static final String contactNumber = "contact_number";

            /**
             * Column name
             */
            public static final String addressLine1 = "address_line_1";

            /**
             * Column name for the address line 2 in the database table
             */
            public static final String addressLine2 = "address_line_2";

            /**
             * Column name for the postal code in the database table
             */
            public static final String postalCode = "postal_code";

            /**
             * Column name for the license number in the database table
             */
            public static final String licenseNumber = "license_number";

            /**
             * Column name for the job experience title in the database table
             */
            public static final String jobExpTitle = "job_exp_title";

            /**
             * Column name for the job description in the database table
             */
            public static final String jobDescription = "job_description";

            /**
             * Column name for the start time in the database table
             */
            public static final String startTime = "start_time";

            /**
             * Column name for the end time in the database table
             */
            public static final String endTime = "end_time";

            /**
             * Column name for the flag indicating availability on Sunday in the database table
             */
            public static final String sunday = "sunday";

            /**
             * Column name for the flag indicating availability on Monday in the database table
             */
            public static final String monday = "monday";

            /**
             * Column name for the flag indicating availability on Tuesday in the database table
             */
            public static final String tuesday = "tuesday";

            /**
             * Column name for the flag indicating availability on Wednesday in the database table
             */
            public static final String wednesday = "wednesday";

            /**
             * Column name for the flag indicating availability on Thursday in the database table
             */
            public static final String thursday = "thursday";

            /**
             * Column name for the flag indicating availability on Friday in the database table
             */
            public static final String friday = "friday";

            /**
             * Column name for the flag indicating availability on Saturday in the database table
             */
            public static final String saturday = "saturday";

            /**
             * Column name for the file path of the profile picture in the database table.
             */
            public static final String profilePictureFilePath = "profile_picture_file_path";

            /**
             * Column name created at
             */
            public static final String createdAt = "created_at";

            /**
             * Column name updated at
             */
            public static final String updatedAt = "updated_at";
        }
    }

    /**
     * Constants related to patient table
     */
    public static class PatientTable {

        /**
         * name of table
         */
        public static final String tableName = "tbl_patients";

        /**
         * Constants related to column name
         */
        public static class Columns {
            /**
             * Column name
             */
            public static final String firstName = "first_name";

            /**
             * Column name
             */
            public static final String lastName = "last_name";

            /**
             * Column name
             */
            public static final String medicalHistory = "medical_history";

            /**
             * Column name
             */
            public static final String bookingHistory = "booking_history";

            /**
             * Column name
             */
            public static final String createdAt = "created_at";

            /**
             * Column name
             */
            public static final String updatedAt = "updated_at";
        }
    }

    /**
     * Constants related to pharmacist table
     */
    public static class PharmacistTable {

        /**
         * name of table
         */
        public static final String tableName = "tbl_pharmacists";

        /**
         * Constants related to columns names
         */
        public static class Columns {
            /**
             * Column name
             */
            public static final String firstName = "first_name";

            /**
             * Column name
             */
            public static final String lastName = "last_name";

            /**
             * Column name
             */
            public static final String pharmacyName = "pharmacy_name";

            /**
             * Column name
             */
            public static final String contactNumber = "contact_number";

            /**
             * Column name
             */
            public static final String addressLine1 = "address_line_1";

            /**
             * Column name
             */
            public static final String addressLine2 = "address_line_2";

            /**
             * Column name
             */
            public static final String postalCode = "postal_code";

            /**
             * Column name
             */
            public static final String licenseNumber = "license_number";

            /**
             * Column name
             */
            public static final String createdAt = "created_at";

            /**
             * Column name
             */
            public static final String updatedAt = "updated_at";
        }
    }

    /**
     * Constants related to user table
     */
    public static class UserTable {

        /**
         * name of table
         */
        public static final String tableName = "tbl_users";

        /**
         * Constants related to columns name
         */
        public static class Columns {
            /**
             * Column name
             */
            public static final String userName = "username";

            /**
             * Column name
             */
            public static final String password = "password";

            /**
             * Column name
             */
            public static final String userRole = "user_role";

            /**
             * Column name
             */
            public static final String doctorId = "doctor_id";

            /**
             * Column name
             */
            public static final String patientId = "patient_id";

            /**
             * Column name
             */
            public static final String pharmacistId = "pharmacist_id";

            /**
             * Column name
             */
            public static final String createdAt = "created_at";

            /**
             * Column name
             */
            public static final String updatedAt = "updated_at";
            /**
             * Column name
             */
            public static final String resetToken = "reset_token";
            /**
             * Column name
             */
            public static final String isEmailVerified = "is_email_verified";
            /**
             * Column name
             */
            public static final String emailVerifyToken = "email_verify_token";
            /**
             * Column name
             */
            public static final String isAdminVerified = "is_admin_verified";

            /**
             * Column name
             */
            public static final String receiveEmailNotification = "receive_email_notification";

            /**
             * Column name
             */
            public static final String adminVerificationStatus = "is_admin_verified";

        }
    }

    /**
     * Constants related to the Blogs Table
     */
    public static class BlogsTable {

        /**
         * Name of the blogs table
         */
        public static final String tableName = "tbl_blogs";

        /**
         * Constants related to column names
         */
        public static class Columns {

            /**
             * Column name for the blog title
             */
            public static final String blogTitle = "blog_title";

            /**
             * Column name for the blog description
             */
            public static final String blogBody = "blog_body";

            /**
             * Column name for the creation timestamp
             */
            public static final String createdAt = "created_at";

            /**
             * Column name for the update timestamp
             */
            public static final String updatedAt = "updated_at";

            /**
             * Column name for the doctorId
             */
            public static final String doctorId = "doctor_id";

        }
    }

    /**
     * Constants related to appointment table
     */
    public static class AppointmentTable {

        /**
         * name of table
         */
        public static final String tableName = "tbl_appointments";

        /**
         * Columns of table tbl_appointments
         */
        public static class Columns {

            /**
             * Column name
             */
            public static final String doctorId = "doctor_id";

            /**
             * Column name
             */
            public static final String patientId = "patient_id";

            /**
             * Column name
             */
            public static final String pharmacistId = "pharmacist_id";

            /**
             * Column name
             */
            public static final String startTime = "start_time";

            /**
             * Column name
             */
            public static final String endTime = "end_time";

            /**
             * Column name
             */
            public static final String appointmentDate = "appointment_date";

            /**
             * Column name
             */
            public static final String status = "status";

            /**
             * Column name
             */
            public static final String remarksFromPatient = "remarks_from_patient";

            /**
             * Column name
             */
            public static final String weekDay = "week_day";

            /**
             * Column name
             */
            public static final String feedbackMessage = "feedback_message";

            /**
             * Column name for the rating
             */
            public static final String rating = "rating";

            /**
             * Column name
             */
            public static final String prescription = "prescription";

            /**
             * Column name
             */
            public static final String createdAt = "created_at";

            /**
             * Column name
             */
            public static final String updatedAt = "updated_at";
        }
    }

    /**
     * Constants related to chat table
     */
    public static class ChatTable {
        /**
         * name of table
         */
        public static final String tableName = "tbl_chat_messages";

        /**
         * Columns of table tbl_chat_messages
         */
        public static class Columns {
            /**
             * Column name
             */
            public static final String id = "id";
            /**
             * Column name
             */
            public static final String senderId = "sender_id";
            /**
             * Column name
             */
            public static final String receiverId = "receiver_id";
            /**
             * Column name
             */
            public static final String content = "content";
            /**
             * Column name
             */
            public static final String createdAt = "created_at";
        }
    }
}