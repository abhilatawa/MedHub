package org.asdc.medhub.Controller;

import jakarta.validation.Valid;
import org.asdc.medhub.Service.Interface.IAuthService;
import org.asdc.medhub.Service.Interface.IPatientService;
import org.asdc.medhub.Utility.Constant.ProjectConstants;
import org.asdc.medhub.Utility.Model.RequestModels.DoctorRegistrationModel;
import org.asdc.medhub.Utility.Model.RequestModels.PatientRegistrationModal;
import org.asdc.medhub.Utility.Model.RequestModels.PharmacistRegistrationModel;
import org.asdc.medhub.Utility.Model.ResponseModel;
import org.asdc.medhub.Utility.Model.DatabaseModels.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller handles requests related to authentication and registration
 */
@RestController
@RequestMapping(ProjectConstants.Routes.Auth.mainRoute)
public class AuthController {

    /**
     * Instance of auth service
     */
    private final IAuthService authService;

    /**
     * Instance of doctor service
     */
    private final IPatientService patientService;

    /**
     * Parameterized constructor
     *
     * @param authService - authentication service injection
     */
    @Autowired
    public AuthController(IAuthService authService,IPatientService patientService) {
        this.authService = authService;
        this.patientService=patientService;
    }

    //region PUBLIC METHODS

    /**
     * Handles registration request for doctors
     *
     * @param doctorRegistrationModel fields for doctor registration
     * @return ResponseModel
     */
    @PostMapping(ProjectConstants.Routes.Auth.registerDoctor)
    public ResponseEntity<ResponseModel<String>> registerDoctor(@Valid @RequestBody DoctorRegistrationModel doctorRegistrationModel) {
        return ResponseEntity.ok(this.authService.registerUser(doctorRegistrationModel));
    }

    /**
     * Handles registration request for patient
     *
     * @param patientRegistrationModal - fields for registration
     * @return ResponseEntity<ResponseModel < String>>
     */
    @PostMapping(ProjectConstants.Routes.Auth.registerPatient)
    public ResponseEntity<ResponseModel<String>> registerPatient(@Valid @RequestBody PatientRegistrationModal patientRegistrationModal) {
        return ResponseEntity.ok(this.authService.registerUser(patientRegistrationModal));
    }

    /**
     * Handles registration request for pharmacists
     *
     * @param pharmacistRegistrationModel - fields for registration
     * @return ResponseEntity<ResponseModel < String>>
     */
    @PostMapping(ProjectConstants.Routes.Auth.registerPharmacist)
    public ResponseEntity<ResponseModel<String>> registerPharmacist(@Valid @RequestBody PharmacistRegistrationModel pharmacistRegistrationModel) {
        return ResponseEntity.ok(this.authService.registerUser(pharmacistRegistrationModel));
    }

    /**
     * Handles signing request for any user
     *
     * @param user - fields for login
     * @return ResponseModel
     */
    @PostMapping(ProjectConstants.Routes.Auth.signInUser)
    public ResponseEntity<ResponseModel<String>> signInUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(this.authService.signInUser(user));
    }

    /**
     * Handles the forgot password request.
     * Generates and sends a password reset link to the provided email address.
     *
     * @param requestBody A map containing the email address of the user.
     * @return ResponseEntity containing a ResponseModel<String>.
     */
    @PostMapping(ProjectConstants.Routes.Auth.forgotPassword)
    public ResponseEntity<ResponseModel<String>> forgotPassword(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        return ResponseEntity.ok(this.authService.addForgotPasswordRequestAndSendEmail(email));
    }

    /**
     * Handles the reset password request.
     * Resets the password for the user associated with the provided email address and token.
     *
     * @param requestBody A map containing the email address, reset token, and new password.
     * @return ResponseEntity containing a ResponseModel<String>.
     */
    @PostMapping(ProjectConstants.Routes.Auth.resetPassword)
    public ResponseEntity<ResponseModel<String>> resetPassword(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String token = requestBody.get("token");
        String newPassword = requestBody.get("newPassword");
        return ResponseEntity.ok(this.authService.resetPassword(email, token, newPassword));
    }

    /**
     * Handles the email verification for the user
     * Sets the isEmailVerified to true when the user clicks on the link sent to their email
     * requestParam containing email parameter from the url
     *
     * @return ResponseEntity containing a ResponseModel<String>.
     */
    @PostMapping(ProjectConstants.Routes.Auth.verifyEmail)
    public ResponseEntity<ResponseModel<String>> verifyEmail(@RequestParam("email") String email, @RequestParam("token") String token) {
        return ResponseEntity.ok(this.authService.verifyEmail(email, token));
    }

    /**
     * Searches in specializations table for given string like entry
     * @param searchString - character(s) to search
     * @return List of String of Specializations
     */
    @GetMapping(ProjectConstants.Routes.Auth.searchSpecializations)
    public ResponseModel<List<String>> getMedicalSpecializationsByName(@RequestParam("searchString") String searchString){
        return this.patientService.getMedicalSpecializationByName(searchString);
    }
    //endregion
}
