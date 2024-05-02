package org.asdc.medhub.Repository;
import org.asdc.medhub.Utility.Constant.DatabaseConstants;
import org.asdc.medhub.Utility.Model.DatabaseModels.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository for db operations related to doctor
 */
@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Integer> {

    /**
     * Gets doctors who have specialization given in parameter list
     * @param specializationList list of specialization to filter
     * @return List of Doctor model
     */
    @Query(DatabaseConstants.Queries.getVerifiedDoctorsWithSpecializationsQuery)
    List<Doctor> getVerifiedDoctorsBySpecializationAvailableIn(@Param("specializationList") List<String> specializationList);

    /**
     * Gets all verified doctors
     * @return List of Doctors
     */
    @Query(DatabaseConstants.Queries.getAllVerifiedDoctors)
    List<Doctor> getAllVerifiedDoctors();
}
