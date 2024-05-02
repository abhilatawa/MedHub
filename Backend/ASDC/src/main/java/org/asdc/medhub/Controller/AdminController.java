package org.asdc.medhub.Controller;

import org.asdc.medhub.Service.Interface.IAdminService;
import org.asdc.medhub.Utility.Constant.ProjectConstants;
import org.asdc.medhub.Utility.Model.ResponseModel;
import org.asdc.medhub.Utility.Model.ResponseModels.DoctorDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.PharmacistDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller for handling requests related to admin.
 */
@Secured("ADMIN")
@RestController
@RequestMapping(ProjectConstants.Routes.Admin.mainRoute)
public class AdminController {

    /**
     * Instance of admin service
     */
    private final IAdminService adminService;

    /**
     * Constructor for initializing the AdminController with an IAdminService instance.
     * @param adminService The service responsible for handling admin-related tasks.
     */
    @Autowired
    public AdminController(IAdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * Endpoint for fetching unverified doctors.
     * @return ResponseEntity containing the response model with unverified doctors' data.
     */
    @GetMapping(ProjectConstants.Routes.Admin.getUnverifiedDoctors)
    public ResponseEntity<ResponseModel<List<DoctorDetail>>> getAdminUnverifiedDoctors() {
        return ResponseEntity.ok(this.adminService.getAdminUnverifiedDoctors());
    }

    /**
     * Endpoint for fetching details of an individual unverified doctor.
     * @param requestBody A map containing the email of the doctor whose details are requested.
     * @return ResponseEntity containing the response model with the details of the requested doctor.
     */
    @PostMapping(ProjectConstants.Routes.Admin.getAdminUnverifiedDoctorDetails)
    public ResponseEntity<ResponseModel<DoctorDetail>> getAdminUnverifiedDoctorDetails(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        return ResponseEntity.ok(this.adminService.getAdminUnverifiedDoctorIndividual(email));
    }

    /**
     * Endpoint for fetching unverified pharmacists.
     * @return ResponseEntity containing the response model with unverified pharmacists' data.
     */
    @GetMapping(ProjectConstants.Routes.Admin.getAdminUnverifiedPharmacists)
    public ResponseEntity<ResponseModel<List<PharmacistDetail>>> getAdminUnverifiedPharmacists() {
        return ResponseEntity.ok(this.adminService.getAdminUnverifiedPharmacists());
    }

    /**
     * Endpoint for fetching details of an individual unverified pharmacist.
     * @param requestBody A map containing the email of the pharmacist whose details are requested.
     * @return ResponseEntity containing the response model with the details of the requested pharmacist.
     */
    @PostMapping(ProjectConstants.Routes.Admin.getAdminUnverifiedPharmacistDetails)
    public ResponseEntity<ResponseModel<PharmacistDetail>> getAdminUnverifiedPharmacistDetails(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        return ResponseEntity.ok(this.adminService.getAdminUnverifiedPharmacistIndividual(email));
    }

    /**
     * Endpoint for approving users by admin.
     * @param requestBody The request body containing the email of the user to be approved.
     * @return ResponseEntity containing the response model with the approval status message.
     */
    @PostMapping(ProjectConstants.Routes.Admin.approveUser)
    public ResponseEntity<ResponseModel<String>> approveUserByAdmin(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        return ResponseEntity.ok(this.adminService.approveUserByAdmin(email));
    }

    /**
     * Endpoint for rejecting users by admin.
     * @param requestBody The request body containing the email of the user to be rejected.
     * @return ResponseEntity containing the response model with the rejection status message.
     */
    @PostMapping(ProjectConstants.Routes.Admin.rejectUser)
    public ResponseEntity<ResponseModel<String>> rejectUserByAdmin(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        return ResponseEntity.ok(this.adminService.rejectUserByAdmin(email));
    }
}
