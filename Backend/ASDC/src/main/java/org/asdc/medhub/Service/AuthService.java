package org.asdc.medhub.Service;

import org.asdc.medhub.Repository.UserRepository;
import org.asdc.medhub.Service.Interface.IAuthService;
import org.asdc.medhub.Service.Interface.IJwtService;
import org.asdc.medhub.Utility.Enums.AdminVerificationStatus;
import org.asdc.medhub.Utility.Enums.UserRole;
import org.asdc.medhub.Utility.Model.DatabaseModels.*;
import org.asdc.medhub.Utility.Model.RequestModels.DoctorRegistrationModel;
import org.asdc.medhub.Utility.Model.RequestModels.PatientRegistrationModal;
import org.asdc.medhub.Utility.Model.RequestModels.PharmacistRegistrationModel;
import org.asdc.medhub.Utility.Model.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.micrometer.common.util.StringUtils;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.asdc.medhub.Utility.Constant.ProjectConstants;

/**
 * Contains supported methods for auth controller
 */
@Service
public class AuthService implements IAuthService {

    /**
     * JwtService instance
     */
    private final IJwtService jwtService;

    /**
     * UserRepository instance
     */
    private final UserRepository userRepository;

    /**
     * EmailService instance
     */
    private final EmailService emailService;

    /**
     * Parameterized constructor with bean injection
     * @param jwtService -JWT service instance
     * @param userRepository - Use repository instance
     * @param emailService - Email service instance
     */
    @Autowired
    public AuthService(IJwtService jwtService,
                       UserRepository userRepository,
                       EmailService emailService
    ) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    //region PUBLIC METHODS
    /**
     * Registers doctor if nor exists
     * @param doctorForRegistration - registration detail for doctor
     * @return ResponseModel
     */
    public ResponseModel<String> registerUser(DoctorRegistrationModel doctorForRegistration){
        ResponseModel<String> response=new ResponseModel<String>();

        //Check if doctor already exists or not
        var isDoctorExists=this.userRepository.existsUserByUsernameAndUserRole(doctorForRegistration.getUsername(),doctorForRegistration.getUserRole());

        if(!isDoctorExists){
            var user=this.getUserModelFromDoctorRegistrationModel(doctorForRegistration);
            this.userRepository.save(user);

            String verificationLink = ProjectConstants.backendRootURL+ "/auth/api/verify" + "?email=" + user.getUsername() + "&token=" + user.getEmailVerifyToken();
            // Send email using EmailService
            this.emailService.sendEmail(
                    user.getUsername(),
                    "Please verify your account at MedHub",
                    "Please click the link below to verify your account:\n" + "\n" + "\n" + verificationLink);

            response.message="Doctor registered successfully.";
            response.isSuccess=true;
        }
        else{
            response.responseData="";
            response.message="Doctor already registered.";
        }
        return response;
    }

    /**
     * Registers patient if not exists
     * @param patientForRegistration - registration details for patient
     * @return ResponseModel
     */
    public ResponseModel<String> registerUser(PatientRegistrationModal patientForRegistration){
        ResponseModel<String> response=new ResponseModel<String>();

        //Check if patient already exists or not
        var isDoctorExists=this
                .userRepository
                .existsUserByUsernameAndUserRole(patientForRegistration.getUsername(),patientForRegistration.getUserRole());

        if(!isDoctorExists){
            var user=this.getUserModelFromPatientRegistrationModel(patientForRegistration);
            this.userRepository.save(user);

            String verificationLink = ProjectConstants.backendRootURL+ "/auth/api/verify" + "?email=" + user.getUsername() + "&token=" + user.getEmailVerifyToken();
            // Send email using EmailService
            this.emailService.sendEmail(
                    user.getUsername(),
                    "Please verify your account at MedHub",
                    "Please click the link below to verify your account:\n" + "\n" + "\n" + verificationLink);

            response.message="Patient registered successfully.";
            response.isSuccess=true;
        }
        else{
            response.responseData="";
            response.message="Patient already registered.";
        }
        return response;
    }

