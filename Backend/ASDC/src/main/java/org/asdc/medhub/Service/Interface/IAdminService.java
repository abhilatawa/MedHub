package org.asdc.medhub.Service.Interface;

import org.asdc.medhub.Utility.Model.ResponseModel;
import org.asdc.medhub.Utility.Model.ResponseModels.DoctorDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.PharmacistDetail;

import java.util.List;
/**
 * Service interface for handling admin-related functionalities.
 */
public interface IAdminService {

    /**
     * Retrieves unverified doctors.
     * @return A ResponseModel containing the list of unverified doctors' information.
     */
    ResponseModel<List<DoctorDetail>> getAdminUnverifiedDoctors();

    /**
     * Retrieves unverified pharmacists.
     * @return A ResponseModel containing the list of unverified pharmacists' information.
     */
    ResponseModel<List<PharmacistDetail>> getAdminUnverifiedPharmacists();

    /**
     * Approves a user by admin.
     * @param email The email of the user to be approved.
     * @return A ResponseModel containing the approval status message.
     */
    ResponseModel<String> approveUserByAdmin(String email);

    /**
     * Rejects a user by admin.
     * @param email The email of the user to be rejected.
     * @return A ResponseModel containing the rejection status message.
     */
    ResponseModel<String> rejectUserByAdmin(String email);

    /**
     * Retrieves details of an individual unverified pharmacist based on the provided email.
     *
     * @param email The email address of the pharmacist whose details are to be retrieved.
     * @return A ResponseModel containing the details of the requested unverified pharmacist.
     */
    ResponseModel<PharmacistDetail> getAdminUnverifiedPharmacistIndividual(String email);

    /**
     * Retrieves details of an individual unverified doctor based on the provided email.
     *
     * @param email The email address of the doctor whose details are to be retrieved.
     * @return A ResponseModel containing the details of the requested unverified doctor.
     */
    ResponseModel<DoctorDetail> getAdminUnverifiedDoctorIndividual(String email);
}
