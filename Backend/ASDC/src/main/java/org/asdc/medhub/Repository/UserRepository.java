package org.asdc.medhub.Repository;

import org.asdc.medhub.Utility.Enums.AdminVerificationStatus;
import org.asdc.medhub.Utility.Enums.UserRole;
import org.asdc.medhub.Utility.Model.DatabaseModels.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository for database operation related to user
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    /**
     * Checks if user with given parameter exists or not
     * @param username - username of user
     * @param userRole - role of user
     * @return boolean
     */
    boolean existsUserByUsernameAndUserRole(String username, UserRole userRole);

    /**
     * Fetches user by user id
     * @param userId user id
     * @return User model
     */
    User findUserById(int userId);

    /**
     * returns the doctor id
     * @param doctorId id of the doctor
     * @return Doctor id
     */
    User findUserByDoctorId(int doctorId);

    /**
     * Returns user by given username
     * @param username - username of user
     * @return User
     */
    User findUserByUsername(String username);

    /**
     * Returns user by given username and token
     * @param username- username of user
     * @return User
     */
    User findDoctorByUsername(String username);

    /**
     * Searches user from tbl_user based on username and reset token
     * @param username username of user
     * @param resetToken reset token stored for user
     * @return User model
     */
    User findUserByUsernameAndResetToken(String username, String resetToken);

    /**
     * Checks if user with given parameter exists or not
     * @param userName - username of user
     * @param password - password of user
     * @param userRole - role of user
     * @return boolean
     */
    boolean existsUserByUsernameAndPasswordAndUserRole(String userName, String password, UserRole userRole);

    /**
     * Finds a user by username and email verification token.
     *
     *@param userName username (emailID) of the user.
     *@param emailVerifyToken Email verification token.
     * @return The user if found with the given username and token; otherwise, null.
     */
    User findUserByUsernameAndEmailVerifyToken(String userName, String emailVerifyToken);

    /**
     * Finds all users by user role and admin verification
     * @param role - role of user
     * @param adminVerificationStatus admin verification status of user
     * @return List of users
     */
    List<User> findUsersByUserRoleAndAdminVerificationStatus(UserRole role, AdminVerificationStatus adminVerificationStatus);
}
