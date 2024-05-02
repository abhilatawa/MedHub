package org.asdc.medhub.Service;

import org.asdc.medhub.Repository.AppointmentRepository;
import org.asdc.medhub.Repository.PharmacistRepository;
import org.asdc.medhub.Repository.UserRepository;
import org.asdc.medhub.Service.Interface.ICommonService;
import org.asdc.medhub.Service.Interface.IPharmacistService;
import org.asdc.medhub.Utility.Model.DatabaseModels.Appointment;
import org.asdc.medhub.Utility.Model.DatabaseModels.Pharmacist;
import org.asdc.medhub.Utility.Model.DatabaseModels.User;
import org.asdc.medhub.Utility.Model.ResponseModels.AppointmentDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.PharmacistDetail;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class PharmacistServiceTest {
    public ICommonService commonServiceMock;

    public PharmacistRepository pharmacistRepositoryMock;

    public IPharmacistService pharmacistService;

    public AppointmentRepository appointmentRepositoryMock;

    public UserRepository userRepository;

    @Before
    public void InitializeTest(){
        this.pharmacistRepositoryMock=Mockito.mock(PharmacistRepository.class);
        this.appointmentRepositoryMock =Mockito.mock(AppointmentRepository.class);
        this.commonServiceMock= Mockito.mock(CommonService.class);
        this.userRepository=Mockito.mock(UserRepository.class);
        this.pharmacistService=new PharmacistService(
                this.pharmacistRepositoryMock,
                this.commonServiceMock,
                this.appointmentRepositoryMock,
                this.userRepository);
    }

    @Test
    public void getPharmacistProfileFromIdTestSuccess(){
        //Arrange
        Pharmacist pharmacist=new Pharmacist();
        when(this.pharmacistRepositoryMock.getPharmacistById(Mockito.anyInt()))
                .thenReturn(pharmacist);
        when(this.commonServiceMock.convertPharmacistToPharmacistDetail(Mockito.any(Pharmacist.class)))
                .thenReturn(new PharmacistDetail());

        //Action
        var result=this.pharmacistService.getPharmacistProfileFromId(1);

        //Assert
        Assert.assertTrue(result.isSuccess);
        Assert.assertNotNull(result.responseData);
    }

    @Test
    public void getPharmacistProfileFromIdTestFail(){
        //Arrange
        when(this.pharmacistRepositoryMock.getPharmacistById(Mockito.anyInt()))
                .thenReturn(null);

        //Action
        var result=this.pharmacistService.getPharmacistProfileFromId(0);

        //Assert
        Assert.assertNull(result.responseData);
    }

    @Test
    public void getAppointmentsFilteredByPatientNameTestSuccess(){
        //Arrange
        List<Appointment> appointmentList=new ArrayList<>();
        appointmentList.add(new Appointment());
        when(this.appointmentRepositoryMock.getAppointmentsByPatient_FirstNameContainingAndPharmacist_Id(Mockito.anyString(),Mockito.anyInt()))
                .thenReturn(appointmentList);
        when(this.commonServiceMock.getAppointmentDetailModelFromAppointment(Mockito.any(Appointment.class)))
                .thenReturn(new AppointmentDetail());

        //Action
        var result=this.pharmacistService.getAppointmentsFilteredByPatientName("search string",0);

        //Assert
        Assert.assertTrue(result.isSuccess);
        Assert.assertEquals(result.responseData.size(),1);
    }

    @Test
    public void getAppointmentsFilteredByPatientNameTestFail(){
        //Arrange
        when(this.appointmentRepositoryMock.getAppointmentsByPatient_FirstNameContainingAndPharmacist_Id(Mockito.anyString(),Mockito.anyInt()))
                .thenReturn(null);

        //Action
        var result=this.pharmacistService.getAppointmentsFilteredByPatientName("search string",0);

        //Assert
        Assert.assertFalse(result.isSuccess);
    }

    @Test
    public void changePasswordTestSuccess(){
        //Arrange
        when(this.userRepository.findUserById(Mockito.anyInt()))
                .thenReturn(new User());
        when(this.userRepository.saveAndFlush(Mockito.any(User.class)))
                .thenReturn(new User());

        //Action
        var result=this.pharmacistService.changePassword(1,"new password string");

        //Assert
        Assert.assertTrue(result.isSuccess);
    }
    @Test
    public void changePasswordTestFail(){
        //Arrange
        when(this.userRepository.findUserById(Mockito.anyInt()))
                .thenReturn(null);

        //Action
        var result=this.pharmacistService.changePassword(1,"new password string");

        //Assert
        Assert.assertFalse(result.isSuccess);
    }

    @Test
    public void updatePharmacistProfileTestSuccess(){
        //Arrange
        when(this.pharmacistRepositoryMock.getPharmacistById(Mockito.anyInt()))
                .thenReturn(new Pharmacist());
        when(this.pharmacistRepositoryMock.save(Mockito.any(Pharmacist.class)))
                .thenReturn(new Pharmacist());
        when(this.commonServiceMock.convertPharmacistToPharmacistDetail(Mockito.any(Pharmacist.class)))
                .thenReturn(new PharmacistDetail());

        //Action
        var result=this.pharmacistService.updatePharmacistProfile(new PharmacistDetail());

        //Assert
        Assert.assertTrue(result.isSuccess);
    }

    @Test
    public void updatePharmacistProfileTestFail(){
        //Arrange
        when(this.pharmacistRepositoryMock.getPharmacistById(Mockito.anyInt()))
                .thenReturn(null);

        //Action
        var result=this.pharmacistService.updatePharmacistProfile(new PharmacistDetail());

        //Assert
        Assert.assertFalse(result.isSuccess);
    }

}
