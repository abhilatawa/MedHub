
package org.asdc.medhub.Service;

import org.asdc.medhub.Configuration.CustomConfigurations;
import org.asdc.medhub.Repository.AppointmentRepository;
import org.asdc.medhub.Repository.PharmacistRepository;
import org.asdc.medhub.Repository.SpecializationOfDoctorRepository;
import org.asdc.medhub.Repository.UserRepository;
import org.asdc.medhub.Service.Interface.ICommonService;
import org.asdc.medhub.Service.Interface.IDoctorService;

import org.asdc.medhub.Utility.Enums.AdminVerificationStatus;
import org.asdc.medhub.Utility.Enums.AppointmentStatus;
import org.asdc.medhub.Utility.Model.DatabaseModels.Appointment;
import org.asdc.medhub.Utility.Model.DatabaseModels.Doctor;
import org.asdc.medhub.Utility.Model.DatabaseModels.Pharmacist;
import org.asdc.medhub.Utility.Model.DatabaseModels.User;
import org.asdc.medhub.Utility.Model.ResponseModel;
import org.asdc.medhub.Utility.Model.ResponseModels.AppointmentDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.DoctorDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.PharmacistDetail;
import org.asdc.medhub.Utility.UtilityMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for managing doctor-related operations
 */
@Service
@Transactional
public class DoctorService implements IDoctorService {

    /**
     * Pharmacist repository instance
     */
    private final PharmacistRepository pharmacistRepository;

    /**
     * Appointment repository instance
     */
    private final AppointmentRepository appointmentRepository;

    /**
     * Common service instance
     */
    private final ICommonService commonService;

    /**
     * Custom configurations specified in application.properties
     */
    private final CustomConfigurations customConfigurations;

    /**
     * UserRepository  instance
     */
    private final UserRepository userRepository;

    /**
     * specializationOfDoctorRepository SpecializationOfDoctorRepository instance
     */
    private final SpecializationOfDoctorRepository specializationOfDoctorRepository;

    /**
     * Constructor injection for DoctorService
     *
     * @param userRepository                  UserRepository instance
     * @param specializationOfDoctorRepository SpecializationOfDoctorRepository instance
     */
    @Autowired
    public DoctorService(UserRepository userRepository,
                         SpecializationOfDoctorRepository specializationOfDoctorRepository,
                         CustomConfigurations customConfigurations,
                         ICommonService commonService,
                         AppointmentRepository appointmentRepository,
                         PharmacistRepository pharmacistRepository) {
        this.userRepository =userRepository;
        this.specializationOfDoctorRepository= specializationOfDoctorRepository;
        this.customConfigurations=customConfigurations;
        this.commonService=commonService;
        this.appointmentRepository=appointmentRepository;
        this.pharmacistRepository=pharmacistRepository;
    }

    /**
     * Retrieves individual details of the doctor using email.
     * @param DocEmail The id of the doctor whose profile details need to be retrieved.
     * @return A ResponseModel containing the doctor's details if found, or an error message if no doctor is found.
     */
    public ResponseModel<DoctorDetail> getDoctorProfile(String DocEmail) {
        ResponseModel<DoctorDetail> response = new ResponseModel<>();
        try {
            Doctor doctor = this.userRepository.findDoctorByUsername(DocEmail).getDoctor();
            if (doctor != null ) {

                response.setMessage("Doctor details retrieved successfully");
                response.setSuccess(true);
                DoctorDetail doctorDetail = this.commonService.convertDoctorToDoctorDetail(doctor);
                response.setResponseData(doctorDetail);
            } else {
                response.setMessage("No doctor found for the provided ID");
            }
        } catch (Exception e) {
            response.setMessage("An error occurred while fetching doctor details: " + e.getMessage());
        }
        return response;
    }

    /**
     * Uploads the profile picture for a doctor.
     *
     * @param profilePicture The profile picture file to upload.
     * @param doctorId The ID of the doctor for whom the profile picture is being uploaded.
     * @return A ResponseModel<String> indicating the result of the profile picture upload operation.
     */
    public ResponseModel<String> uploadProfilePicture(MultipartFile profilePicture,int doctorId){
        ResponseModel<String> responseModel=new ResponseModel<>();
        if(!profilePicture.isEmpty()){
            User user=this.userRepository.findUserByDoctorId(doctorId);
            user.getDoctor().setProfilePictureFileName(this.storeProfilePicture(profilePicture));
            this.userRepository.save(user);
            responseModel.isSuccess=true;
        }else{
            responseModel.message="Unable to save profile picture.";
        }
        return responseModel;
    }

