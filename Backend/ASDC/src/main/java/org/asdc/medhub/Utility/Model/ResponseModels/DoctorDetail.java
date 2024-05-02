package org.asdc.medhub.Utility.Model.ResponseModels;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Model that carries details of doctor for response
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDetail {

    /**
     * Doctor id
     */
    @NotNull
    private int id;

    /**
     * Username of doctor (here email)
     */
    @Email
    private String username;

    /**
     * Firstname of doctor
     */
    @NotBlank
    private String firstName;

    /**
     * Lastname of doctor
     */
    @NotBlank
    private String lastName;

    /**
     * Contact number of doctor
     */
    private String contactNumber;

    /**
     * Address line 1 of doctor
     */
    private String addressLine1;

    /**
     * Address line 2 of doctor
     */
    private String addressLine2;

    /**
     * Postal code of doctor
     */
    private String postalCode;

    /**
     * License number of doctor
     */
    @NotBlank
    private String licenseNumber;

    /**
     * JobExpTitle of doctor
     */
    public  String jobExpTitle;

    /**
     * jobDescription of doctor
     */
    public  String jobDescription;

    /**
     * startTime of doctor
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    public Timestamp startTime ;

    /**
     * endTime of doctor
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    public Timestamp endTime ;

    /**
     * sunday availability of doctor
     */
    private  boolean sunday ;

    /**
     * monday availability of doctor
     */
    private  boolean monday;

    /**
     * tuesday availability of doctor
     */
    private  boolean tuesday;

    /**
     * wednesday availability of doctor
     */
    private  boolean wednesday;

    /**
     * thursday availability of doctor
     */
    private  boolean thursday;

    /**
     * friday availability of doctor
     */
    private  boolean friday;

    /**
     * saturday availability of doctor
     */
    private  boolean  saturday;

    /**
     * Profile picture image string of doctor
     */
    private String profilePictureBase64String;

    /**
     * Flag to set notification preferences
     */
    private boolean receiveEmailNotification;

    /**
     * sunday availability of doctor
     */
    @NotNull
    private List<String> specializationsOfDoctor=new ArrayList<>();
}
