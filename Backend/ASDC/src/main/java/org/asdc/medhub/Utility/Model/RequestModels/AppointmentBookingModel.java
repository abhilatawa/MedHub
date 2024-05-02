package org.asdc.medhub.Utility.Model.RequestModels;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Model format to book an appointment
 */
@Getter @Setter
@NoArgsConstructor
public class AppointmentBookingModel {

    /**
     * User id of doctor
     */
    private int doctorId;

    /**
     * Timeslot that patient is booking appointment for
     */
    private String timeSlot;

    /**
     * Start time of appointment
     */
    @Setter(AccessLevel.NONE)
    private Timestamp startTime;

    /**
     * Returns Timestamp of start time from time_slot string
     * @return Timestamp start time
     */
    public Timestamp getStartTime() {
        return this.getTimestampFromDateAndSlotTimeString(this.getAppointmentDate(),timeSlot.split(" to ")[0]);
    }

    /**
     * End time of appointment
     */
    @Setter(AccessLevel.NONE)
    private Timestamp endTime;

    /**
     * Returns Timestamp of end time from time_slot string
     * @return Timestamp end time
     */
    public Timestamp getEndTime(){
        return this.getTimestampFromDateAndSlotTimeString(this.getAppointmentDate(),timeSlot.split(" to ")[1]);
    }

    /**
     * Appointment date
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate appointmentDate;

    /**
     * Remarks from patient if any
     */
    private String remarksFromPatient;

    /**
     *
     * @param date Appointment date
     * @param timeSlotString Time slot string like  14:00
     * @return Timestamp equivalent object
     */
    private Timestamp getTimestampFromDateAndSlotTimeString(LocalDate date, String timeSlotString) {
        Timestamp timestamp=null;
        try {
            String combinedDateTimeString = date + "T" + timeSlotString;
            LocalDateTime combinedDateTime = LocalDateTime.parse(combinedDateTimeString,
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            // Convert to java.sql.Timestamp
            timestamp = Timestamp.valueOf(combinedDateTime);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return timestamp;
    }
}
