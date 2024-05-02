package org.asdc.medhub.Service;

import org.asdc.medhub.Repository.*;
import org.asdc.medhub.Service.Interface.ICommonService;
import org.asdc.medhub.Service.Interface.IPatientService;
import org.asdc.medhub.Utility.Enums.AppointmentStatus;
import org.asdc.medhub.Utility.Model.DatabaseModels.*;
import org.asdc.medhub.Utility.Model.RequestModels.AppointmentBookingModel;
import org.asdc.medhub.Utility.Model.RequestModels.DoctorFilterModel;
import org.asdc.medhub.Utility.Model.ResponseModel;
import org.asdc.medhub.Utility.Model.ResponseModels.AppointmentDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.DoctorDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.PatientDetail;
import org.asdc.medhub.Utility.UtilityMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Contains supported methods for patient controller
 */
@Service
public class PatientService implements IPatientService {

    /**
     * Common service instance
     */
    private final ICommonService commonService;

    /**
     * User repository instance
     */
    private final UserRepository userRepository;

    /**
     * DoctorRepository instance
     */
    private final DoctorRepository doctorRepository;

    /**
     * MedicalSpecializationRepository instance
     */
    private final MedicalSpecializationRepository medicalSpecializationRepository;

    /**
     * AppointmentRepository instance
     */
    private final AppointmentRepository appointmentRepository;

    /**
     * Patient repository instance
     */
    private final PatientRepository patientRepository;

