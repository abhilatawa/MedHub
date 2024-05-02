package org.asdc.medhub.Service;
import org.asdc.medhub.Repository.UserRepository;
import org.asdc.medhub.Service.Interface.IAdminService;
import org.asdc.medhub.Service.Interface.ICommonService;
import org.asdc.medhub.Utility.Constant.ProjectConstants;
import org.asdc.medhub.Utility.Enums.AdminVerificationStatus;
import org.asdc.medhub.Utility.Enums.UserRole;
import org.asdc.medhub.Utility.Model.DatabaseModels.Doctor;
import org.asdc.medhub.Utility.Model.DatabaseModels.Pharmacist;
import org.asdc.medhub.Utility.Model.DatabaseModels.User;
import org.asdc.medhub.Utility.Model.ResponseModel;
import org.asdc.medhub.Utility.Model.ResponseModels.DoctorDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.PharmacistDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains supported methods for admin controller
 */
@Service
public class AdminService implements IAdminService {

    /**
     * Common service instance
     */
    private final ICommonService commonService;

    /**
     * User repository instance
     */
    private final UserRepository userRepository;

    /**
     * Email service instance
     */
    private final EmailService emailService;

    /**
     * Parameterized constructor
     * @param userRepository - userRepository bean
     * @param emailService - emailService bean
     */
    @Autowired
    public AdminService(UserRepository userRepository, EmailService emailService,ICommonService commonService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.commonService=commonService;
    }

    /**
     * Retrieves unverified doctors.
     * @return A ResponseModel containing the list of unverified doctors' information.
     */
    public ResponseModel<List<DoctorDetail>> getAdminUnverifiedDoctors() {
        ResponseModel<List<DoctorDetail>> response = new ResponseModel<>();
        try {
            List<User> users = this.userRepository.findUsersByUserRoleAndAdminVerificationStatus(UserRole.DOCTOR, AdminVerificationStatus.PENDING);
            List<DoctorDetail> doctorsInfo = new ArrayList<>();
            users.forEach(user -> doctorsInfo.add(this.convertDoctorToDoctorDetail(user.getDoctor())));
            response.setResponseData(doctorsInfo);
            response.setMessage("Found: " + doctorsInfo.size() + " unverified doctors.");
            response.setSuccess(true);
        } catch (Exception e) {
            response.setMessage("An error occurred while fetching unverified doctors: " + e.getMessage());
        }
        return response;
    }

    /**
     * Converts a Doctor object to a DoctorDetail object.
     *
     * @param doctor The Doctor object to be converted.
     * @return A DoctorDetail object containing the details of the doctor.
     */
    private DoctorDetail convertDoctorToDoctorDetail(Doctor doctor) {
        DoctorDetail doctorDetail = new DoctorDetail();
        doctorDetail.setId(doctor.getId());
        doctorDetail.setPostalCode(doctor.getPostalCode());
        doctorDetail.setUsername(doctor.getUser().getUsername());
        doctorDetail.setLicenseNumber(doctor.getLicenseNumber());
        return doctorDetail;
    }

    /**
     * Retrieves individual details of an unverified doctor by email.
     *
     * @param email The email of the user whose doctor details need to be retrieved.
     * @return A Map containing the doctor's details if found, or an error message if no doctor is found.
     */
    public ResponseModel<DoctorDetail> getAdminUnverifiedDoctorIndividual(String email) {
        ResponseModel<DoctorDetail> response = new ResponseModel<>();
        try {
            Doctor doctor = this.userRepository.findUserByUsername(email).getDoctor();
            if (doctor != null) {
                DoctorDetail doctorDetail = this.convertDoctorToDoctorDetailForIndividual(doctor);
                response.setResponseData(doctorDetail);
                response.setMessage("Doctor details retrieved successfully");
                response.setSuccess(true);
            } else {
                response.setMessage("No doctor found for the provided email");
            }
        } catch (Exception e) {
            response.setMessage("An error occurred while fetching doctor details: " + e.getMessage());
        }
        return response;
    }

    /**
     * Converts a Doctor object to a DoctorDetail object for an individual doctor.
     *
     * @param doctor The Doctor object to be converted.
     * @return A DoctorDetail object containing the details of the individual doctor.
     */
    private DoctorDetail convertDoctorToDoctorDetailForIndividual(Doctor doctor) {
        DoctorDetail doctorDetail = new DoctorDetail();
        doctorDetail.setId(doctor.getId());
        doctorDetail.setFirstName(doctor.getFirstName());
        doctorDetail.setLastName(doctor.getLastName());
        doctorDetail.setContactNumber(doctor.getContactNumber());
        doctorDetail.setAddressLine1(doctor.getAddressLine1());
        doctorDetail.setAddressLine2(doctor.getAddressLine2());
        doctorDetail.setPostalCode(doctor.getPostalCode());
        doctorDetail.setLicenseNumber(doctor.getLicenseNumber());
        return doctorDetail;
    }

