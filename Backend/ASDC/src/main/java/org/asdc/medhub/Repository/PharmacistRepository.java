package org.asdc.medhub.Repository;

import org.asdc.medhub.Utility.Enums.AdminVerificationStatus;
import org.asdc.medhub.Utility.Model.DatabaseModels.Pharmacist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for db operations related to pharmacist
 */
@Repository
public interface PharmacistRepository extends JpaRepository<Pharmacist,Integer> {

    /**
     * Returns list of pharmacists which are verified and contains pharmacy name as parameter
     * @param pharmacyName String to search in pharmacy name
     * @return List of pharmacist
     */
    List<Pharmacist> getPharmacistsByPharmacyNameContainingAndUser_AdminVerificationStatus(String pharmacyName, AdminVerificationStatus status);

    /**
     * Gets pharmacist by pharmacist id
     * @param pharmacistId id of pharmacist
     * @return Pharmacist object
     */
    Pharmacist getPharmacistById(int pharmacistId);
}
