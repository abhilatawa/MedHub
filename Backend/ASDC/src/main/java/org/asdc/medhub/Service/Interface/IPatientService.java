package org.asdc.medhub.Service.Interface;

import org.asdc.medhub.Utility.Model.DatabaseModels.User;
import org.asdc.medhub.Utility.Model.RequestModels.AppointmentBookingModel;
import org.asdc.medhub.Utility.Model.RequestModels.DoctorFilterModel;
import org.asdc.medhub.Utility.Model.ResponseModel;
import org.asdc.medhub.Utility.Model.ResponseModels.AppointmentDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.DoctorDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.PatientDetail;
import java.util.HashMap;
import java.util.List;

/**
 * Service interface for handling patient-related functionalities.
 */
public interface IPatientService {

    /**
     * Retrieves patient profile details.
     * @return A ResponseModel containing patient profile information.
     */
    ResponseModel<PatientDetail> getPatientProfile(String email);

    /**
     * Edits patient profile details.
     * @return A ResponseModel containing patient profile information update status.
     */
    ResponseModel<String> editPatientProfile(String email, PatientDetail patientDetail);

    /**
     * Searches given string in specialization table using like query
     * @param searchString - string to search
     * @return List of string of specialization matched
     */
    ResponseModel<List<String>> getMedicalSpecializationByName(String searchString);

    /**
     * Returns list of DoctorDetail objects with applied filter
     * If filter empty then returns all verified doctors
     * @param filter DoctorFilterModel
     * @return List of DoctorDetail
     */
    ResponseModel<List<DoctorDetail>> getAllFilteredDoctorList(DoctorFilterModel filter);

    /**
     * Create a new appointment entry in database
     * @param appointment appointment booking details
     * @param currentPatient current logged in patient
     * @return AppointmentBookingModel
     */
    ResponseModel<AppointmentBookingModel> bookAppointment(AppointmentBookingModel appointment,User currentPatient);

    /**
     * Gets doctor's available timeslots
     * @param doctorId Doctor id
     * @return HashMap of day wise available timeslots
     */
    ResponseModel<HashMap<String,List<String>>> getDoctorAvailabilityTimeSlots(int doctorId);

    /**
     * Retrieves individual details of the doctor using email.
     * @param doctorId The id of the doctor whose profile details need to be retrieved.
     * @return A ResponseModel containing the doctor's details if found, or an error message if no doctor is found.
     */
    ResponseModel<DoctorDetail> getDoctorDetails(int doctorId);

    /**
     * Returns all appointments of current logged in patient
     * @param user User model of patient
     * @return List of appointments
     */
    ResponseModel<List<AppointmentDetail>> getAppointments(User user);

    /**
     * Returns updated appointment detail model
     * @param appointmentDetail to be updated
     * @return updateAppointmentDetail
     */
    ResponseModel<AppointmentDetail> updateAppointmentDetail(AppointmentDetail appointmentDetail);
}
