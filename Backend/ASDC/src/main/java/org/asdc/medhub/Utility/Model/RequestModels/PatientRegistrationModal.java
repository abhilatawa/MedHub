package org.asdc.medhub.Utility.Model.RequestModels;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.asdc.medhub.Utility.Model.DatabaseModels.User;

/**
 * Model used for registration request of patient
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientRegistrationModal extends User {

    /**
     * First name of patient
     */
    @NotBlank
    protected String firstName;

    /**
     * Last name of patient
     */
    @NotBlank
    protected String lastName;
}
