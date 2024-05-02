package org.asdc.medhub.Service;

import org.asdc.medhub.Configuration.CustomConfigurations;
import org.asdc.medhub.Repository.AppointmentRepository;
import org.asdc.medhub.Repository.PharmacistRepository;
import org.asdc.medhub.Repository.SpecializationOfDoctorRepository;
import org.asdc.medhub.Repository.UserRepository;
import org.asdc.medhub.Service.Interface.ICommonService;
import org.asdc.medhub.Utility.Model.DatabaseModels.Appointment;
import org.asdc.medhub.Utility.Model.DatabaseModels.Doctor;
import org.asdc.medhub.Utility.Model.ResponseModel;
import org.asdc.medhub.Utility.Model.DatabaseModels.Pharmacist;
import org.asdc.medhub.Utility.Model.ResponseModels.AppointmentDetail;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.*;

public class DoctorServiceTest {

    @InjectMocks
    private DoctorService doctorService;

    public AppointmentRepository appointmentRepositoryMock;
    public ICommonService commonServiceMock;
    public CustomConfigurations customConfigurationsMock;
    public UserRepository userRepositoryMock;
    public SpecializationOfDoctorRepository specializationOfDoctorRepositoryMock;
    public PharmacistRepository pharmacistRepository;

    @Before
    public void InitializeTest(){
        this.appointmentRepositoryMock= Mockito.mock(AppointmentRepository.class);
        this.commonServiceMock=Mockito.mock(ICommonService.class);
        this.customConfigurationsMock=Mockito.mock(CustomConfigurations.class);
        this.userRepositoryMock=Mockito.mock(UserRepository.class);
        this.specializationOfDoctorRepositoryMock=Mockito.mock(SpecializationOfDoctorRepository.class);
        this.pharmacistRepository=Mockito.mock(PharmacistRepository.class);

        this.doctorService=new DoctorService(
                this.userRepositoryMock,
                this.specializationOfDoctorRepositoryMock,
                this.customConfigurationsMock,
                this.commonServiceMock,
                this.appointmentRepositoryMock,
                this.pharmacistRepository
        );
    }

    @Test
    public void  getAppointmentsTestSuccess(){
        //Arrange
        Mockito.when(appointmentRepositoryMock.getAppointmentsByDoctorIdAndStatusIsIn(Mockito.anyInt(),Mockito.anyList()))
                .thenReturn(List.of(new Appointment()));

        //Action
        var result=this.doctorService.getAppointments(true,1);

        //Assert
        Assert.assertTrue(result.isSuccess);
    }

    @Test
    public void  getAppointmentsTestFail(){
        //Arrange
        Mockito.when(appointmentRepositoryMock.getAppointmentsByDoctorIdAndStatusIsIn(Mockito.anyInt(),Mockito.anyList()))
                .thenReturn(null);

        //Action
        var result=this.doctorService.getAppointments(true,1);

        //Assert
        Assert.assertFalse(result.isSuccess);
    }

    @Test
    public void getFilteredAndVerifiedPharmacistListTestSuccess(){
        //Arrange
        List<Pharmacist> pharmacistList=new ArrayList<>();
        pharmacistList.add(new Pharmacist());
        pharmacistList.add(new Pharmacist());
        Mockito.when(this.pharmacistRepository.getPharmacistsByPharmacyNameContainingAndUser_AdminVerificationStatus(Mockito.anyString(),Mockito.any()))
                .thenReturn(pharmacistList);

        //Action
        var result=this.doctorService.getFilteredAndVerifiedPharmacistList("search string");

        //Assert
        Assert.assertTrue(result.isSuccess);
        Assert.assertEquals(result.responseData.size(),2);
    }

    @Test
    public void getFilteredAndVerifiedPharmacistListTestFail(){
        //Arrange
        Mockito.when(this.pharmacistRepository.getPharmacistsByPharmacyNameContainingAndUser_AdminVerificationStatus(Mockito.anyString(),Mockito.any()))
                .thenReturn(null);

        //Action
        var result=this.doctorService.getFilteredAndVerifiedPharmacistList("search string");

        //Assert
        Assert.assertFalse(result.isSuccess);
    }

    @Test
    public void updateAppointmentDetailTestSuccess(){
        //Arrange
        Appointment appointment=new Appointment();
        Mockito.when(this.appointmentRepositoryMock.getAppointmentById(Mockito.anyInt()))
                .thenReturn(appointment);

        Pharmacist pharmacist=new Pharmacist();
        Mockito.when(this.pharmacistRepository.getPharmacistById(Mockito.anyInt()))
                .thenReturn(pharmacist);

        AppointmentDetail appointmentDetail=new AppointmentDetail();
        appointmentDetail.setId(1);

        //Action
        var result=this.doctorService.updateAppointmentDetail(appointmentDetail);

        //Assert
        Assert.assertTrue(result.isSuccess);
    }

    @Test
    public void updateAppointmentDetailTestFail(){
        //Arrange
        Appointment appointment=new Appointment();
        Mockito.when(this.appointmentRepositoryMock.getAppointmentById(Mockito.anyInt()))
                .thenReturn(null);

        Pharmacist pharmacist=new Pharmacist();
        Mockito.when(this.pharmacistRepository.getPharmacistById(Mockito.anyInt()))
                .thenReturn(null);

        AppointmentDetail appointmentDetail=new AppointmentDetail();
        //Action
        var result=this.doctorService.updateAppointmentDetail(appointmentDetail);

        //Assert
        Assert.assertFalse(result.isSuccess);
    }

    @Test
    public void getDoctorFeedbackDetailsTestSuccess() {
        // Arrange

        Doctor doctor = new Doctor();
        doctor.setId(1);

// Create the Appointment object and set its Doctor
        List<Appointment> appointments= new ArrayList<>();
        Appointment appointment1 = new Appointment();
        appointment1.setDoctor(doctor);
        appointment1.setFeedbackMessage("Feedback 1");
        appointment1.setRating(4);
        appointments.add(appointment1);

// Create another Appointment object and set its Doctor
        Appointment appointment2 = new Appointment();
        appointment2.setDoctor(doctor);
        appointment2.setFeedbackMessage("Feedback 2");
        appointment2.setRating(5);
        appointments.add(appointment2);


        Mockito.when(this.appointmentRepositoryMock.getAppointmentsByDoctorId(Mockito.anyInt())).thenReturn(appointments);

        // Action
        ResponseModel<Map<String, Object>> result = doctorService.getDoctorFeedbackDetails(1);

        // Assert
        assertTrue(result.isSuccess());
        assertNotNull(result.getResponseData());
        assertEquals(4.5, result.getResponseData().get("averageRating"));
        var feedbackDetails = (List<Map<String, Object>>) result.getResponseData().get("feedbackDetails");
        assertEquals(2, feedbackDetails.size());
        assertEquals("Feedback 1", feedbackDetails.get(0).get("feedbackMessage"));
        assertEquals(4, feedbackDetails.get(0).get("rating"));
        assertEquals("Feedback 2", feedbackDetails.get(1).get("feedbackMessage"));
        assertEquals(5, feedbackDetails.get(1).get("rating"));
    }
}

