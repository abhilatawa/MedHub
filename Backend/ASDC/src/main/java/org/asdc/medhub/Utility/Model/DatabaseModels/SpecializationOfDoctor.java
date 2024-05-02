package org.asdc.medhub.Utility.Model.DatabaseModels;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.asdc.medhub.Utility.Constant.DatabaseConstants;

import java.sql.Timestamp;

/**
 * represents table patient of database
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = DatabaseConstants.SpecializationOfDoctorTable.tableName)
public class SpecializationOfDoctor {

    /**
     * primary key | auto increment
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Doctor ID to which this specialization is linked with
     */
    @ManyToOne
    @JoinColumn(name= DatabaseConstants.SpecializationOfDoctorTable.Columns.doctorId)
    @JsonIgnore
    private Doctor doctor;

    /**
     * Name of specialization
     */
    @Column(name = DatabaseConstants.SpecializationOfDoctorTable.Columns.specialization)
    private String specialization;

    /**
     * Timestamp when record was created
     */
    @Column(name = DatabaseConstants.SpecializationOfDoctorTable.Columns.createdAt)
    private Timestamp createdAt;

    /**
     * Timestamp when record was updated
     */
    @Column(name = DatabaseConstants.SpecializationOfDoctorTable.Columns.updatedAt)
    private Timestamp updatedAt;
}