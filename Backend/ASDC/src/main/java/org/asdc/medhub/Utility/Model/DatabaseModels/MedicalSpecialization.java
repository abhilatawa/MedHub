package org.asdc.medhub.Utility.Model.DatabaseModels;
import jakarta.persistence.*;
import lombok.*;
import org.asdc.medhub.Utility.Constant.DatabaseConstants;
import java.sql.Timestamp;

/**
 * represents the table medical specialization in database
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name= DatabaseConstants.MedicalSpecializationTable.tableName)
public class MedicalSpecialization {

    /**
     * Primary key | auto increment
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Name of specialization
     */
    @Column(name = DatabaseConstants.MedicalSpecializationTable.Columns.specialization)
    private String specialization;

    /**
     * Timestamp when record was created
     */
    @Column(name = DatabaseConstants.MedicalSpecializationTable.Columns.createdAt)
    private Timestamp createdAt;

    /**
     * Timestamp when record was updated
     */
    @Column(name = DatabaseConstants.MedicalSpecializationTable.Columns.updatedAt)
    private Timestamp updatedAt;
}