    /**
     * Registers the pharmacist in database if not exists
     * @param pharmacistRegistrationModel  - registration details for pharmacist
     * @return ResponseModel
     */
    public ResponseModel<String> registerUser(PharmacistRegistrationModel pharmacistRegistrationModel){
        ResponseModel<String> response=new ResponseModel<String>();

        //Check if patient already exists or not
        var isPharmacistExist=this
                .userRepository
                .existsUserByUsernameAndUserRole(pharmacistRegistrationModel.getUsername(),pharmacistRegistrationModel.getUserRole());

        if(!isPharmacistExist){
            var user=this.getUserModelFromPharmacistRegistrationModel(pharmacistRegistrationModel);
            this.userRepository.save(user);

            response.message="Pharmacist registered successfully.";
            response.isSuccess=true;
        }
        else{
            response.responseData="";
            response.message="Pharmacist already registered.";
        }
        return response;
    }

    /**
     * Sign In user and return JWT token
     * @param user - contains user credentials
     * @return ResponseModel
     */
    public ResponseModel<String> signInUser(User user){
        ResponseModel<String> response=new ResponseModel<String>();

        //replace password with its hash value before using it
        user.hashPassword();
        boolean isAuthenticationSuccesfull=this
                .userRepository
                .existsUserByUsernameAndPasswordAndUserRole(
                        user.getUsername(),
                        user.getPassword(),
                        user.getUserRole());

        if(isAuthenticationSuccesfull){
            User dbUser = userRepository.findUserByUsername(user.getUsername());
            if ((UserRole.DOCTOR.equals(user.getUserRole()) || UserRole.PHARMACIST.equals(user.getUserRole())) && dbUser.getAdminVerificationStatus() == AdminVerificationStatus.PENDING) {
                response.message = "User is yet to be admin approved.";
            } else if ((UserRole.DOCTOR.equals(user.getUserRole()) || UserRole.PHARMACIST.equals(user.getUserRole())) && dbUser.getAdminVerificationStatus() == AdminVerificationStatus.REJECTED) {
                response.message = "You are rejected by MedHub admin. Please follow steps on mail for further process";
            } else {
                response.responseData = this.jwtService.generateToken(dbUser.getUsername(), Long.toString(dbUser.getId()), dbUser.getUserRole());
                response.message = "Authentication successful.";
                response.isSuccess = true;
            }
        } else {
            response.responseData = "";
            response.message = "Authentication failed.";
        }
        return response;
    }
    //endregion

    //region PRIVATE METHODS
    /**
     * Creates User model from DoctorRegistrationModel
     * @param doctorRegistrationModel - model containing doctor registration details
     * @return User
     */
    private User getUserModelFromDoctorRegistrationModel(DoctorRegistrationModel doctorRegistrationModel){
        User user=new User();
        user.setUsername(doctorRegistrationModel.getUsername());
        user.setPassword(doctorRegistrationModel.getPassword());
        user.hashPassword();
        user.setUserRole(doctorRegistrationModel.getUserRole());
        user.setCreatedAt(Timestamp.from(Instant.now()));
        user.setUpdatedAt(Timestamp.from(Instant.now()));
        user.setEmailVerifyToken(generateResetToken(doctorRegistrationModel.getUsername()));
        user.setAdminVerificationStatus(AdminVerificationStatus.PENDING);

        Doctor doctor=new Doctor();
        doctor.setFirstName(doctorRegistrationModel.getFirstName());
        doctor.setLastName(doctorRegistrationModel.getLastName());
        doctor.setContactNumber(doctorRegistrationModel.getContactNumber());
        doctor.setAddressLine1(doctorRegistrationModel.getAddressLine1());
        doctor.setAddressLine2(doctorRegistrationModel.getAddressLine2());
        doctor.setPostalCode(doctorRegistrationModel.getPostalCode());
        doctor.setLicenseNumber(doctorRegistrationModel.getLicenseNumber());
        this.setSpecializationOfDoctorToDoctorModel(doctorRegistrationModel.getSpecializationsOfDoctor(),doctor);

        doctor.setCreatedAt(Timestamp.from(Instant.now()));
        doctor.setUpdatedAt(Timestamp.from(Instant.now()));
        user.setDoctor(doctor);
        return user;
    }

