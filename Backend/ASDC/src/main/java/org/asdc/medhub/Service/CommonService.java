package org.asdc.medhub.Service;

import io.micrometer.common.util.StringUtils;
import org.asdc.medhub.Configuration.CustomConfigurations;
import org.asdc.medhub.Service.Interface.ICommonService;
import org.asdc.medhub.Utility.Model.DatabaseModels.Appointment;
import org.asdc.medhub.Utility.Model.DatabaseModels.Doctor;
import org.asdc.medhub.Utility.Model.DatabaseModels.Pharmacist;
import org.asdc.medhub.Utility.Model.DatabaseModels.SpecializationOfDoctor;
import org.asdc.medhub.Utility.Model.ResponseModels.AppointmentDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.DoctorDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.PharmacistDetail;
import org.asdc.medhub.Utility.UtilityMethods;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;

/**
 * Service class contains common operations that is shared by other services
 */
@Service
public class CommonService implements ICommonService {

    /**
     * Custom configurations specified in application.properties
     */
    private final CustomConfigurations customConfigurations;

    /**
     * Constructor
     * @param customConfigurations Custom configuration object
     */
    public CommonService(CustomConfigurations customConfigurations){
        this.customConfigurations=customConfigurations;
    }

    /**
     * Converts a Doctor entity to a DoctorDetail object.
     * @param doctor The Doctor entity to be converted.
     * @return The corresponding DoctorDetail object.
     */
    public DoctorDetail convertDoctorToDoctorDetail(Doctor doctor) {
        DoctorDetail doctorDetail = new DoctorDetail();
        doctorDetail.setUsername(doctor.getUser().getUsername());
        doctorDetail.setId(doctor.getId());
        doctorDetail.setFirstName(doctor.getFirstName());
        doctorDetail.setLastName(doctor.getLastName());
        doctorDetail.setContactNumber(doctor.getContactNumber());
        doctorDetail.setAddressLine1(doctor.getAddressLine1());
        doctorDetail.setAddressLine2(doctor.getAddressLine2());
        doctorDetail.setPostalCode(doctor.getPostalCode());
        doctorDetail.setSpecializationsOfDoctor(doctor.getSpecializationsOfDoctor().stream().map(SpecializationOfDoctor::getSpecialization).toList());
        doctorDetail.setStartTime(doctor.getStartTime());
        doctorDetail.setEndTime(doctor.getEndTime());
        doctorDetail.setJobExpTitle(doctor.getJobDescription());
        doctorDetail.setJobDescription(doctor.getJobDescription());
        doctorDetail.setSunday(doctor.isSunday());
        doctorDetail.setMonday(doctor.isMonday());
        doctorDetail.setTuesday(doctor.isTuesday());
        doctorDetail.setWednesday(doctor.isWednesday());
        doctorDetail.setThursday(doctor.isThursday());
        doctorDetail.setFriday(doctor.isFriday());
        doctorDetail.setSaturday(doctor.isSaturday());
        doctorDetail.setLicenseNumber(doctor.getLicenseNumber());
        doctorDetail.setReceiveEmailNotification(doctor.getUser().isReceiveEmailNotification());

        if(StringUtils.isBlank(doctor.getProfilePictureFileName())){
            doctorDetail.setProfilePictureBase64String("");
        }else{
            doctorDetail.setProfilePictureBase64String(UtilityMethods.getBase64StringFromImage(this.customConfigurations.getProfilePictureFolderPath(),doctor.getProfilePictureFileName()));
        }
        return doctorDetail;
    }


    /**
     * Sets the specializations in doctor and adds doctor link in all specializations
     * @param specializationOfDoctor - List os specializations to add in doctor's object
     * @param doctor - Reference to the target doctor object
     */
    private void setSpecializationOfDoctorToDoctorModel(List<String> specializationOfDoctor, Doctor doctor){
        specializationOfDoctor.forEach(item->{
            SpecializationOfDoctor specialization=new SpecializationOfDoctor();
            specialization.setSpecialization(item);
            specialization.setCreatedAt(Timestamp.from(Instant.now()));
            specialization.setUpdatedAt(Timestamp.from(Instant.now()));
            specialization.setDoctor(doctor);
            doctor.getSpecializationsOfDoctor().add(specialization);
        });
    }

