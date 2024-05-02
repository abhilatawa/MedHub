package org.asdc.medhub.Utility.Model.RequestModels;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.asdc.medhub.Utility.Model.DatabaseModels.User;

/**
 * Model used for registration request of pharmacist
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PharmacistRegistrationModel extends User {

    /**
     * First name of pharmacist
     */
    @NotBlank
    private String firstName;

    /**
     * Last name of pharmacist
     */
    @NotBlank
    private String lastName;

    /**
     * Name of pharmacy related to
     */
    @NotBlank
    private String pharmacyName;

    /**
     * Contact number of pharmacist
     */
    @NotBlank
    private String contactNumber;

    /**
     * Address line 1 of pharmacy
     */
    private String addressLine1;

    /**
     * Address lin 2 of pharmacy
     */
    private String addressLine2;

    /**
     * Postal code of pharmacy
     */
    private String postalCode;

    /**
     * License number of pharmacy (issued by medical authority)
     */
    @NotBlank
    private String licenseNumber;
}
