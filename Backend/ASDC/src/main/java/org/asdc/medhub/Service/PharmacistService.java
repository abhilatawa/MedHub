package org.asdc.medhub.Service;

import org.asdc.medhub.Repository.AppointmentRepository;
import org.asdc.medhub.Repository.PharmacistRepository;
import org.asdc.medhub.Repository.UserRepository;
import org.asdc.medhub.Service.Interface.ICommonService;
import org.asdc.medhub.Service.Interface.IPharmacistService;
import org.asdc.medhub.Utility.Model.DatabaseModels.Pharmacist;
import org.asdc.medhub.Utility.Model.DatabaseModels.User;
import org.asdc.medhub.Utility.Model.ResponseModel;
import org.asdc.medhub.Utility.Model.ResponseModels.AppointmentDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.PharmacistDetail;
import org.asdc.medhub.Utility.UtilityMethods;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contains supported methods for pharmacist controller
 */
@Service
public class PharmacistService implements IPharmacistService {

    /**
     * Pharmacist repository instance
     */
    private final PharmacistRepository pharmacistRepository;

    /**
     * Common service instance
     */
    private final ICommonService commonService;

    /**
     * Appointment repository instance
     */
    private final AppointmentRepository appointmentRepository;

    /**
     * Repository instance of user
     */
    private final UserRepository userRepository;

    /**
     * Parameterized constructor with injected beans
     * @param pharmacistRepository pharmacist repository bean
     */
    public PharmacistService(
            PharmacistRepository pharmacistRepository,
            ICommonService commonService,
            AppointmentRepository appointmentRepository,
            UserRepository userRepository){
        this.pharmacistRepository=pharmacistRepository;
        this.commonService=commonService;
        this.appointmentRepository=appointmentRepository;
        this.userRepository=userRepository;
    }

    /**
     * Retrieves profile data for pharmacist based on pharmacist_id
     * @param pharmacistId id of pharmacist from tbl_pharmacist
     * @return ResponseModel with pharmacist details
     */
    public ResponseModel<PharmacistDetail> getPharmacistProfileFromId(int pharmacistId){
        ResponseModel<PharmacistDetail> responseModel=new ResponseModel<>();

        try{
            var pharmacistFromDb=this.pharmacistRepository.getPharmacistById(pharmacistId);
            responseModel.responseData=this.commonService.convertPharmacistToPharmacistDetail(pharmacistFromDb);
            responseModel.message="Pharmacist profile retrieved successfully.";
            responseModel.isSuccess=true;
        }
        catch(Exception exception){
            responseModel.message=exception.getMessage();
        }
        return responseModel;
    }

    /**
     * Updates profile data for pharmacist
     * @return Response data with Pharmacist Detail model
     */
    public ResponseModel<PharmacistDetail> updatePharmacistProfile(PharmacistDetail pharmacistDetail){
        ResponseModel<PharmacistDetail> responseModel=new ResponseModel<>();

        try{
            var pharmacistFromDb=this.pharmacistRepository.getPharmacistById(pharmacistDetail.getId());
            this.setUpdatedPharmacistProfileFields(pharmacistFromDb,pharmacistDetail);
            var pharmacistUpdated=this.pharmacistRepository.save(pharmacistFromDb);
            responseModel.responseData=this.commonService.convertPharmacistToPharmacistDetail(pharmacistUpdated);
            responseModel.message="Pharmacist profile updated successfully.";
            responseModel.isSuccess=true;
        }
        catch(Exception exception){
            responseModel.message=exception.getMessage();
        }
        return responseModel;
    }

    private void setUpdatedPharmacistProfileFields(Pharmacist pharmacistFromDb,PharmacistDetail pharmacistDetail){
        pharmacistFromDb.setFirstName(pharmacistDetail.getFirstName());
        pharmacistFromDb.setLastName(pharmacistDetail.getLastName());
        pharmacistFromDb.setPharmacyName(pharmacistDetail.getPharmacyName());
        pharmacistFromDb.setContactNumber(pharmacistDetail.getContactNumber());
        pharmacistFromDb.setAddressLine1(pharmacistDetail.getAddressLine1());
        pharmacistFromDb.setAddressLine2(pharmacistDetail.getAddressLine2());
        pharmacistFromDb.setPostalCode(pharmacistDetail.getPostalCode());
        pharmacistFromDb.setLicenseNumber(pharmacistDetail.getLicenseNumber());
    }

    /**
     * Fetches appointments based on pharmacist id and first name search string
     * @param patientNameSearchString patient firstname search string
     * @param pharmacistId id of pharmacist
     * @return List of appointment detail
     */
        public ResponseModel<List<AppointmentDetail>> getAppointmentsFilteredByPatientName(String patientNameSearchString,int pharmacistId){
        ResponseModel<List<AppointmentDetail>> responseModel=new ResponseModel<>();

        try{
            var appointmentList=this.appointmentRepository
                    .getAppointmentsByPatient_FirstNameContainingAndPharmacist_Id(patientNameSearchString,pharmacistId);
            responseModel.responseData=appointmentList.stream()
                    .map(this.commonService::getAppointmentDetailModelFromAppointment)
                    .collect(Collectors.toList());
            responseModel.message="Found: "+appointmentList.size()+" appointments.";
            responseModel.isSuccess=true;
        }
        catch (Exception exception){
            responseModel.message=exception.getMessage();
        }
        return responseModel;
    }

    /**
     * Changes existing user password with new password
     * @param userId id of the user
     * @param newPassword new password entered by user
     * @return ResponseModel
     */
    public ResponseModel<String> changePassword(int userId,String newPassword){
        ResponseModel<String> responseModel=new ResponseModel<>();
        try{
            String hashedNewPassword= UtilityMethods.getSha256HashString(newPassword);
            User user=this.userRepository.findUserById(userId);
            user.setPassword(hashedNewPassword);
            this.userRepository.saveAndFlush(user);
            responseModel.isSuccess=true;
            responseModel.message="Password changed successfully.";
        }catch (Exception exception){
            responseModel.setMessage(exception.getMessage());
        }
        return responseModel;
    }
}