    /**
     * Sets the specializations in doctor and adds doctor link in all specializations
     * @param specializationOfDoctor - List os specializations to add in doctor's object
     * @param doctor - Reference to the target doctor object
     */
    private void setSpecializationOfDoctorToDoctorModel(List<String> specializationOfDoctor,Doctor doctor){
        specializationOfDoctor.forEach(item->{
            SpecializationOfDoctor specialization=new SpecializationOfDoctor();
            specialization.setSpecialization(item);
            specialization.setCreatedAt(Timestamp.from(Instant.now()));
            specialization.setUpdatedAt(Timestamp.from(Instant.now()));
            specialization.setDoctor(doctor);
            doctor.getSpecializationsOfDoctor().add(specialization);
        });
    }

    /**
     * Creates User model from PatientRegistrationModal
     * @param patientRegistrationModal - model containing patient registration details
     * @return User
     */
    private User getUserModelFromPatientRegistrationModel(PatientRegistrationModal patientRegistrationModal){
        User user=new User();
        user.setUsername(patientRegistrationModal.getUsername());
        user.setPassword(patientRegistrationModal.getPassword());
        user.hashPassword();
        user.setUserRole(patientRegistrationModal.getUserRole());
        user.setCreatedAt(Timestamp.from(Instant.now()));
        user.setUpdatedAt(Timestamp.from(Instant.now()));
        user.setEmailVerifyToken(generateResetToken(patientRegistrationModal.getUsername()));

        user.setAdminVerificationStatus(AdminVerificationStatus.VERIFIED);
        Patient patient=new Patient();
        patient.setFirstName(patientRegistrationModal.getFirstName());
        patient.setLastName(patientRegistrationModal.getLastName());
        patient.setCreatedAt(Timestamp.from(Instant.now()));
        patient.setUpdatedAt(Timestamp.from(Instant.now()));

        user.setPatient(patient);
        return user;
    }

    /**
     * Creates User model from PharmacistRegistrationModel
     * @param pharmacistRegistrationModel - model containing pharmacist registration details
     * @return User
     */
    private User getUserModelFromPharmacistRegistrationModel(PharmacistRegistrationModel pharmacistRegistrationModel){
        User user=new User();
        user.setUsername(pharmacistRegistrationModel.getUsername());
        user.setPassword(pharmacistRegistrationModel.getPassword());
        user.hashPassword();
        user.setUserRole(pharmacistRegistrationModel.getUserRole());
        user.setCreatedAt(Timestamp.from(Instant.now()));
        user.setUpdatedAt(Timestamp.from(Instant.now()));
        user.setAdminVerificationStatus(AdminVerificationStatus.PENDING);

        Pharmacist pharmacist=new Pharmacist();
        pharmacist.setFirstName(pharmacistRegistrationModel.getFirstName());
        pharmacist.setLastName(pharmacistRegistrationModel.getLastName());
        pharmacist.setPharmacyName(pharmacistRegistrationModel.getPharmacyName());
        pharmacist.setContactNumber(pharmacistRegistrationModel.getContactNumber());
        pharmacist.setAddressLine1(pharmacistRegistrationModel.getAddressLine1());
        pharmacist.setAddressLine2(pharmacistRegistrationModel.getAddressLine2());
        pharmacist.setPostalCode(pharmacistRegistrationModel.getPostalCode());
        pharmacist.setLicenseNumber(pharmacistRegistrationModel.getLicenseNumber());
        pharmacist.setCreatedAt(Timestamp.from(Instant.now()));
        pharmacist.setUpdatedAt(Timestamp.from(Instant.now()));

        user.setPharmacist(pharmacist);
        return user;
    }

    /**
     * Initiate password reset for the user.
     * @param email The email address of the user.
     * @return True if the reset request was sent successfully, false otherwise.
     */
    private boolean addResetPasswordRequestToDatabaseAndSendEmail(String email) {
        boolean response;
        // Find user by email
        User user = this.userRepository.findUserByUsername(email);
        if (user == null) {
            response = false;
        }
        else {
            String resetToken = generateResetToken(email);
            user.setResetToken(resetToken);
            this.userRepository.save(user);
            this.sendResetEmail(email, resetToken);
            response = true;
        }
        return response;
    }

