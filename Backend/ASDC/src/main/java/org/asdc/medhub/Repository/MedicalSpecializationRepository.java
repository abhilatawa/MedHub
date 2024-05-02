package org.asdc.medhub.Repository;
import org.asdc.medhub.Utility.Model.DatabaseModels.MedicalSpecialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for database operations related to medical specializations table
 */
@Repository
public interface MedicalSpecializationRepository extends JpaRepository<MedicalSpecialization,Integer> {
    /**
     * Searches medical specializations table with searchString with 'like' clause
     * @param searchString - string to search
     * @return List<MedicalSpecialization> object
     */
    List<MedicalSpecialization> getMedicalSpecializationsBySpecializationContainingIgnoreCase(String searchString);

}
