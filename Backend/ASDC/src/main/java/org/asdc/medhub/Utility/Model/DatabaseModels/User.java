package org.asdc.medhub.Utility.Model.DatabaseModels;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.asdc.medhub.Utility.Constant.DatabaseConstants;
import org.asdc.medhub.Utility.Enums.AdminVerificationStatus;
import org.asdc.medhub.Utility.Enums.UserRole;
import org.asdc.medhub.Utility.UtilityMethods;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

/**
 * represents table user of database
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = DatabaseConstants.UserTable.tableName)
@Component
public class User implements UserDetails {

    /**
     * primary key | auto increment
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Username of user
     */
    @NotBlank
    @Email
    @Column(name = DatabaseConstants.UserTable.Columns.userName)
    protected String username;

    /**
     * Hashed password of user | SHA256
     */
    @NotBlank
    @Column(name = DatabaseConstants.UserTable.Columns.password)
    protected String password;

    /**
     * Role of user
     */
    @Enumerated(EnumType.STRING)
    @Column(name = DatabaseConstants.UserTable.Columns.userRole)
    protected UserRole userRole;

    /**
     * Timestamp when record was created
     */
    @Column(name = DatabaseConstants.UserTable.Columns.createdAt)
    private Timestamp createdAt;

    /**
     * Timestamp when record was updated
     */
    @Column(name = DatabaseConstants.UserTable.Columns.updatedAt)
    private Timestamp updatedAt;

    /**
     * Boolean value for email verification at sign up
     */
    @Column(name = DatabaseConstants.UserTable.Columns.isEmailVerified)
    private Boolean isEmailVerified;

    /**
     * Admin verification status of the user
     */
    @Enumerated(EnumType.STRING)
    @Column(name = DatabaseConstants.UserTable.Columns.adminVerificationStatus)
    protected AdminVerificationStatus adminVerificationStatus;

    /**
     * String value for email verification token at sign up
     */
    @Column(name = DatabaseConstants.UserTable.Columns.emailVerifyToken)
    private String emailVerifyToken;

    /**
     Getter and setter methods for the reset token attribute of the User entity.
     This attribute represents the token used for resetting the user's password.
     It is mapped to the 'resetToken' column in the database table.
     */
    @Column(name = DatabaseConstants.UserTable.Columns.resetToken)
    private String resetToken;

    /**
     * Linked doctor record of user
     */
    @OneToOne(cascade =CascadeType.ALL)
    @JoinColumn(name = DatabaseConstants.UserTable.Columns.doctorId)
    private Doctor doctor;

    /**
     * Linked patient record of user
     */
    @OneToOne(cascade =CascadeType.ALL)
    @JoinColumn(name = DatabaseConstants.UserTable.Columns.patientId)
    private Patient patient;

    /**
     * Linked pharmacist record of pharmacist
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name=DatabaseConstants.UserTable.Columns.pharmacistId)
    private Pharmacist pharmacist;

    /**
     * Flag to set notification preferences
     */
    @Column(name = DatabaseConstants.UserTable.Columns.receiveEmailNotification)
    private boolean receiveEmailNotification;

    /**
     * Gets collection of authorities
     * @return Collection of granted authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.userRole.name()));
    }

    /**
     * Gets username from this object
     * @return String - username
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * Gets status of account expiration
     * @return boolean
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Gets status of account lock
     * @return boolean
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Gets status of credential expiration
     * @return boolean
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Gets status of account (enable or disable)
     * @return boolean
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Converts password string of this object and replaces it
     */
    public void hashPassword(){
        this.password= UtilityMethods.getSha256HashString(this.password);
    }
}
