package org.asdc.medhub.Repository;

import org.asdc.medhub.Utility.Model.DatabaseModels.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for database operation related to patient
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient,Integer> {

}
