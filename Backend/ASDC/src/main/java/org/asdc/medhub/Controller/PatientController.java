package org.asdc.medhub.Controller;
import jakarta.validation.Valid;
import org.asdc.medhub.Utility.Constant.ProjectConstants;
import org.asdc.medhub.Utility.Model.DatabaseModels.User;
import org.asdc.medhub.Utility.Model.RequestModels.AppointmentBookingModel;
import org.asdc.medhub.Utility.Model.RequestModels.DoctorFilterModel;
import org.asdc.medhub.Utility.Model.ResponseModels.AppointmentDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.DoctorDetail;
import org.springframework.security.core.Authentication;
import org.asdc.medhub.Service.Interface.IPatientService;
import org.asdc.medhub.Utility.Model.ResponseModel;
import org.asdc.medhub.Utility.Model.ResponseModels.PatientDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * Controller for handling requests related to patient
 */
@Secured("PATIENT")
@RestController
@RequestMapping(ProjectConstants.Routes.Patient.mainRoute)
public class PatientController {

    /**
     * Instance of patient service
     */
    private final IPatientService patientService;

    /**
     * Constructor for initializing the PatientController with an IPatientService instance.
     * @param patientService The service responsible for handling patient-related tasks.
     */
    @Autowired
    public PatientController(IPatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * Endpoint for fetching details of an individual patient.
     * @return ResponseEntity containing the response model with the details of the requested patient.
     */
    @GetMapping(ProjectConstants.Routes.Patient.getProfile)
    public ResponseEntity<ResponseModel<PatientDetail>> getPatientProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return ResponseEntity.ok(this.patientService.getPatientProfile(email));
    }

    /**
     * Endpoint for patching details of an individual patient.
     * @param patientDetail model containing all the details of updated patient model
     * @return ResponseEntity containing the response model with the details of the requested patient.
     */
    @PatchMapping(ProjectConstants.Routes.Patient.editProfile)
    public ResponseEntity<ResponseModel<String>> editPatientProfile(@Valid @RequestBody PatientDetail patientDetail) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return ResponseEntity.ok(this.patientService.editPatientProfile(email,patientDetail));
    }

    /**
     * Returns doctor details with applied filter
     * @param filter DoctorFilterModel that contains specialization filter data
     */
    @PostMapping(ProjectConstants.Routes.Patient.getFilteredDoctorList)
    public ResponseEntity<ResponseModel<List<DoctorDetail>>> getAllFilteredDoctorList(@Valid @RequestBody DoctorFilterModel filter){
        return ResponseEntity.ok(this.patientService.getAllFilteredDoctorList(filter));
    }

    /**
     * Searches in specializations table for given string like entry
     * @param searchString - character(s) to search
     * @return List of String of Specializations
     */
    @GetMapping(ProjectConstants.Routes.Patient.searchSpecialization)
    public ResponseEntity<ResponseModel<List<String>>> getMedicalSpecializationsByName(@RequestParam("searchString") String searchString){
        return ResponseEntity.ok(this.patientService.getMedicalSpecializationByName(searchString));
    }

    /**
     * Books an appointment with doctor
     * @param appointment AppointmentBookingModel
     * @return ResponseModel
     */
    @PostMapping(ProjectConstants.Routes.Patient.bookAppointment)
    public ResponseEntity<ResponseModel<AppointmentBookingModel>> bookAppointment(@RequestBody AppointmentBookingModel appointment,Authentication authenticationContext){
        return ResponseEntity.ok(this.patientService.bookAppointment(appointment,(User)authenticationContext.getPrincipal()));
    }

    /**
     * Returns list of timeslots weekday wise
     * @param doctorId doctor id of which available slots to return
     * @return HashMap of week day and time slots
     */
    @GetMapping(ProjectConstants.Routes.Patient.getDoctorAvailability)
    public ResponseEntity< ResponseModel<HashMap<String,List<String>>>> getDoctorsAvailableTimeSlots(@RequestParam("doctorId") int doctorId){
        return ResponseEntity.ok(this.patientService.getDoctorAvailabilityTimeSlots(doctorId));
    }

    /**
     * Returns details about doctor from id
     * @param doctorId id of doctor
     * @return DoctorDetail model
     */
    @GetMapping(ProjectConstants.Routes.Patient.getDoctorDetails)
    public ResponseEntity<ResponseModel<DoctorDetail>> getDoctorDetails(@RequestParam("doctorId") int doctorId){
        return ResponseEntity.ok(this.patientService.getDoctorDetails(doctorId));
    }

    /**
     * Returns list of appointments of patient
     * @param authentication user principle of current logged user
     * @return List of AppointmentDetail
     */
    @GetMapping(ProjectConstants.Routes.Patient.getPatientAppointments)
    public ResponseEntity<ResponseModel<List<AppointmentDetail>>> getAppointments(Authentication authentication){
        User user=(User)authentication.getPrincipal();
        return ResponseEntity.ok(this.patientService.getAppointments(user));
    }

    /**
     * Saves feedback in to database
     * @param appointmentDetail Appointment detail model to update for feedback
     * @return Updated appointment detail model
     */
    @PatchMapping(ProjectConstants.Routes.Patient.updatePatientFeedback)
    public ResponseEntity<ResponseModel<AppointmentDetail>>saveFeedback(@Valid @RequestBody AppointmentDetail appointmentDetail){
        return ResponseEntity.ok(this.patientService.updateAppointmentDetail(appointmentDetail));
    }
}