    /**
     * Edits the profile of a doctor.
     *
     * @param doctorDetail The DoctorDetail object containing updated doctor profile information.
     * @return A ResponseModel indicating the success or failure of the operation.
     */
    public ResponseModel<DoctorDetail> editDoctorProfile( DoctorDetail doctorDetail) {
        ResponseModel<DoctorDetail> response = new ResponseModel<>();
        try {
            User user = this.userRepository.findUserByDoctorId(doctorDetail.getId());
            if (user != null) {
                //This is not permanent code, this is temporary patch
                this.specializationOfDoctorRepository.deleteAllByDoctorId(doctorDetail.getId());
                this.userRepository.flush();
                user.setDoctor(this.commonService.updateDoctorFromDoctorDetailModel(user.getDoctor(), doctorDetail));
                this.specializationOfDoctorRepository.saveAll(user.getDoctor().getSpecializationsOfDoctor());
                this.userRepository.save(user);
                response.setMessage("Doctor details updated successfully");
                response.setResponseData(doctorDetail);
                response.setSuccess(true);
            } else {
                response.setMessage("No doctor found for the provided email");
            }
        } catch (Exception e) {
            response.setMessage("An error occurred while updating doctor details: " + e.getMessage());
        }
        return response;
    }

    /**
     * Saves user profile picture in local file storage
     * @param image MultipartFile
     * @return String of image path
     */
    private String storeProfilePicture(MultipartFile image) {
        Path directory = Paths.get(this.customConfigurations.getProfilePictureFolderPath());
        String fileName=null;
        try {
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
            fileName = UUID.randomUUID() + image.getOriginalFilename();
            Path filePath = Path.of(this.customConfigurations.getProfilePictureFolderPath(), fileName);
            Files.write(filePath, image.getBytes());
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return fileName;
    }

    /**
     * Sets email notification preference for the doctor
     * @param doctorEmail email of doctor
     * @param emailPreference flag of email notification
     * @return ResponseModel
     */
    public ResponseModel<String> setEmailNotificationPreferenceForDoctor(String doctorEmail,boolean emailPreference){
        ResponseModel<String> responseModel=new ResponseModel();
        try{
            User user=this.userRepository.findUserByUsername(doctorEmail);
            if(null!=user){
                user.setReceiveEmailNotification(emailPreference);
                this.userRepository.saveAndFlush(user);
                responseModel.isSuccess=true;
                responseModel.message="Notification preference set to : "+emailPreference +" successfully.";
            }

        }catch (Exception exception){
            responseModel.setMessage(exception.getMessage());
        }
        return responseModel;
    }

    /**
     * Changes existing user password with new password
     * @param doctorEmail email of the user
     * @param newPassword new password entered by user
     * @return ResponseModel
     */
    public ResponseModel<String> changePassword(String doctorEmail,String newPassword){
        ResponseModel<String> responseModel=new ResponseModel<>();
        try{
            String hashedNewPassword=UtilityMethods.getSha256HashString(newPassword);
            User user=this.userRepository.findUserByUsername(doctorEmail);
            if(null!=user){
                user.setPassword(hashedNewPassword);
                this.userRepository.saveAndFlush(user);
                responseModel.isSuccess=true;
                responseModel.message="Password changed successfully.";
            }else{
                responseModel.message="User not found.";
            }
        }catch (Exception exception){
            responseModel.setMessage(exception.getMessage());
        }
        return responseModel;
    }

    /**
     * Retrieves appointment list according to given status and doctor id
     * @param activeAppointments if we want only active appointment or not
     * @param doctorId doctor id of whose appointment we need to fetch
     * @return List of appointment detail model
     */
    public ResponseModel<List<AppointmentDetail>> getAppointments(boolean activeAppointments, int doctorId){
        ResponseModel<List<AppointmentDetail>> responseModel=new ResponseModel<>();
        try{
            List<Appointment> appointmentList=this.appointmentRepository
                    .getAppointmentsByDoctorIdAndStatusIsIn(
                            doctorId,
                            activeAppointments?
                                    List.of(AppointmentStatus.BOOKED)
                                    :List.of(AppointmentStatus.CANCELLED,AppointmentStatus.COMPLETED));
            responseModel.responseData= appointmentList.stream()
                    .map(this.commonService::getAppointmentDetailModelFromAppointment)
                    .collect(Collectors.toList());
            responseModel.isSuccess=true;
        }
        catch (Exception exception){
            responseModel.message=exception.getMessage();
        }
        return responseModel;
    }

    /**
     * Searches verified pharmacist based on pharmacy name
     * @param pharmacistNameSearchString search string for name of pharmacy
     * @return List of PharmacistDetail
     */
    public ResponseModel<List<PharmacistDetail>> getFilteredAndVerifiedPharmacistList(String pharmacistNameSearchString){
        ResponseModel<List<PharmacistDetail>> responseModel=new ResponseModel<>();
        try{
            List<Pharmacist> pharmacistList=
                    this.pharmacistRepository.
                            getPharmacistsByPharmacyNameContainingAndUser_AdminVerificationStatus(pharmacistNameSearchString, AdminVerificationStatus.VERIFIED);
            responseModel.responseData = pharmacistList.stream()
                    .map(this.commonService::convertPharmacistToPharmacistDetail)
                    .collect(Collectors.toList());
            responseModel.message="Found: "+pharmacistList.size()+" pharmacists.";
            responseModel.isSuccess=true;
        }
        catch (Exception exception){
            responseModel.message=exception.getMessage();
        }
        return responseModel;
    }

    /**
     * Updates appointment in database
     * @param appointmentDetail Appointment detail model
     * @return Response model of appointment detail model
     */
    @Transactional
    public ResponseModel<AppointmentDetail> updateAppointmentDetail(AppointmentDetail appointmentDetail){
        ResponseModel<AppointmentDetail> responseModel=new ResponseModel<>();
        try{
            Appointment appointmentFromDb=this.appointmentRepository.getAppointmentById(appointmentDetail.getId());
            appointmentFromDb.setPrescription(appointmentDetail.getPrescription());
            //here default value of int is 0 that's why comparing with it
            if(appointmentDetail.getPharmacistId()>0){
                var pharmacist=this.pharmacistRepository.getPharmacistById(appointmentDetail.getPharmacistId());
                appointmentFromDb.setPharmacist(pharmacist);
            }
            appointmentFromDb.setUpdatedAt(Timestamp.from(Instant.now()));
            appointmentFromDb.setStatus(AppointmentStatus.COMPLETED);
            var result=this.appointmentRepository.saveAndFlush(appointmentFromDb);
            responseModel.responseData=this.commonService.getAppointmentDetailModelFromAppointment(result);
            responseModel.message="Appointment updated successfully.";
            responseModel.isSuccess=true;
        }
        catch (Exception exception){
            responseModel.message=exception.getMessage();
        }
        return responseModel;
    }

    
    /**
     *
     * @param doctorId doctor id of whose feedback details needs to be fetched
     * @return the Map of the Doctors feedback details with average rating of the
     * doctor and individual rating for the doctors along with the feedback message
     */
    public ResponseModel<Map<String, Object>> getDoctorFeedbackDetails(int doctorId) {
        ResponseModel<Map<String, Object>> responseModel = new ResponseModel<>();
        try {
            List<Appointment> appointments = this.appointmentRepository.getAppointmentsByDoctorId(doctorId);

            double totalRating = appointments.stream()
                    .mapToDouble(Appointment::getRating)
                    .sum();
            double averageRating = totalRating / appointments.size();

            List<Map<String, Object>> feedbackDetails = appointments.stream()
                    .map(appointment -> {
                        Map<String, Object> feedback = new HashMap<>();
                        feedback.put("rating", appointment.getRating());
                        feedback.put("feedbackMessage", appointment.getFeedbackMessage());
                        return feedback;
                    })
                    .collect(Collectors.toList());

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("averageRating", averageRating);
            responseData.put("feedbackDetails", feedbackDetails);
            responseModel.setResponseData(responseData);
            responseModel.setMessage("Doctor feedback details retrieved successfully.");
            responseModel.setSuccess(true);
        } catch (Exception exception) {
            responseModel.setMessage(exception.getMessage());
            responseModel.setSuccess(false);
        }
        return responseModel;
    }
}


