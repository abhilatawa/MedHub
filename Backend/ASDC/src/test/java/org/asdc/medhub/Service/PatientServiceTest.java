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
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class PatientServiceTest {
    public UserRepository userRepository;

    public DoctorRepository doctorRepository;

    public AppointmentRepository appointmentRepository;

    public MedicalSpecializationRepository medicalSpecializationRepository;

    public PatientRepository patientRepository;

    public IPatientService patientService;

    public ICommonService commonService;

    public AppointmentDetail appointmentDetail;

    
    @Before
    public void InitializeTest(){
        this.userRepository= Mockito.mock(UserRepository.class);
        this.doctorRepository=Mockito.mock(DoctorRepository.class);
        this.appointmentRepository=Mockito.mock(AppointmentRepository.class);
        this.patientRepository=Mockito.mock(PatientRepository.class);
        this.medicalSpecializationRepository=Mockito.mock(MedicalSpecializationRepository.class);
        this.commonService=Mockito.mock(CommonService.class);

        this.patientService=new PatientService(
                this.userRepository,
                this.patientRepository,
                this.doctorRepository,
                this.medicalSpecializationRepository,
                this.appointmentRepository,
                this.commonService);

        appointmentDetail = new AppointmentDetail();
        appointmentDetail.setId(2);
        appointmentDetail.setFeedbackMessage("Test feedback message");
        appointmentDetail.setRating(5);

    }

    @After
    public void FinalizeTest(){
        this.userRepository= null;
        this.doctorRepository=null;
        this.appointmentRepository=null;
        this.patientRepository=null;
        this.medicalSpecializationRepository=null;
        this.commonService=null;
        this.patientService=null;
    }
    @Test
    public void bookAppointmentTestSuccess() {
        // Arrange
        AppointmentBookingModel appointmentBookingModel = new AppointmentBookingModel();
        appointmentBookingModel.setTimeSlot("09:00 to 10:00");
        appointmentBookingModel.setAppointmentDate(LocalDate.now());

        Patient patient = new Patient();
        patient.setId(1); // Assuming patient ID is 1 for the test
        User user=new User();
        user.setPatient(patient);

        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        when(appointmentRepository.save(any())).thenReturn(new Appointment());
        when(doctorRepository.findById(anyInt())).thenReturn(Optional.of(new Doctor()));

        // Act
        ResponseModel<AppointmentBookingModel> responseModel = this.patientService.bookAppointment(appointmentBookingModel,user);

        // Assert
        assertTrue(responseModel.isSuccess());
    }

    @Test
    public void bookAppointmentTestFail() {
        // Arrange
        AppointmentBookingModel appointmentBookingModel = new AppointmentBookingModel();
        appointmentBookingModel.setTimeSlot("09:00 to 10:00");

        Patient patient = new Patient();
        patient.setId(1); // Assuming patient ID is 1 for the test
        User user=new User();
        user.setPatient(patient);

        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        when(appointmentRepository.save(any())).thenReturn(new Appointment());

        // Act
        ResponseModel<AppointmentBookingModel> responseModel = patientService.bookAppointment(appointmentBookingModel,new User());

        // Assert
        assertFalse(responseModel.isSuccess());
    }

    @Test
    public void getDoctorAvailabilityTimeSlotsTestSuccess() {
        // Arrange
        int doctorId = 1;
        Doctor doctor = new Doctor();
        doctor.setMonday(true);
        doctor.setStartTime(Timestamp.from(Instant.now()));
        doctor.setEndTime(Timestamp.from(Instant.now()));

        when(this.doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(this.appointmentRepository.getAppointmentsByDoctorIdAndStatusAndWeekDay(anyInt(), any(), any()))
                .thenReturn(new ArrayList<>());

        // Act
        ResponseModel<HashMap<String, List<String>>> response = this.patientService.getDoctorAvailabilityTimeSlots(doctorId);

        // Assert
        assertTrue(response.isSuccess());
    }
    @Test
    public void getDoctorAvailabilityTimeSlotsTestWhenAvailabilityIsNull() {
        // Arrange
        int doctorId = 1;
        Doctor doctor = new Doctor();
        doctor.setMonday(true);
        doctor.setStartTime(null);
        doctor.setEndTime(null);

        when(this.doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(this.appointmentRepository.getAppointmentsByDoctorIdAndStatusAndWeekDay(anyInt(), any(), any()))
                .thenReturn(new ArrayList<>());

        // Act
        ResponseModel<HashMap<String, List<String>>> response = this.patientService.getDoctorAvailabilityTimeSlots(doctorId);

        // Assert
        assertTrue(response.isSuccess());
        assertEquals(response.responseData.size(),1);
    }


    @Test
    public void getDoctorAvailabilityTimeSlotsTestFail() {
        // Arrange
        int doctorId = 1;
        Doctor doctor = new Doctor();

        when(this.doctorRepository.findById(doctorId)).thenReturn(null);
        when(this.appointmentRepository.getAppointmentsByDoctorIdAndStatusAndWeekDay(anyInt(), any(AppointmentStatus.class), any(DayOfWeek.class)))
                .thenReturn(new ArrayList<>());

        // Act
        ResponseModel<HashMap<String, List<String>>> response = this.patientService.getDoctorAvailabilityTimeSlots(doctorId);

        // Assert
        assertFalse(response.isSuccess());
    }

    @Test
    public void getDoctorDetailTestSuccess() {
        // Arrange
        int doctorId = 1;
        Doctor doctor = new Doctor();
        when(this.doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));

        // Act
        ResponseModel<DoctorDetail> response = this.patientService.getDoctorDetails(doctorId);

        // Assert
        assertTrue(response.isSuccess());
    }

    @Test
    public void getDoctorDetailTestFail() {
        // Arrange
        int doctorId = 1;
        Doctor doctor=new Doctor();
        when(this.doctorRepository.findById(Mockito.anyInt())).thenReturn(null);
        when(this.commonService.convertDoctorToDoctorDetail(Mockito.any(Doctor.class))).thenReturn(new DoctorDetail());

        // Act
        ResponseModel<DoctorDetail> response = this.patientService.getDoctorDetails(doctorId);

        // Assert
        assertFalse(response.isSuccess());
    }

    @Test
    public void  getAppointmentsTestSuccess(){
        //Arrange
        Patient patient=new Patient();
        patient.setId(22);
        User user=new User();
        user.setPatient(patient);

        when(this.appointmentRepository.getAppointmentsByPatientId(Mockito.anyInt())).thenReturn(new ArrayList<>());

        //Action
        var result=this.patientService.getAppointments(user);

        //Assert
        assertTrue(result.isSuccess);
    }

    @Test
    public void  getAppointmentsTestFail(){
        //Arrange
        User user=new User();
        when(this.appointmentRepository.getAppointmentsByPatientId(Mockito.anyInt())).thenReturn(new ArrayList<>());

        //Action
        var result=this.patientService.getAppointments(user);

        //Assert
        assertFalse(result.isSuccess);
    }

    @Test
    public void getPatientProfileTestWhenPatientIsNotNull(){
        //Arrange
        Patient patient=new Patient();
        User user=new User();
        user.setPatient(patient);

        when(this.userRepository.findUserByUsername(Mockito.anyString())).thenReturn(user);

        //Action
        var result=this.patientService.getPatientProfile("Any string here");

        //Assert
        assertTrue(result.isSuccess);
    }

    @Test
    public void getPatientProfileTestWhenPatientIsNull(){
        //Arrange
        User user=new User();
        user.setPatient(null);

        when(this.userRepository.findUserByUsername(Mockito.anyString())).thenReturn(user);

        //Action
        var result=this.patientService.getPatientProfile("Any string here");

        //Assert
        assertFalse(result.isSuccess);
        Assert.assertEquals(result.message,"No patient found for the provided email");
    }

    @Test
    public void editPatientProfileTestSuccess(){
        //Arrange
        Patient patient=new Patient();
        User user=new User();
        user.setPatient(patient);

        PatientDetail patientDetail=new PatientDetail();
        patientDetail.setFirstName("First name");
        patientDetail.setLastName("Last name");

        when(this.userRepository.findUserByUsername(Mockito.anyString())).thenReturn(user);
        when(this.patientRepository.save(Mockito.any(Patient.class))).thenReturn(null);

        //Action
        var result=this.patientService.editPatientProfile("Any string here",patientDetail);

        //Assert
        System.out.println(result.isSuccess+"================");
        assertTrue(result.isSuccess);
    }

    @Test
    public void editPatientProfileTestPatientIsNull(){
        //Arrange
        User user=new User();
        user.setPatient(null);

        PatientDetail patientDetail=new PatientDetail();
        patientDetail.setFirstName("First name");
        patientDetail.setLastName("Last name");

        when(this.userRepository.findUserByUsername(Mockito.anyString())).thenReturn(user);
        when(this.patientRepository.save(Mockito.any(Patient.class)));

        //Action
        var result=this.patientService.editPatientProfile("Any string here",patientDetail);

        //Assert
        assertFalse(result.isSuccess);
    }

    @Test
    public void getAllFilteredDoctorListTestWhenFilterNotEmpty(){
        //Arrange
        DoctorFilterModel doctorFilterModel=new DoctorFilterModel();
        doctorFilterModel.setSpecializations(List.of("Specialization 1"));

        when(this.doctorRepository.getVerifiedDoctorsBySpecializationAvailableIn(anyList())).thenReturn(List.of(new Doctor()));

        //Action
        var result=this.patientService.getAllFilteredDoctorList(doctorFilterModel);

        //Assert
        assertTrue(result.isSuccess);
        Assert.assertEquals(result.responseData.size(),1);
    }

    @Test
    public void getAllFilteredDoctorListTestWhenFilterEmpty(){
        //Arrange
        DoctorFilterModel doctorFilterModel=new DoctorFilterModel();
        doctorFilterModel.setSpecializations(new ArrayList<>());

        when(this.doctorRepository.getAllVerifiedDoctors()).thenReturn(List.of(new Doctor()));

        //Action
        var result=this.patientService.getAllFilteredDoctorList(doctorFilterModel);

        //Assert
        assertTrue(result.isSuccess);
    }

    @Test
    public void getAllFilteredDoctorListTestFail(){
        //Arrange
        when(this.doctorRepository.getAllVerifiedDoctors()).thenReturn(List.of(new Doctor()));

        //Action
        var result=this.patientService.getAllFilteredDoctorList(null);

        //Assert
        assertFalse(result.isSuccess);
    }

    @Test
    public void getMedicalSpecializationByNameTestSuccess(){
        //Arrange
        when(this.medicalSpecializationRepository
                .getMedicalSpecializationsBySpecializationContainingIgnoreCase(anyString()))
                .thenReturn(new ArrayList<>());
        //Action
        var result=this.patientService.getMedicalSpecializationByName("Search String");

        //Assert
        assertTrue(result.isSuccess);
    }

    @Test
    public void getMedicalSpecializationByNameTestFail(){
        //Arrange
        List<MedicalSpecialization> medicalSpecializationList=new ArrayList<>();
        medicalSpecializationList.add(null);

        when(this.medicalSpecializationRepository
                .getMedicalSpecializationsBySpecializationContainingIgnoreCase(anyString()))
                .thenReturn(null);
        //Action
        var result=this.patientService.getMedicalSpecializationByName("Search String");

        //Assert
        assertFalse(result.isSuccess);
    }

    /* @Test
    public void getListOf30minutesTimeSlotsTestWhenNoConflict() throws Exception{
        //Arrange
        long startTime=LocalDateTime.of(LocalDate.now(), LocalTime.of(12,0)).toEpochSecond(ZoneOffset.UTC)*1000;
        long endTime=LocalDateTime.of(LocalDate.now(), LocalTime.of(13,1)).toEpochSecond(ZoneOffset.UTC)*1000;;

        Appointment appointment=new Appointment();
        appointment.setStartTime(Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(1,0))));
        appointment.setEndTime(Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(1,0))));
        List<Appointment> bookedAppointments=new ArrayList<>();

        Method privateMethod = this.patientService.getClass().getDeclaredMethod("getListOf30minutesTimeSlots", long.class,long.class,List.class);
        privateMethod.setAccessible(true); // Allows invoking private method

        //Action
        var result = (List<String>) privateMethod.invoke(this.patientService, startTime,endTime,bookedAppointments);

        //Assert
        assertEquals(2, result.size());
        assertTrue(result.contains("09:00 to 09:30"));
        assertTrue(result.contains("09:30 to 10:00"));
    } */
    /*
    @Test
    public void getListOf30minutesTimeSlotsTestWhenStartTimeIsMoreThenEndTime() throws Exception{
        //Arrange
        long startTime=LocalDateTime.of(LocalDate.now(), LocalTime.of(12,0)).toEpochSecond(ZoneOffset.UTC)*1000;
        long endTime=LocalDateTime.of(LocalDate.now(), LocalTime.of(12,59)).toEpochSecond(ZoneOffset.UTC)*1000;;

        Appointment appointment=new Appointment();
        appointment.setStartTime(Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(1,0))));
        appointment.setEndTime(Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(1,0))));
        List<Appointment> bookedAppointments=new ArrayList<>();
        bookedAppointments.add(appointment);

        Method privateMethod = this.patientService.getClass().getDeclaredMethod("getListOf30minutesTimeSlots", long.class,long.class,List.class);
        privateMethod.setAccessible(true); // Allows invoking private method

        //Action
        var result = (List<String>) privateMethod.invoke(this.patientService, startTime,endTime,bookedAppointments);

        //Assert
        assertEquals(1, result.size());
        assertTrue(result.contains("09:00 to 09:30"));
    } */

    @Test
    public void testUpdateAppointmentDetail_Success() {
        Appointment appointmentFromDb = new Appointment(); // Create a mock appointment object
        when(appointmentRepository.getAppointmentById(2)).thenReturn(appointmentFromDb); // Mock the repository method

        ResponseModel<AppointmentDetail> responseModel = patientService.updateAppointmentDetail(appointmentDetail);
        System.out.println("Response Model: " + responseModel);
        System.out.println("Appointment Detail Model: " + appointmentDetail);
        assertTrue(responseModel.isSuccess()); // Verify success flag
        assertEquals("Feedback saved successfully", responseModel.getMessage()); // Verify success message

        // Verify that the appointment object's fields are updated correctly
        assertEquals(appointmentDetail.getFeedbackMessage(), appointmentFromDb.getFeedbackMessage());
        assertEquals(appointmentDetail.getRating(), appointmentFromDb.getRating());
    }


    @Test
    public void testUpdateAppointmentDetail_Failure() {
        when(appointmentRepository.getAppointmentById(1)).thenThrow(new RuntimeException("Database error")); // Mock repository method to throw an exception

        ResponseModel<AppointmentDetail> responseModel = patientService.updateAppointmentDetail(appointmentDetail);

        assertFalse(responseModel.isSuccess()); // Verify failure flag
    }

}
