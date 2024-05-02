package org.asdc.medhub.Utility.Model.ResponseModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model that carries details of pharmacist for response
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PharmacistDetail {

    /**
     * Pharmacist id
     */
    private int id;

    /**
     * Username of pharmacist (here email)
     */
    private String username;

    /**
     * Firstname of pharmacist
     */
    private String firstName;

    /**
     * Lastname of pharmacist
     */
    private String lastName;

    /**
     * pharmacyName of pharmacist
     */
    private String pharmacyName;

    /**
     * Contact number of pharmacist
     */
    private String contactNumber;

    /**
     * Address line 1 of pharmacist
     */
    private String addressLine1;

    /**
     * Address line 2 of pharmacist
     */
    private String addressLine2;

    /**
     * Postal code of pharmacist
     */
    private String postalCode;

    /**
     * License number of pharmacist
     */
    private String licenseNumber;
}
