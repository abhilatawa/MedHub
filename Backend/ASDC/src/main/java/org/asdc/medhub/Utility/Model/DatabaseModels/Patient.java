package org.asdc.medhub.Utility.Model.DatabaseModels;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.asdc.medhub.Utility.Constant.DatabaseConstants;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * represents table patient of database
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name= DatabaseConstants.PatientTable.tableName)
public class Patient{

    /**
     * primary key  | auto increment
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Firstname of patient
     */
    @Column(name = DatabaseConstants.PatientTable.Columns.firstName)
    protected String firstName;

    /**
     * Lastname of patient
     */
    @Column(name = DatabaseConstants.PatientTable.Columns.lastName)
    protected String lastName;

    /**
     * Medical history of patient
     */
    @Column(name = DatabaseConstants.PatientTable.Columns.medicalHistory)
    protected String medicalHistory;

    /**
     * Booking history of patient
     */
    @Column(name = DatabaseConstants.PatientTable.Columns.bookingHistory)
    protected String bookingHistory;

    /**
     * Linked user record of patient
     */
    @OneToOne(mappedBy = "patient")
    private User user;

    /**
     * Linked appointments of patient
     */
    @OneToMany(mappedBy = "patient", cascade = CascadeType.PERSIST)
    private List<Appointment> appointments=new ArrayList<>();

    /**
     * Timestamp when record was created
     */
    @Column(name = DatabaseConstants.PatientTable.Columns.createdAt)
    private Timestamp createdAt;

    /**
     * Timestamp when record was updated
     */
    @Column(name = DatabaseConstants.PatientTable.Columns.updatedAt)
    private Timestamp updatedAt;
}