    /**
     * Retrieves the details of all unverified pharmacists.
     *
     * @return A ResponseModel containing the list of unverified pharmacist details if found,
     *         otherwise, an error message indicating that no unverified pharmacists were found.
     */
    public ResponseModel<List<PharmacistDetail>> getAdminUnverifiedPharmacists() {
        ResponseModel<List<PharmacistDetail>> response = new ResponseModel<>();
        try {
            List<User> users = this.userRepository.findUsersByUserRoleAndAdminVerificationStatus(UserRole.PHARMACIST, AdminVerificationStatus.PENDING);
            List<PharmacistDetail> pharmacistsInfo = new ArrayList<>();
            users.forEach(user -> pharmacistsInfo.add(this.convertPharmacistToPharmacistDetail(user.getPharmacist())));
            response.setResponseData(pharmacistsInfo);
            response.setMessage("Found: " + pharmacistsInfo.size() + " unverified doctors.");
            response.setSuccess(true);
        } catch (Exception e) {
            response.setMessage("An error occurred while fetching unverified pharmacists: " + e.getMessage());
        }
        return response;
    }

    /**
     * Converts a Pharmacist object to a PharmacistDetail object.
     *
     * @param pharmacist The Pharmacist object to be converted.
     * @return A PharmacistDetail object containing the details of the pharmacist.
     */
    private PharmacistDetail convertPharmacistToPharmacistDetail(Pharmacist pharmacist) {
        PharmacistDetail pharmacistDetail = new PharmacistDetail();
        pharmacistDetail.setId(pharmacist.getId());
        pharmacistDetail.setPostalCode(pharmacist.getPostalCode());
        pharmacistDetail.setLicenseNumber(pharmacist.getLicenseNumber());
        pharmacistDetail.setUsername(pharmacist.getUser().getUsername());
        return pharmacistDetail;
    }

    /**
     * Retrieves the details of an individual pharmacist by their email.
     *
     * @param email The email address of the pharmacist.
     * @return A ResponseModel containing the details of the pharmacist if found,
     *         otherwise, an error message indicating that no pharmacist was found for the provided email.
     */
    public ResponseModel<PharmacistDetail> getAdminUnverifiedPharmacistIndividual(String email) {
        ResponseModel<PharmacistDetail> response = new ResponseModel<>();
        try {
            Pharmacist pharmacist = this.userRepository.findUserByUsername(email).getPharmacist();
            PharmacistDetail pharmacistDetail = this.commonService.convertPharmacistToPharmacistDetail(pharmacist);
            response.setResponseData(pharmacistDetail);
            response.setMessage("Pharmacist details retrieved successfully");
            response.setSuccess(true);
        } catch (Exception e) {
            response.setMessage("An error occurred while fetching pharmacist details: " + e.getMessage());
        }
        return response;
    }

    /**
     * Approves a user by admin.
     * @param email The email of the user to be approved.
     * @return A ResponseModel containing the approval status message.
     */
    public ResponseModel<String> approveUserByAdmin(String email) {
        ResponseModel<String> response = new ResponseModel<String>();
        try {
            User user = this.userRepository.findUserByUsername(email);
            if (null != user) {
                user.setAdminVerificationStatus(AdminVerificationStatus.VERIFIED);
                this.userRepository.save(user);
                response.message = "User is now admin verified!";
                response.isSuccess = true;
                sendApprovalMail(email);
            } else {
                response.message = "User not found, please try again!";
            }
        } catch (Exception e) {
            response.message = "An error occurred while approving the user: " + e.getMessage();
            response.isSuccess = false;
        }
        return response;
    }

    /**
     * Rejects a user by admin.
     * @param email The email of the user to be rejected.
     * @return A ResponseModel containing the rejection status message.
     */
    public ResponseModel<String> rejectUserByAdmin(String email) {
        ResponseModel<String> response = new ResponseModel<String>();
        try {
            User user = this.userRepository.findUserByUsername(email);
            if (null != user) {
                user.setAdminVerificationStatus(AdminVerificationStatus.REJECTED);
                this.userRepository.save(user);
                response.message = "User is rejected by the admin.";
                response.isSuccess = true;
                this.sendRejectMail(email);
            } else {
                response.message = "User not found, please try again!";
            }
        } catch (Exception e) {
            response.message = "An error occurred while rejecting the user: " + e.getMessage();
            response.isSuccess = false;
        }
        return response;
    }

    /**
     * Sends an approval email to the specified email address.
     * @param email The recipient's email address.
     */
    private void sendApprovalMail(String email) {
        String body = "Congratulations! Your account at MedHub has been approved! Please click the link below to login\n" + "\n" + "\n" + ProjectConstants.backendRootURL + "/login";
        this.emailService.sendEmail(email, "Account approved. Welcome to MedHub!", body);
    }

    /**
     * Sends a rejection email to the specified email address.
     * @param email The recipient's email address.
     */
    private void sendRejectMail(String email) {
        this.emailService.sendEmail(email, "Your account at MedHub could not be approved.", "Oh no! Your account at MedHub could not be approved. Please email help@medhub.com from your registered email id for more info.");
    }
}