    /**
     * Updates the Doctor entity using information from the DoctorDetail object.
     *
     * @param doctor        The Doctor entity to be updated.
     * @param doctorDetail  The DoctorDetail object containing updated doctor profile information.
     */
    public Doctor updateDoctorFromDoctorDetailModel(Doctor doctor, DoctorDetail doctorDetail) {

        doctor.setFirstName(doctorDetail.getFirstName());
        doctor.setLastName(doctorDetail.getLastName());
        doctor.setAddressLine1(doctorDetail.getAddressLine1());
        doctor.setAddressLine2(doctorDetail.getAddressLine2());
        doctor.setPostalCode(doctorDetail.getPostalCode());
        this.setSpecializationOfDoctorToDoctorModel(doctorDetail.getSpecializationsOfDoctor(), doctor);
        doctor.setStartTime(doctorDetail.getStartTime());
        doctor.setEndTime(doctorDetail.getEndTime());
        doctor.setJobExpTitle(doctorDetail.getJobDescription());
        doctor.setJobDescription(doctorDetail.getJobDescription());
        doctor.setSunday(doctorDetail.isSunday());
        doctor.setMonday(doctorDetail.isMonday());
        doctor.setTuesday(doctorDetail.isTuesday());
        doctor.setWednesday(doctorDetail.isWednesday());
        doctor.setThursday(doctorDetail.isThursday());
        doctor.setFriday(doctorDetail.isFriday());
        doctor.setSaturday(doctorDetail.isSaturday());
        doctor.getUser().setReceiveEmailNotification(doctorDetail.isReceiveEmailNotification());
        return doctor;
    }

    /**
     * Converts Appointment to AppointmentDetail
     * @param appointment appointment model
     * @return AppointmentDetail
     */
    public AppointmentDetail getAppointmentDetailModelFromAppointment(Appointment appointment){
        AppointmentDetail appointmentDetail =new AppointmentDetail();
        appointmentDetail.setId(appointment.getId());
        appointmentDetail.setAppointmentDate(appointment.getAppointmentDate());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        appointmentDetail.setTimeSlot(timeFormat.format(appointment.getStartTime())
                + " to "
                + timeFormat.format(appointment.getEndTime()));

        appointmentDetail.setStatus(appointment.getStatus());
        appointmentDetail.setRemarksFromPatient(appointment.getRemarksFromPatient());
        appointmentDetail.setDayOfWeek(appointment.getWeekDay());
        appointmentDetail.setDoctorName(appointment.getDoctor().getFirstName()+" "+appointment.getDoctor().getLastName());
        appointmentDetail.setPatientName(appointment.getPatient().getFirstName()+" "+appointment.getPatient().getLastName());
        appointmentDetail.setCreatedAt(appointment.getCreatedAt());
        appointmentDetail.setFeedbackMessage(appointment.getFeedbackMessage());
        appointmentDetail.setRating(appointment.getRating());
         appointmentDetail.setDoctorEmail(appointment.getDoctor().getUser().getUsername());
        appointmentDetail.setDoctorContactNumber(appointment.getDoctor().getContactNumber());
        appointmentDetail.setPatientName(appointment.getPatient().getFirstName()+" "+appointment.getPatient().getLastName());
        appointmentDetail.setPatientEmail(appointment.getPatient().getUser().getUsername());
        appointmentDetail.setCreatedAt(appointment.getCreatedAt());
        if(appointment.getPrescription()!=null){
            appointmentDetail.setPrescription(appointment.getPrescription());
        }

        if(appointment.getPharmacist()!=null){
            appointmentDetail.setPharmacistId(appointment.getPharmacist().getId());
            appointmentDetail.setPharmacyName(appointment.getPharmacist().getPharmacyName());
        }

        return appointmentDetail;
    }

    /**
     * Converts a Pharmacist object to a PharmacistDetail object for an individual pharmacist.
     *
     * @param pharmacist The Pharmacist object to be converted to PharmacistDetail.
     * @return A PharmacistDetail object containing the details of the pharmacist.
     */
    public PharmacistDetail convertPharmacistToPharmacistDetail(Pharmacist pharmacist) throws NullPointerException {
        PharmacistDetail pharmacistDetail = new PharmacistDetail();
        pharmacistDetail.setId(pharmacist.getId());
        pharmacistDetail.setFirstName(pharmacist.getFirstName());
        pharmacistDetail.setLastName(pharmacist.getLastName());
        pharmacistDetail.setPharmacyName(pharmacist.getPharmacyName());
        pharmacistDetail.setContactNumber(pharmacist.getContactNumber());
        pharmacistDetail.setAddressLine1(pharmacist.getAddressLine1());
        pharmacistDetail.setAddressLine2(pharmacist.getAddressLine2());
        pharmacistDetail.setPostalCode(pharmacist.getPostalCode());
        pharmacistDetail.setLicenseNumber(pharmacist.getLicenseNumber());
        pharmacistDetail.setUsername(pharmacist.getUser().getUsername());
        return pharmacistDetail;
    }
}