    /**
     * Initiates the process of resetting the user's password.
     * @param email      The email address of the user
     * @param resetToken The reset token sent to the user via email
     * @param newPassword The new password to set
     * @return A response indicating the outcome of the password reset attempt
     */
    public ResponseModel<String> resetPassword(String email, String resetToken, String newPassword) {
        ResponseModel<String> response = new ResponseModel<>();
        if (email == null || email.isEmpty() || resetToken == null || resetToken.isEmpty() || newPassword == null || newPassword.isEmpty()) {
            response.responseData = "Patient already exists.";
            response.message = "Failure";
        } else {
            boolean resetSuccessful = this.resetPasswordInDatabase(email, resetToken, newPassword);
            if (resetSuccessful) {
                response.responseData = "Password reset successfully.";
                response.message = "Success.";
                response.isSuccess = true;
            } else {
                response.responseData = "Invalid token or email.";
                response.message = "Failure.";
            }
        }
        return response;
    }

    /**
     * Initiates the process of sending a password reset request to the user's email.
     *
     * @param email The email address of the user
     * @return A response indicating the outcome of the password reset request
     */
    public ResponseModel<String> addForgotPasswordRequestAndSendEmail(String email) {

        ResponseModel<String> response = new ResponseModel<>();
        if (StringUtils.isBlank(email)) {
            response.responseData = "Email is required.";
            response.message = "Failure.";
        } else {
            boolean resetRequestSent = this.addResetPasswordRequestToDatabaseAndSendEmail(email);
            if (resetRequestSent) {
                response.responseData = "Password reset request sent successfully.";
                response.message = "Success.";
                response.isSuccess = true;
            } else {
                response.responseData = "User not found.";
                response.message = "Failure.";
            }
        }
        return response;
    }

    /**
     * Reset the user's password using the provided token.
     * @param email The email of the user.
     * @param token The reset token.
     * @return True if the password was reset successfully, false otherwise.
     */
    private boolean resetPasswordInDatabase(String email, String token, String newPassword) {
        boolean response = false;
        User user = this.userRepository.findUserByUsernameAndResetToken(email, token);
        if (user != null && !StringUtils.isBlank(token)) {
            user.setPassword(newPassword);
            user.hashPassword();
            user.setResetToken(null);
            this.userRepository.save(user);
            response = true;
        }
        return response;
    }

    /**
     * Generate a unique reset token including email and timestamp.
     * @param email The email of the user.
     * @return The generated reset token.
     */
    private String generateResetToken(String email) {
        return UUID.randomUUID().toString() + "_" + email + "_" + LocalDateTime.now().toString();
    }

    /**
     * Send reset email to the user containing the reset link.
     * @param email The email address of the user.
     * @param resetToken The reset token to include in the reset link.
     */
    private void sendResetEmail(String email, String resetToken) {
        // Construct the email body with the reset token URL
        String resetLink = ProjectConstants.backendRootURL + "/reset-password?token=" + resetToken + "&email=" + email;
        String body = "Please click the link below to reset your password:\n" + "\n" + "\n" + resetLink;
        // Send email using EmailService
        this.emailService.sendEmail(email, "Password Reset Request", body);
    }

    /**
     * Verify user email when they click reset link sent to their email
     * @param email The email address of the user.
     */
    public ResponseModel<String> verifyEmail(String email, String emailVerifytoken) {
        ResponseModel<String> response=new ResponseModel<String>();
        User user = this.userRepository.findUserByUsernameAndEmailVerifyToken(email,emailVerifytoken);
        if (user != null) {
            user.setIsEmailVerified(true);
            this.userRepository.save(user);
            response.responseData="Email verified!";
            response.message="Success.";
            response.isSuccess = true;
        } else {
            response.responseData="Verification failed, please try again!";
            response.message="Failure.";
        }
        return response;
    }
    //endregion
}
