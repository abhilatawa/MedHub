package org.asdc.medhub.Service.Interface;

import org.asdc.medhub.Utility.Model.RequestModels.DoctorRegistrationModel;
import org.asdc.medhub.Utility.Model.RequestModels.PatientRegistrationModal;
import org.asdc.medhub.Utility.Model.RequestModels.PharmacistRegistrationModel;
import org.asdc.medhub.Utility.Model.ResponseModel;
import org.asdc.medhub.Utility.Model.DatabaseModels.User;

/**
 * Interface for AuthService
 */
public interface IAuthService {

    /**
     * Registers doctor if nor exists
     * @param doctorRegistrationModel - registration detail for doctor
     * @return ResponseModel
     */
    ResponseModel<String> registerUser(DoctorRegistrationModel doctorRegistrationModel);

    /**
     * Registers patient if not exists
     * @param patientRegistrationModal - registration details for patient
     * @return ResponseModel
     */
    ResponseModel<String> registerUser(PatientRegistrationModal patientRegistrationModal);

    /**
     * Registers pharmacist if not exists
     * @param pharmacistRegistrationModel  - registration details for pharmacist
     * @return ResponseModel
     */
    ResponseModel<String> registerUser(PharmacistRegistrationModel pharmacistRegistrationModel);

    /**
     * Sign In user and return JWT token
     * @param user - contains user credentials
     * @return ResponseModel
     */
    ResponseModel<String> signInUser(User user);

    /**
     * Initiate the process of resetting the password for a user with the provided email, reset token, and new password.
     * @param email The email address of the user.
     * @param resetToken The reset token sent to the user's email.
     * @param newPassword The new password to set.
     * @return A response model indicating the result of the password reset initiation.
     */
    ResponseModel<String> resetPassword(String email, String resetToken, String newPassword);

    /**
     * Initiate the process of resetting the password for a user who has forgotten their password.
     * @param email The email address of the user.
     * @return A response model indicating the result of the password reset request.
     */
    ResponseModel<String> addForgotPasswordRequestAndSendEmail(String email);

    /**
     * Verifies the email address of a user.
     *
     * @param email The email address of the user to be verified.
     * @return A response model indicating the result of the email verification process.
     */
    ResponseModel<String> verifyEmail(String email, String token);
}
