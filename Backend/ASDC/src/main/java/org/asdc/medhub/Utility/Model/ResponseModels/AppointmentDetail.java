package org.asdc.medhub.Utility.Model.ResponseModels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.asdc.medhub.Utility.Enums.AppointmentStatus;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.DayOfWeek;

/**
 * Appointment detail model
 */
@Getter @Setter
@NoArgsConstructor
public class AppointmentDetail {

    /**
     * id of appointment
     */
    private int id;

    /**
     * Date of appointment
     */
    private Date appointmentDate;

    /**
     * Timeslot of appointment
     */
    private String timeSlot;

    /**
     * Status of appointment
     */
    private AppointmentStatus status;

    /**
     * Remarks from patient for appointment
     */
    private String remarksFromPatient;

    /**
     * Day of week of appointment
     */
    private DayOfWeek dayOfWeek;

    /**
     * Name of patient
     */
    private String patientName;

    /**
     * Email id of linked patient
     */
    private String patientEmail;

    /**
     * Name of doctor
     */
    private String doctorName;

    /**
     * Feedback message for the doctor
     */

    private String feedbackMessage;

    /**
     * rating for the doctor
     */
    private int rating;

     /**
     * Contact number of linked doctor
     */
    private String doctorContactNumber;

    /**
     * Email id of linked doctor
     */
    private String doctorEmail;

    /**
     * Prescription provided by doctor
     */
    private String prescription;

    /**
     * pharmacist id from tbl_pharmacist
     */
    private int pharmacistId;

    /**
     * Name of pharmacy
      */
    private String pharmacyName;

    /**
     * Timestamp when appointment was created
     */
    private Timestamp createdAt;
}
