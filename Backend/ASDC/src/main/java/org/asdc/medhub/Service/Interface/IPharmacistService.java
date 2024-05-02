package org.asdc.medhub.Service.Interface;

import org.asdc.medhub.Utility.Model.ResponseModel;
import org.asdc.medhub.Utility.Model.ResponseModels.AppointmentDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.PharmacistDetail;

import java.util.List;

/**
 * Contains supported methods for pharmacist controller
 */
public interface IPharmacistService {

    /**
     * Retrieves profile data for pharmacist based on pharmacist_id
     * @param pharmacistId id of pharmacist from tbl_pharmacist
     * @return ResponseModel with pharmacist details
     */
    ResponseModel<PharmacistDetail> getPharmacistProfileFromId(int pharmacistId);

    /**
     * Fetches appointments based on pharmacist id and first name search string
     * @param patientNameSearchString patient firstname search string
     * @param pharmacistId id of pharmacist
     * @return List of appointment detail
     */
    ResponseModel<List<AppointmentDetail>> getAppointmentsFilteredByPatientName(String patientNameSearchString, int pharmacistId);

    /**
     * Changes existing user password with new password
     * @param userId id of the user
     * @param newPassword new password entered by user
     * @return ResponseModel
     */
    ResponseModel<String> changePassword(int userId,String newPassword);

    /**
     * Updates profile data for pharmacist
     * @return Response dasta with Pharmacist Detail model
     */
    ResponseModel<PharmacistDetail> updatePharmacistProfile(PharmacistDetail pharmacistDetail);
}
