package org.asdc.medhub.Service.Interface;

import org.asdc.medhub.Utility.Model.ResponseModel;
import org.asdc.medhub.Utility.Model.ResponseModels.AppointmentDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.DoctorDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.PharmacistDetail;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Service interface for managing doctors
 */
public interface IDoctorService {

    /**
     * Edits the profile of a doctor.
     *
     * @param doctorDetail The DoctorDetail object containing updated doctor profile information.
     * @return A ResponseModel indicating the success or failure of the operation.
     */
    ResponseModel<DoctorDetail> editDoctorProfile(DoctorDetail doctorDetail);

    /**
     * Retrieves individual details of the doctor using email.
     * @param doctorEmail The id of the doctor whose profile details need to be retrieved.
     * @return A ResponseModel containing the doctor's details if found, or an error message if no doctor is found.
     */
    ResponseModel<DoctorDetail> getDoctorProfile(String doctorEmail);

    /**
     * Saves user profile picture in local file storage
     * @param profilePicture MultipartFile
     * @return String of image path
     */
    ResponseModel<String> uploadProfilePicture(MultipartFile profilePicture,int doctorId);

    /**
     * Sets email notification preference for the doctor
     * @param doctorEmail email of doctor
     * @param emailPreference flag of email notification
     * @return ResponseModel
     */
    ResponseModel<String> setEmailNotificationPreferenceForDoctor(String doctorEmail,boolean emailPreference);

    /**
     * Changes existing user password with new password
     * @param doctorEmail email of the user
     * @param newPassword new password entered by user
     * @return ResponseModel
     */
    ResponseModel<String> changePassword(String doctorEmail,String newPassword);

    /**
     * Retrieves appointment list according to given status and doctori id
     * @param activeAppointments if we want only active appointment or not
     * @param doctorId doctor id of whose appointment we need to fetch
     * @return List of appointment detail model
     */
    ResponseModel<List<AppointmentDetail>> getAppointments(boolean activeAppointments, int doctorId);

    /**
     * Searches verified pharmacist based on pharmacy name
     * @param pharmacistNameSearchString search string for name of pharmacy
     * @return List of PharmacistDetail
     */
    ResponseModel<List<PharmacistDetail>> getFilteredAndVerifiedPharmacistList(String pharmacistNameSearchString);

    /**
     * Updates appointment in database
     * @param appointmentDetail Appointment detail model
     * @return Response model of appointment detail model
     */
    ResponseModel<AppointmentDetail> updateAppointmentDetail(AppointmentDetail appointmentDetail);

    /**
     * Retrieves the feedback for the given doctor
     * @param doctorId doctor id of whose feedback details needs to be fetched
     * @return the list of feedback for the doctor
     */
    ResponseModel<Map<String, Object>> getDoctorFeedbackDetails(int doctorId);
}
