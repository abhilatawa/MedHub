package org.asdc.medhub.Utility.Model.RequestModels;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.asdc.medhub.Utility.Model.DatabaseModels.User;
import java.util.List;

/**
 * Model used for registration request of doctor
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorRegistrationModel extends User{

    /**
     * First name of doctor
     */
    @NotBlank
    private String firstName;

    /**
     * Last name of doctor
     */
    @NotBlank
    private String lastName;

    /**
     * Contact number of doctor
     */
    @NotBlank
    private String contactNumber;

    /**
     * Address line 1 of doctor
     */
    private String addressLine1;

    /**
     * Address line 2 of doctor
     */
    private String addressLine2;

    /**
     * Postal code of doctor
     */
    private String postalCode;

    /**
     * License number of doctor (issued by medical authority)
     */
    @NotBlank
    private String licenseNumber;

    /**
     * List of string for specializations of doctor
     */
    @NotNull
    private List<String> specializationsOfDoctor;
}