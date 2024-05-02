package org.asdc.medhub.Utility.Model.ResponseModels;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.asdc.medhub.Utility.Enums.UserRole;

/**
 * Model that carries details of User for response
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetail {
    /**
     * Username of User (here email)
     */
    @Email
    private String username;
    /**
     * Firstname of User
     */
    @NotBlank
    private String firstName;

    /**
     * Lastname of User
     */
    @NotBlank
    private String lastName;
    /**
     * userRole of User
     */
    @NotBlank
    private UserRole userRole;
}
