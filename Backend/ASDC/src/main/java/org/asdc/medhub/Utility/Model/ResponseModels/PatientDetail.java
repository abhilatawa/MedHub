package org.asdc.medhub.Utility.Model.ResponseModels;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model that carries details of doctor for response
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientDetail {

    /**
     * Firstname of Patient
     */
    @NotBlank
    private String firstName;

    /**
     * Lastname of Patient
     */
    @NotBlank
    private String lastName;

    /**
     * Medical History of Patient
     */
    private String medicalHistory;
 }
