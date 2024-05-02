package org.asdc.medhub.Service;
import org.asdc.medhub.Repository.UserRepository;
import org.asdc.medhub.Service.Interface.ICommonService;
import org.asdc.medhub.Utility.Enums.AdminVerificationStatus;
import org.asdc.medhub.Utility.Enums.UserRole;
import org.asdc.medhub.Utility.Model.DatabaseModels.Doctor;
import org.asdc.medhub.Utility.Model.DatabaseModels.Pharmacist;
import org.asdc.medhub.Utility.Model.ResponseModel;
import org.asdc.medhub.Utility.Model.ResponseModels.DoctorDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.PharmacistDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.asdc.medhub.Utility.Model.DatabaseModels.User;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminServiceTest {
    static User user1;
    static User user2;
    static String username1;
    static String username2;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private CommonService commonService;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);

        username1 = "xyz@gmail.com";
        username2 = "abc@gmail.com";

        user1 = new User();
        user1.setUserRole(UserRole.DOCTOR);
        user1.setAdminVerificationStatus(AdminVerificationStatus.PENDING);
        user1.setId(1);
        user1.setUsername(username1);

        user2 = new User();
        user2.setUserRole(UserRole.DOCTOR);
        user2.setAdminVerificationStatus(AdminVerificationStatus.PENDING);
        user2.setId(2);
        user2.setUsername(username2);
    }

    @Test
    public void testGetAdminUnverifiedDoctorsSuccessful() {
        // Create mock data
        Doctor doctor1 = new Doctor();
        user1.setDoctor(doctor1);
        doctor1.setUser(user1);

        Doctor doctor2 = new Doctor();
        user2.setDoctor(doctor2);
        doctor2.setUser(user2);

        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(user1);
        mockUsers.add(user2);

        // Mock the findUsersByUserRoleAndAdminVerificationStatus method
        when(userRepository.findUsersByUserRoleAndAdminVerificationStatus(UserRole.DOCTOR, AdminVerificationStatus.PENDING))
                .thenReturn(mockUsers);

        // Call the method under test
        ResponseModel<List<DoctorDetail>> response = adminService.getAdminUnverifiedDoctors();

        // Check assertions
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals(2, response.getResponseData().size());
        assertEquals("Found: 2 unverified doctors.", response.getMessage());
    }

    @Test
    public void testGetAdminUnverifiedDoctorsError() {
        // Use mock data but do not set the doctor
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(user1);

        // Mock the findUsersByUserRoleAndAdminVerificationStatus method
        when(userRepository.findUsersByUserRoleAndAdminVerificationStatus(UserRole.DOCTOR, AdminVerificationStatus.PENDING))
                .thenReturn(mockUsers);

        ResponseModel<List<DoctorDetail>> response = adminService.getAdminUnverifiedDoctors();

        // Check assertions
        assertFalse(response.isSuccess);
    }

    @Test
    public void testGetAdminUnverifiedDoctorIndividualSuccessful() {
        // Create mock data
        Doctor doctor1 = new Doctor();
        user1.setDoctor(doctor1);
        doctor1.setUser(user1);

        // Mock the findByUsername method
        when(userRepository.findUserByUsername(username1)).thenReturn(user1);

        // Call the method
        ResponseModel<DoctorDetail> response = adminService.getAdminUnverifiedDoctorIndividual(username1);

        // Check assertions
        assertEquals("Doctor details retrieved successfully", response.getMessage());
        assertTrue(response.isSuccess);
    }

    @Test
    public void testGetAdminUnverifiedDoctorIndividualNoDoctorFound() {
        // Use mock data but do not set the doctor

        // Mock the findByUsername method
        when(userRepository.findUserByUsername(username1)).thenReturn(user1);

        // Call the method
        ResponseModel<DoctorDetail> response = adminService.getAdminUnverifiedDoctorIndividual(username1);

        // Check assertions
        assertEquals("No doctor found for the provided email", response.getMessage());
        assertFalse(response.isSuccess);
    }

    @Test
    public void testGetAdminUnverifiedDoctorIndividualError() {
        // Mock the findByUsername method to return null
        when(userRepository.findUserByUsername(username1)).thenReturn(null);

        // Call the method
        ResponseModel<DoctorDetail> response = adminService.getAdminUnverifiedDoctorIndividual(username1);

        // Check assertions
        assertFalse(response.isSuccess);
    }

    @Test
    public void testGetAdminUnverifiedPharmacistsSuccessful() {
        // Create mock data
        Pharmacist pharmacist1 = new Pharmacist();
        user1.setPharmacist(pharmacist1);
        pharmacist1.setUser(user1);

        Pharmacist pharmacist2 = new Pharmacist();
        user2.setPharmacist(pharmacist2);
        pharmacist2.setUser(user2);

        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(user1);
        mockUsers.add(user2);

        // Mock the findUsersByUserRoleAndAdminVerificationStatus method
        when(userRepository.findUsersByUserRoleAndAdminVerificationStatus(UserRole.PHARMACIST, AdminVerificationStatus.PENDING))
                .thenReturn(mockUsers);

        // Call the method under test
        ResponseModel<List<PharmacistDetail>> response = adminService.getAdminUnverifiedPharmacists();

        // Check assertions
        assertTrue(response.isSuccess());
        assertEquals(2, response.getResponseData().size());
        assertEquals("Found: 2 unverified doctors.", response.getMessage());
    }

    @Test
    public void testGetAdminUnverifiedPharmacistsError() {
        // Use mock data but do not set the pharmacist
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(user1);

        // Mock the findUsersByUserRoleAndAdminVerificationStatus method
        when(userRepository.findUsersByUserRoleAndAdminVerificationStatus(UserRole.PHARMACIST, AdminVerificationStatus.PENDING))
                .thenReturn(mockUsers);

        // Call the method under test
        ResponseModel<List<PharmacistDetail>> response = adminService.getAdminUnverifiedPharmacists();

        // Check assertions
        assertFalse(response.isSuccess);
    }

    @Test
    public void testGetAdminUnverifiedPharmacistIndividualSuccessful() {
        // Create mock data
        Pharmacist pharmacist1 = new Pharmacist();
        user1.setPharmacist(pharmacist1);
        pharmacist1.setUser(user1);

        // Mock the findByUsername method
        when(userRepository.findUserByUsername(username1)).thenReturn(user1);

        // Call the method
        ResponseModel<PharmacistDetail> response = adminService.getAdminUnverifiedPharmacistIndividual(username1);

        // Check assertions
        assertEquals("Pharmacist details retrieved successfully", response.getMessage());
        assertTrue(response.isSuccess);
    }

    @Test
    public void testGetAdminUnverifiedPharmacistIndividualError() {
        // Mock the findByUsername method to return null
        when(userRepository.findUserByUsername(username1)).thenReturn(null);

        // Call the method
        ResponseModel<PharmacistDetail> response = adminService.getAdminUnverifiedPharmacistIndividual(username1);

        // Check assertions
        assertFalse(response.isSuccess);
    }

    @Test
    public void testApproveUserByAdminSuccessful(){
        // Create mock data
        Doctor doctor1 = new Doctor();
        user1.setDoctor(doctor1);
        doctor1.setUser(user1);

        // Mock the findUsersByUserRoleAndAdminVerificationStatus method
        when(userRepository.findUserByUsername(username1)).thenReturn(user1);

        // Call the method
        ResponseModel<String> response = adminService.approveUserByAdmin(username1);

        // Check assertions
        assertEquals("User is now admin verified!", response.getMessage());
        assertEquals(AdminVerificationStatus.VERIFIED, user1.getAdminVerificationStatus());
        assertTrue(response.isSuccess);
        verify(userRepository, times(1)).save(user1);
        verify(emailService, times(1)).sendEmail(eq(username1), anyString(), anyString());

    }

    @Test
    public void testRejectUserByAdminSuccessful(){
        // Create mock data
        Doctor doctor1 = new Doctor();
        user1.setDoctor(doctor1);
        doctor1.setUser(user1);

        // Mock the findUsersByUserRoleAndAdminVerificationStatus method
        when(userRepository.findUserByUsername(username1)).thenReturn(user1);

        // Call the method
        ResponseModel<String> response = adminService.rejectUserByAdmin(username1);

        // Check assertions
        assertEquals("User is rejected by the admin.", response.getMessage());
        assertEquals(AdminVerificationStatus.REJECTED, user1.getAdminVerificationStatus());
        assertTrue(response.isSuccess);
        verify(userRepository, times(1)).save(user1);
        verify(emailService, times(1)).sendEmail(eq(username1), anyString(), anyString());

    }

}

