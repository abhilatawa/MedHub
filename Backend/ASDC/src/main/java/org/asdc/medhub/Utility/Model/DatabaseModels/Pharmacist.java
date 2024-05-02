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
 * Represents the pharmacist table of database
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = DatabaseConstants.PharmacistTable.tableName)
public class Pharmacist {

    /**
     * primary key | auto increment
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Firstname of pharmacist
     */
    @Column(name = DatabaseConstants.PharmacistTable.Columns.firstName)
    protected String firstName;

    /**
     * Lastname of pharmacist
     */
    @Column(name = DatabaseConstants.PharmacistTable.Columns.lastName)
    protected String lastName;

    /**
     * Name of pharmacy that pharmacist is related to
     */
    @Column(name = DatabaseConstants.PharmacistTable.Columns.pharmacyName)
    protected String pharmacyName;

    /**
     * Contact number of pharmacist
     */
    @Column(name = DatabaseConstants.PharmacistTable.Columns.contactNumber)
    protected String contactNumber;

    /**
     * Address line 1 of pharmacist
     */
    @Column(name=DatabaseConstants.PharmacistTable.Columns.addressLine1)
    protected String addressLine1;

    /**
     * Address line 2 of pharmacist
     */
    @Column(name=DatabaseConstants.PharmacistTable.Columns.addressLine2)
    protected String addressLine2;

    /**
     * Postal code of pharmacist
     */
    @Column(name=DatabaseConstants.PharmacistTable.Columns.postalCode)
    protected String postalCode;

    /**
     * License number of pharmacist (issued by medical authority)
     */
    @Column(name = DatabaseConstants.PharmacistTable.Columns.licenseNumber)
    protected String licenseNumber;

    /**
     * Linked user record of pharmacist
     */
    @OneToOne(mappedBy = "pharmacist")
    private User user;

    /**
     * Linked appointments of pharmacists
     */
    @OneToMany(mappedBy = "pharmacist", cascade = CascadeType.PERSIST)
    private List<Appointment> appointments=new ArrayList<>();

    /**
     * Timestamp when record was created
     */
    @Column(name = DatabaseConstants.PharmacistTable.Columns.createdAt)
    private Timestamp createdAt;

    /**
     * Timestamp when record was updated
     */
    @Column(name = DatabaseConstants.PharmacistTable.Columns.updatedAt)
    private Timestamp updatedAt;
}
