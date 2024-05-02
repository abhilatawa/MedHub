package org.asdc.medhub.Utility.Model.RequestModels;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

/**
 * To accept filter parameter for doctors
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorFilterModel {

    /**
     * Specialization - list of strings
     */
    @NotNull
    private List<String> specializations;
}
