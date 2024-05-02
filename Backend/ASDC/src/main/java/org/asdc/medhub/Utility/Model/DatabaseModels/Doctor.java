package org.asdc.medhub.Utility.Model.DatabaseModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.asdc.medhub.Utility.Constant.DatabaseConstants;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * represents table doctor of database
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = DatabaseConstants.DoctorTable.tableName)
public class Doctor{

    /**
     * primary key | auto increment
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    /**
     * Firstname of doctor
     */
    @Column(name = DatabaseConstants.DoctorTable.Columns.firstName)
    protected String firstName;

    /**
     * Lastname of doctor
     */
    @Column(name = DatabaseConstants.DoctorTable.Columns.lastName)
    protected String lastName;

    /**
     * Contact number of doctor
     */
    @Column(name = DatabaseConstants.DoctorTable.Columns.contactNumber)
    protected String contactNumber;

    /**
     * Address line 1 of doctor
     */
    @Column(name=DatabaseConstants.DoctorTable.Columns.addressLine1)
    protected String addressLine1;

    /**
     * Address line 2 of doctor
     */
    @Column(name=DatabaseConstants.DoctorTable.Columns.addressLine2)
    protected String addressLine2;

    /**
     * Postal code of doctor
     */
    @Column(name=DatabaseConstants.DoctorTable.Columns.postalCode)
    protected String postalCode;

    /**
     * License number of doctor (issued by medical authority)
     */
    @Column(name = DatabaseConstants.DoctorTable.Columns.licenseNumber)
    protected String licenseNumber;

    /**
     * Linked user record of doctor
     */
    @JsonIgnore
    @OneToOne(mappedBy = "doctor")
    private User user;

    /**
     * Linked specializations of doctor
     */
    @OneToMany(mappedBy = "doctor",cascade = CascadeType.PERSIST)
    protected List<SpecializationOfDoctor> specializationsOfDoctor=new ArrayList<>();

    /**
     * Linked appointments of doctor
     */
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.PERSIST)
    private List<Appointment> appointments=new ArrayList<>();

    /**
     * Job experience title of the doctor
     */
    @Column(name=DatabaseConstants.DoctorTable.Columns.jobExpTitle)
    private String jobExpTitle;

    /**
     * Job description of the doctor
     */
    @Column(name=DatabaseConstants.DoctorTable.Columns.jobDescription)
    private String jobDescription;

    /**
     * Start time of the doctor's availability
     */
    @Getter(AccessLevel.NONE)
    @Column(name=DatabaseConstants.DoctorTable.Columns.startTime)
    private Timestamp startTime;

    public Timestamp getStartTime(){
        return this.startTime == null
                ? Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)))
                : Timestamp.valueOf(this.startTime.toLocalDateTime().toLocalTime().atDate(LocalDate.now()));
    }

    /**
     * End time of the doctor's availability
     */
    @Getter(AccessLevel.NONE)
    @Column(name=DatabaseConstants.DoctorTable.Columns.endTime)
    private Timestamp endTime;

    public Timestamp getEndTime(){
        return this.endTime == null
                ? Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)))
                : Timestamp.valueOf(this.endTime.toLocalDateTime().toLocalTime().atDate(LocalDate.now()));

    }

    /**
     * Availability on Monday
     */
    @Column(name=DatabaseConstants.DoctorTable.Columns.monday)
    private boolean monday;

    /**
     * Availability on Tuesday
     */
    @Column(name=DatabaseConstants.DoctorTable.Columns.tuesday)
    private boolean tuesday;

    /**
     * Availability on Wednesday
     */
    @Column(name=DatabaseConstants.DoctorTable.Columns.wednesday)
    private boolean wednesday;

    /**
     * Availability on Thursday
     */
    @Column(name=DatabaseConstants.DoctorTable.Columns.thursday)
    private boolean thursday;

    /**
     * Availability on Friday
     */
    @Column(name=DatabaseConstants.DoctorTable.Columns.friday)
    private boolean friday;

    /**
     * Availability on Saturday
     */
    @Column(name=DatabaseConstants.DoctorTable.Columns.saturday)
    private boolean saturday;

    /**
     * Availability on Sunday
     */
    @Column(name=DatabaseConstants.DoctorTable.Columns.sunday)
    private boolean sunday;

    /**
     * The file path of the profile picture stored in the database.
     * It is mapped to the column named "profile_picture_file_path" in the database table.
     */
    @Column(name = DatabaseConstants.DoctorTable.Columns.profilePictureFilePath)
    private String profilePictureFileName;

    /**
     * List of blogs associated with the doctor
     */
    @OneToMany(mappedBy = "doctor",cascade = CascadeType.PERSIST)
    protected List<Blogs> listOfBlogs=new ArrayList<>();

    /**
     * Timestamp when record was created
     */
    @Column(name = DatabaseConstants.DoctorTable.Columns.createdAt)
    private Timestamp createdAt;

    /**
     * Timestamp when record was updated
     */
    @Column(name = DatabaseConstants.DoctorTable.Columns.updatedAt)
    private Timestamp updatedAt;
}