    /**
     * Parameterized constructor
     * @param userRepository - userRepository bean
     * @param patientRepository - patientRepository bean
     */
    @Autowired
    public PatientService(UserRepository userRepository,
                          PatientRepository patientRepository,
                          DoctorRepository doctorRepository,
                          MedicalSpecializationRepository medicalSpecializationRepository,
                          AppointmentRepository appointmentRepository,
                          ICommonService commonService) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository=doctorRepository;
        this.medicalSpecializationRepository=medicalSpecializationRepository;
        this.appointmentRepository=appointmentRepository;
        this.commonService=commonService;
    }

    /**
     * Retrieves individual details of the patient using email.
     *
     * @param email The email of the user whose patient profile details need to be retrieved.
     * @return A ResponseModel containing the patient's details if found, or an error message if no doctor is found.
     */
    public ResponseModel<PatientDetail> getPatientProfile(String email) {
        ResponseModel<PatientDetail> response = new ResponseModel<>();
        try {
            Patient patient = this.userRepository.findUserByUsername(email).getPatient();
            if (patient != null) {
                PatientDetail patientDetail = this.convertPatientToPatientDetail(patient);
                response.setResponseData(patientDetail);
                response.setMessage("Patient details retrieved successfully");
                response.setSuccess(true);
            } else {
                response.setMessage("No patient found for the provided email");
            }
        } catch (Exception e) {
            response.setMessage("An error occurred while fetching patient details: " + e.getMessage());
        }
        return response;
    }

    /**
     * Converts a Patient object to a PatientDetail object for an individual doctor.
     *
     * @param patient The Patient object to be converted.
     * @return A PatientDetail object containing the details of the individual doctor.
     */
    private PatientDetail convertPatientToPatientDetail(Patient patient) {
        PatientDetail patientDetail = new PatientDetail();
        patientDetail.setFirstName(patient.getFirstName());
        patientDetail.setLastName(patient.getLastName());
        patientDetail.setMedicalHistory(patient.getMedicalHistory());
        return patientDetail;
    }

    /**
     * Edits individual details of the patient using email and patientDetail
     * @param email The email of the user whose patient profile details need to be retrieved.
     * @param patientDetail The field name of the patientDetail model
     * @return A ResponseModel containing the result of update
     */
    public ResponseModel<String> editPatientProfile(String email, PatientDetail patientDetail) {
        ResponseModel<String> response = new ResponseModel<>();
        try {
                Patient patient = this.userRepository.findUserByUsername(email).getPatient();
                this.updatePatientFromPatientDetailModel(patient,patientDetail);
                this.patientRepository.save(patient);
                response.setSuccess(true);
                response.setMessage("Patient profile updated successfully");
        } catch (Exception e) {
            response.setMessage("An error occurred while updating patient details: " + e.getMessage());
        }
        return response;
    }

    /**
     * Returns doctor details list with applied filters
     * If filters are empty then returns all the verified doctors
     * @param filter DoctorFilterModel
     * @return ResponseModel with List of DoctorDetail
     */
    public ResponseModel<List<DoctorDetail>> getAllFilteredDoctorList(DoctorFilterModel filter){
        ResponseModel<List<DoctorDetail>> responseModel=new ResponseModel<>();
        responseModel.responseData=new ArrayList<>();

        try{
            List<Doctor> doctorsListFromDb;
            //If filters are empty then return all verified doctors
            if(!filter.getSpecializations().isEmpty()){
                doctorsListFromDb=this.doctorRepository.getVerifiedDoctorsBySpecializationAvailableIn(filter.getSpecializations());
            }else{
                doctorsListFromDb=this.doctorRepository.getAllVerifiedDoctors();
            }
            doctorsListFromDb.forEach(doctor-> responseModel.responseData.add(this.commonService.convertDoctorToDoctorDetail(doctor)));

            responseModel.isSuccess=true;
            responseModel.message="Found : "+responseModel.responseData.size()+ " doctors.";
        }
        catch (Exception exception){
            responseModel.message="Exception : "+exception.getMessage();
        }
        return responseModel;
    }

    /**
     * Searches given string in specialization table using like query
     * @param searchString - string to search
     * @return List of string of specialization matched
     */
    public ResponseModel<List<String>> getMedicalSpecializationByName(String searchString){
        ResponseModel<List<String>> responseModel=new ResponseModel<>();

        try {
            var medicalSpecializationList=this.medicalSpecializationRepository
                    .getMedicalSpecializationsBySpecializationContainingIgnoreCase(searchString);

            responseModel.responseData= medicalSpecializationList
                    .stream()
                    .map(MedicalSpecialization::getSpecialization)
                    .collect(Collectors.toList());
            responseModel.isSuccess=true;
            responseModel.message="Found "+responseModel.responseData.size()+" similar specializations.";
        }
        catch (Exception exception){
            responseModel.responseData=null;
            responseModel.message=exception.getMessage();
        }
        return responseModel;
    }

    /**
     * Updates the patient entity with data from the provided PatientDetail object.
     *
     * @param patient The Patient entity to be updated.
     * @param patientDetail The PatientDetail object containing updated patient data.
     */
    private void updatePatientFromPatientDetailModel(Patient patient, PatientDetail patientDetail){
        patient.setFirstName(patientDetail.getFirstName());
        patient.setLastName(patientDetail.getLastName());
        patient.setMedicalHistory(patientDetail.getMedicalHistory());
    }

    /**
     * Returns all appointments of patient
     * @param user User model of patient
     * @return AppointmentDetail list
     */
    public ResponseModel<List<AppointmentDetail>> getAppointments(User user){
        ResponseModel<List<AppointmentDetail>> response = new ResponseModel<>();
        try {
            var appointments=this.appointmentRepository.getAppointmentsByPatientId(user.getPatient().getId());
            response.responseData=appointments.stream()
                    .map(this.commonService::getAppointmentDetailModelFromAppointment)
                    .collect(Collectors.toList());
            response.setMessage("Found : "+ response.responseData.size()+ " appointments.");
            response.setSuccess(true);
        }
        catch (Exception e) {
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /**
     * Create a new appointment entry in database
     * @param appointmentBookingModel appointment booking details
     * @param currentPatient current logged in patient
     * @return AppointmentBookingModel
     */
    public ResponseModel<AppointmentBookingModel> bookAppointment(AppointmentBookingModel appointmentBookingModel,User currentPatient){
        ResponseModel<AppointmentBookingModel> responseModel=new ResponseModel<>();
        try{
            Appointment appointment=this.getAppointmentFromAppointmentBookingModel(appointmentBookingModel,currentPatient.getId());
            this.appointmentRepository.save(appointment);
            responseModel.isSuccess=true;
            responseModel.message="Appointment booked successfully!";
        }
        catch (Exception exception){
            responseModel.message=exception.getMessage();
        }
        return responseModel;
    }

    /**
     * Retrieves individual details of the doctor using email.
     * @param doctorId The id of the doctor whose profile details need to be retrieved.
     * @return A ResponseModel containing the doctor's details if found, or an error message if no doctor is found.
     */
    public ResponseModel<DoctorDetail> getDoctorDetails(int doctorId) {
        ResponseModel<DoctorDetail> response = new ResponseModel<>();
        try {
            var doctorFromDb = this.doctorRepository.findById(doctorId);
            if(doctorFromDb.isPresent()) {
                Doctor doctor=doctorFromDb.get();
                response.setMessage("Doctor details retrieved successfully");
                response.setSuccess(true);
                DoctorDetail doctorDetail = this.commonService.convertDoctorToDoctorDetail(doctor);
                response.setResponseData(doctorDetail);
            }
        } catch (Exception e) {
            response.setMessage("An error occurred while fetching doctor details: " + e.getMessage());
        }
        return response;
    }

    /**
     * Converts from AppointmentBookingModel to Appointment model
     * @param appointmentBookingModel data received from user
     * @return Appointment
     */
    private Appointment getAppointmentFromAppointmentBookingModel(AppointmentBookingModel appointmentBookingModel,int patientId){
        Appointment appointment=new Appointment();
        appointment.setDoctor(this.doctorRepository
                .findById(appointmentBookingModel.getDoctorId())
                .orElseThrow(()->new NullPointerException("Doctor not found!")));
        appointment.setPatient(this.userRepository
                .findById(patientId)
                .orElseThrow(() -> new NullPointerException("Patient not found!"))
                .getPatient());
        appointment.setStartTime(appointmentBookingModel.getStartTime());
        appointment.setEndTime(appointmentBookingModel.getEndTime());
        appointment.setWeekDay(UtilityMethods.getWeekDayFromDate(appointmentBookingModel.getAppointmentDate()));
        appointment.setAppointmentDate(java.sql.Date.valueOf(appointmentBookingModel.getAppointmentDate()));
        appointment.setStatus(AppointmentStatus.BOOKED);
        appointment.setRemarksFromPatient(appointmentBookingModel.getRemarksFromPatient());
        appointment.setCreatedAt(Timestamp.from(Instant.now()));
        appointment.setUpdatedAt(Timestamp.from(Instant.now()));
        return appointment;
    }

    /**
     * Gets doctor's available timeslots
     * @param doctorId Doctor id
     * @return HashMap of day wise available timeslots
     */
    public ResponseModel<HashMap<String,List<String>>> getDoctorAvailabilityTimeSlots(int doctorId){
        ResponseModel<HashMap<String,List<String>>> responseModel=new ResponseModel<>();
        try {
            Doctor doctor = this.doctorRepository
                    .findById(doctorId)
                    .orElseThrow(() -> new NullPointerException("Doctor not found!"));

            responseModel.responseData= this.getWeekWiseAvailabilityOfDoctor(doctor);
            responseModel.isSuccess=true;
        }
        catch (Exception exception){
            responseModel.message=exception.getMessage();
        }
        return responseModel;
    }

    /**
     * Get list
     * @param doctor Doctor object
     * @return HashMap of week wise available time slot
     */
    private HashMap<String,List<String>> getWeekWiseAvailabilityOfDoctor(Doctor doctor){
        //Get start time rounded to next/previous 30 minutes
        long startTime= this.roundTo30Minutes((
                (doctor.getStartTime() != null)
                    ? doctor.getStartTime(): new Timestamp(LocalDate.now().atTime(10, 0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())).getTime());
        //Get end time rounded to next/previous 30 minutes
        long endTime = this.roundTo30Minutes((
                (doctor.getEndTime() != null)
                        ? doctor.getEndTime(): new Timestamp(LocalDate.now().atTime(17, 0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())).getTime());

        HashMap<String,List<String>> availability=new HashMap<>();
        for (DayOfWeek weekDay: DayOfWeek.values()){
           if(this.isDoctorAvailableForDay(doctor,weekDay)) {
               var upComingAppointments = this.appointmentRepository.getAppointmentsByDoctorIdAndStatusAndWeekDay(doctor.getId(), AppointmentStatus.BOOKED, weekDay);
               availability.put(weekDay.toString().toLowerCase(), this.getListOf30minutesTimeSlots(startTime, endTime, upComingAppointments));
           }
        }
        return  availability;
    }

    /**
     * Checks if doctor is available for given day of week or not
     * @param doctor Doctor
     * @param weekDay DayOfWeek enum
     * @return Boolean
     */
    private boolean isDoctorAvailableForDay(Doctor doctor,DayOfWeek weekDay){
        boolean isAvailable;
        isAvailable= switch (weekDay) {
            case SUNDAY -> doctor.isSunday();
            case MONDAY -> doctor.isMonday();
            case TUESDAY -> doctor.isTuesday();
            case WEDNESDAY -> doctor.isWednesday();
            case THURSDAY -> doctor.isThursday();
            case FRIDAY -> doctor.isFriday();
            case SATURDAY -> doctor.isSaturday();
        };
        return isAvailable;
    }

    /**
     *
     * @param startTime  start time
     * @param endTime end time
     * @param bookedAppointments List of appointments to check the conflict
     * @return List of string of available slots
     */
    private List<String> getListOf30minutesTimeSlots(long startTime,long endTime,List<Appointment> bookedAppointments){
        List<String> timeSlots = new ArrayList<>();

        while (startTime <= endTime) {
            Timestamp slotStartTime = new Timestamp(startTime);
            Timestamp slotEndTime = new Timestamp(startTime + 30 * 60 * 1000); // 30 minutes in milliseconds
            if(slotEndTime.getTime()>=endTime){
                break;
            }

            long conflictingAppointmentCount = bookedAppointments
                    .stream()
                    .filter(appointment -> {
                        var slotStartTimeWithCorrectDate=Timestamp.valueOf(slotStartTime.toLocalDateTime()
                                .withYear(appointment.getStartTime().toLocalDateTime().getYear())
                                .withMonth(appointment.getStartTime().toLocalDateTime().getMonth().getValue())
                                .withDayOfMonth(appointment.getStartTime().toLocalDateTime().getDayOfMonth()));
                        boolean isConflicting= appointment.getStartTime().getTime() <= slotStartTimeWithCorrectDate.getTime() && slotStartTimeWithCorrectDate.getTime() <= appointment.getEndTime().getTime();
                        return isConflicting;
                    }).count();

            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            String timeSlot = timeFormat.format(slotStartTime) + " to " + timeFormat.format(slotEndTime);

            if(conflictingAppointmentCount==0) {
                timeSlots.add(timeSlot);
            }
            // Move to the next time slot
            startTime += 30 * 60 * 1000;
        }
        return timeSlots;
    }

    /**
     * Rounds timestamp to next 30 minutes
     * @param timestamp epoch time
     * @return Long
     */
    private long roundTo30Minutes(long timestamp) {
        long millisIn30Minutes = 30 * 60 * 1000; // 30 minutes in milliseconds
        long remainder = timestamp % millisIn30Minutes;
        return timestamp + (remainder > 0 ? (millisIn30Minutes - remainder) : 0);
    }

    /**
     * Updates appointment in database
     * @param appointmentDetail Appointment detail model
     * @return Response model of appointment detail model
     */
    @Transactional
    public ResponseModel<AppointmentDetail> updateAppointmentDetail(AppointmentDetail appointmentDetail){
        ResponseModel<AppointmentDetail> responseModel=new ResponseModel<>();
        try{
            Appointment appointmentFromDb=this.appointmentRepository.getAppointmentById(appointmentDetail.getId());
            appointmentFromDb.setFeedbackMessage(appointmentDetail.getFeedbackMessage());
            appointmentFromDb.setRating(appointmentDetail.getRating());
            appointmentFromDb.setUpdatedAt(Timestamp.from(Instant.now()));
            var result=this.appointmentRepository.save(appointmentFromDb);
            responseModel.responseData=this.commonService.getAppointmentDetailModelFromAppointment(result);
            responseModel.message= "Feedback saved successfully";
            responseModel.isSuccess=true;
        }
        catch (Exception exception){
            responseModel.message=exception.getMessage();
        }
        return responseModel;
    }
}

