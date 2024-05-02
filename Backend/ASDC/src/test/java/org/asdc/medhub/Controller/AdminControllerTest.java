package org.asdc.medhub.Controller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import org.asdc.medhub.Service.Interface.IAdminService;
import org.asdc.medhub.Utility.Model.ResponseModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AdminController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AdminControllerTest {
    @Autowired
    private AdminController adminController;

    @MockBean
    private IAdminService iAdminService;

    /**
     * Method under test: {@link AdminController#getAdminUnverifiedDoctors()}
     */
    @Test
    void testGetAdminUnverifiedDoctors() throws Exception {
        // Arrange
        when(iAdminService.getAdminUnverifiedDoctors()).thenReturn(new ResponseModel<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/dashboard/unverified_doctors");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"responseData\":null,\"message\":null,\"isSuccess\":false,\"success\":false}"));
    }

    /**
     * Method under test: {@link AdminController#getAdminUnverifiedPharmacists()}
     */
    @Test
    void testGetAdminUnverifiedPharmacists() throws Exception {
        // Arrange
        when(iAdminService.getAdminUnverifiedPharmacists()).thenReturn(new ResponseModel<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/admin/dashboard/unverified_pharmacists");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"responseData\":null,\"message\":null,\"isSuccess\":false,\"success\":false}"));
    }

    /**
     * Method under test: {@link AdminController#approveUserByAdmin(Map)}
     */
    @Test
    void testApproveUserByAdmin() throws Exception {
        // Arrange
        when(iAdminService.approveUserByAdmin(Mockito.<String>any())).thenReturn(new ResponseModel<>());

        HashMap<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("email", "foo");
        String content = (new ObjectMapper()).writeValueAsString(stringStringMap);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin/dashboard/approve")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"responseData\":null,\"message\":null,\"isSuccess\":false,\"success\":false}"));
    }

    /**
     * Method under test: {@link AdminController#rejectUserByAdmin(Map)}
     */
    @Test
    void testRejectUserByAdmin() throws Exception {
        // Arrange
        when(iAdminService.rejectUserByAdmin(Mockito.<String>any())).thenReturn(new ResponseModel<>());

        HashMap<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("email", "foo");
        String content = (new ObjectMapper()).writeValueAsString(stringStringMap);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin/dashboard/reject")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"responseData\":null,\"message\":null,\"isSuccess\":false,\"success\":false}"));
    }

    /**
     * Method under test:
     * {@link AdminController#getAdminUnverifiedDoctorDetails(Map)}
     */
    @Test
    void testGetAdminUnverifiedDoctorDetails() throws Exception {
        // Arrange
        when(iAdminService.getAdminUnverifiedDoctorIndividual(Mockito.<String>any())).thenReturn(new ResponseModel<>());

        HashMap<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("email", "foo");
        String content = (new ObjectMapper()).writeValueAsString(stringStringMap);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/admin/dashboard/unverified_doctors/details")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"responseData\":null,\"message\":null,\"isSuccess\":false,\"success\":false}"));
    }

    /**
     * Method under test:
     * {@link AdminController#getAdminUnverifiedPharmacistDetails(Map)}
     */
    @Test
    void testGetAdminUnverifiedPharmacistDetails() throws Exception {
        // Arrange
        when(iAdminService.getAdminUnverifiedPharmacistIndividual(Mockito.<String>any())).thenReturn(new ResponseModel<>());

        HashMap<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("email", "foo");
        String content = (new ObjectMapper()).writeValueAsString(stringStringMap);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/admin/dashboard/unverified_pharmacists/details")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"responseData\":null,\"message\":null,\"isSuccess\":false,\"success\":false}"));
    }
}
