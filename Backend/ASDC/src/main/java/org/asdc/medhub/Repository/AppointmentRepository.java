package org.asdc.medhub.Repository;

import org.asdc.medhub.Utility.Enums.AppointmentStatus;
import org.asdc.medhub.Utility.Model.DatabaseModels.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

/**
 * Repository for db operations related to appointments
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {

    /**
     * Gets list of appointment with doctor id, status and week day
     * @param doctorId id of doctor
     * @param status Appointment status
     * @param weekDay Day of week
     * @return Returns list of appointment
     */
    List<Appointment> getAppointmentsByDoctorIdAndStatusAndWeekDay(int doctorId, AppointmentStatus status, DayOfWeek weekDay);

    /**
     * Gets appointment list by doctor id and status list
     * @param doctorId id of doctor
     * @param statusList status of list
     * @return List of appointment
     */
    List<Appointment> getAppointmentsByDoctorIdAndStatusIsIn(int doctorId,List<AppointmentStatus> statusList);

    /**
     * Gets all the appointments of patient
     * @param patientId Id patient from tbl_patient
     * @return List of appointments
     */
    List<Appointment> getAppointmentsByPatientId(int patientId);

    /**
     *
     * @param id of the Appointment booked
     * @return List of Appointments
     */
    Appointment getAppointmentById(int id);

    /**
     * @param doctorId Id of the current doctor
     * @return List of Appointments
     */
    List<Appointment> getAppointmentsByDoctorId(int doctorId);

    /**
     * Returns filtered appointment list
     * @param firstName search string
     * @param pharmacistId id of pharmacist
     * @return List of appointment
     */
    List<Appointment> getAppointmentsByPatient_FirstNameContainingAndPharmacist_Id(String firstName,int pharmacistId);

}
