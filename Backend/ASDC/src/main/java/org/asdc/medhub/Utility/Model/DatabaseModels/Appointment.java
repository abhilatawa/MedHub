package org.asdc.medhub.Utility.Model.DatabaseModels;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.asdc.medhub.Utility.Constant.DatabaseConstants;
import org.asdc.medhub.Utility.Enums.AppointmentStatus;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.DayOfWeek;

/**
 * represents table appointments of database
 */
@Getter @Setter
@NoArgsConstructor
@Entity(name=DatabaseConstants.AppointmentTable.tableName)
public class Appointment {

    /**
     * Primary key column
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Linked doctor of appointment
     */
    @ManyToOne
    @JoinColumn(name=DatabaseConstants.AppointmentTable.Columns.doctorId)
    private Doctor doctor;

    /**
     * Linked patient of appointment
     */
    @ManyToOne
    @JoinColumn(name= DatabaseConstants.AppointmentTable.Columns.patientId)
    private Patient patient;

    /**
     * Linked pharmacist of appointment
     */
    @ManyToOne
    @JoinColumn(name= DatabaseConstants.AppointmentTable.Columns.pharmacistId)
    private Pharmacist pharmacist;

    /**
     * Start time of appointment
     */
    @Column(name = DatabaseConstants.AppointmentTable.Columns.startTime)
    private Timestamp startTime;

    /**
     * End time of appointment
     */
    @Column(name = DatabaseConstants.AppointmentTable.Columns.endTime)
    private Timestamp endTime;

    /**
     * Appointment date
     */
    @Column(name = DatabaseConstants.AppointmentTable.Columns.appointmentDate)
    private Date appointmentDate;

    /**
     * Appointment status
     */
    @Enumerated(EnumType.STRING)
    @Column(name = DatabaseConstants.AppointmentTable.Columns.status)
    private AppointmentStatus status;

    /**
     * Remarks from patient if any
     */
    @Column(name=DatabaseConstants.AppointmentTable.Columns.remarksFromPatient)
    private String remarksFromPatient;

    /**
     * Day of week of appointment
     */
    @Enumerated(EnumType.STRING)
    @Column(name=DatabaseConstants.AppointmentTable.Columns.weekDay)
    private DayOfWeek weekDay;

    /**
     * Feedback message
     */
    @Column(name = DatabaseConstants.AppointmentTable.Columns.feedbackMessage, length = 5000)
    private String feedbackMessage;

    /**
     * Rating provided by the patient
     */
    @Column(name = DatabaseConstants.AppointmentTable.Columns.rating)
    private int rating;
    
     /** 
     * Prescription by doctor
     */
    @Column(name=DatabaseConstants.AppointmentTable.Columns.prescription,length = 2000)
    private String prescription;

    /**
     * Timestamp when record was created
     */
    @Column(name = DatabaseConstants.AppointmentTable.Columns.createdAt)
    private Timestamp createdAt;

    /**
     * Timestamp when record was updated
     */
    @Column(name = DatabaseConstants.AppointmentTable.Columns.updatedAt)
    private Timestamp updatedAt;


}
