package org.asdc.medhub.Controller;

import jakarta.validation.Valid;
import org.asdc.medhub.Service.Interface.IDoctorService;
import org.asdc.medhub.Utility.Constant.ProjectConstants;
import org.asdc.medhub.Utility.Model.DatabaseModels.User;
import org.asdc.medhub.Utility.Model.ResponseModel;
import org.asdc.medhub.Utility.Model.ResponseModels.AppointmentDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.DoctorDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.PharmacistDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.access.annotation.Secured;
import java.util.List;
import java.util.Map;

/**
 * Controller for handling requests related to doctor
 */
@Secured("DOCTOR")
@RestController
@RequestMapping(ProjectConstants.Routes.Doctor.mainRoute)
public class DoctorController {

    /**
     * Doctor service instance
     */
    private final IDoctorService doctorService;

    /**
     * Parameterized constructor
     * @param doctorService - bean injected doctor service instance
     */
    @Autowired
    public DoctorController(IDoctorService doctorService){
        this.doctorService=doctorService;
    }

    /**
     * Endpoint to retrieve the profile of the authenticated doctor
     *
     * @return ResponseEntity containing ResponseModel with DoctorDetail
     */
    @ResponseBody
    @GetMapping(ProjectConstants.Routes.Doctor.details)
    public ResponseEntity<ResponseModel<DoctorDetail>> getDoctorProfile() {
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(this.doctorService.getDoctorProfile(email));
    }

    /**
     * Endpoint to edit the profile of the authenticated doctor
     *
     * @param doctorDetail DoctorDetail object containing updated doctor profile information
     * @return ResponseEntity containing ResponseModel with a success message or error
     */
    @ResponseBody
    @PatchMapping(ProjectConstants.Routes.Doctor.editDetails)
    public ResponseEntity<ResponseModel<DoctorDetail>> editDoctorProfile(@Valid @RequestBody DoctorDetail doctorDetail) {
        return ResponseEntity.ok(this.doctorService.editDoctorProfile(doctorDetail));
    }

    /**
     * Endpoint to upload a profile picture for a doctor.
     *
     * @param profilePicture The profile picture file to upload.
     * @param doctorId The ID of the doctor whose profile picture is being uploaded.
     * @return ResponseEntity containing a ResponseModel<String> indicating the result of the upload operation.
     */
    @PostMapping(ProjectConstants.Routes.Doctor.uploadProfilePicture)
    public ResponseEntity<ResponseModel<String>> uploadProfilePicture(@RequestParam("profilePicture") MultipartFile profilePicture,@RequestParam("doctorId") int doctorId){
        return ResponseEntity.ok(this.doctorService.uploadProfilePicture(profilePicture,doctorId));
    }

    /**
     * Sets email notification preference of doctor
     * @param emailNotificationPreference boolean field of flag
     * @return RsponseModel
     */
    @PostMapping(ProjectConstants.Routes.Doctor.setNotificationPreference)
    public ResponseEntity<ResponseModel<String>> setEmailNotificationPreference(@RequestParam("preference") boolean emailNotificationPreference){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(this.doctorService.setEmailNotificationPreferenceForDoctor(email,emailNotificationPreference));
    }

    /**
     * Change password of doctor
     * @param newPassword new password
     * @return ResponseModel
     */
    @PostMapping(ProjectConstants.Routes.Doctor.changePassword)
    public ResponseEntity<ResponseModel<String>> changePassword(@RequestParam("new_password") String newPassword){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(this.doctorService.changePassword(email,newPassword));
    }

    /**
     * Retrieves all the appointment of doctor
     * @param activeAppointment If need BOOKED appointments only else false
     * @param authentication Current logged in user
     * @return List of appointment details
     */
    @GetMapping(ProjectConstants.Routes.Doctor.getAppointments)
    public ResponseEntity<ResponseModel<List<AppointmentDetail>>> getAppointments(@RequestParam("active_appointment") boolean activeAppointment, Authentication authentication){
        User user=(User)authentication.getPrincipal();
        return ResponseEntity.ok(this.doctorService.getAppointments(activeAppointment,user.getDoctor().getId()));
    }

    /**
     * Retrieves all verified pharmacists filtered by pharmacy name
     * @param pharmacyNameSearchString search string for pharmacy name
     * @return List of PharmacistDetail model
     */
    @GetMapping(ProjectConstants.Routes.Doctor.getVerifiedFilteredPharmacists)
    public ResponseEntity<ResponseModel<List<PharmacistDetail>>> getVerifiedFilteredPharmacists(@RequestParam("searchString") String pharmacyNameSearchString){
        return ResponseEntity.ok(this.doctorService.getFilteredAndVerifiedPharmacistList(pharmacyNameSearchString));
    }

    /**
     * Updates appointment
     * @param appointmentDetail Appointment detail model as request body
     * @return Appointment detail model if updated in database
     */
    @PatchMapping(ProjectConstants.Routes.Doctor.updatedAppointment)
    public ResponseEntity<ResponseModel<AppointmentDetail>> updateAppointment(@Valid @RequestBody AppointmentDetail appointmentDetail){
        return ResponseEntity.ok(this.doctorService.updateAppointmentDetail(appointmentDetail));
    }
    /**
     * Retrieves all the Feedback of doctor
     * @param authentication Current logged in user
     * @return List of Feedback for doctors
     */
    @GetMapping(ProjectConstants.Routes.Doctor.getDoctorFeedbackDetails)
    public ResponseEntity<ResponseModel<Map<String, Object>>> getDoctorFeedbackDetails(Authentication authentication) {
        int doctorId = ((User)authentication.getPrincipal()).getDoctor().getId();
        ResponseModel<Map<String, Object>> responseModel = this.doctorService.getDoctorFeedbackDetails(doctorId);
        return ResponseEntity.ok(responseModel);
        }
    }

