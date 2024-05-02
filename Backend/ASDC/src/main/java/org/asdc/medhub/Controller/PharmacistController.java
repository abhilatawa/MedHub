package org.asdc.medhub.Controller;

import org.asdc.medhub.Service.Interface.IPharmacistService;
import org.asdc.medhub.Utility.Constant.ProjectConstants;
import org.asdc.medhub.Utility.Model.DatabaseModels.User;
import org.asdc.medhub.Utility.Model.ResponseModel;
import org.asdc.medhub.Utility.Model.ResponseModels.AppointmentDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.PharmacistDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Secured("PHARMACIST")
@RestController
@RequestMapping(ProjectConstants.Routes.Pharmacist.mainRoute)
public class PharmacistController {

    /**
     * Pharmacist service instance
     */
    private final IPharmacistService pharmacistService;

    /**
     * Parameterized controller  with injected beans
     * @param pharmacistService injected pharmacist service bean
     */
    public PharmacistController(IPharmacistService pharmacistService){
        this.pharmacistService=pharmacistService;
    }

    /**
     * Fetches pharmacist profile for current logged in pharmacist
     * @param authentication User principle for current logged in user
     * @return Pharmacist detail model
     */
    @GetMapping(ProjectConstants.Routes.Pharmacist.GetProfile)
    public ResponseEntity<ResponseModel<PharmacistDetail>> getProfile(Authentication authentication){
        User user=(User)authentication.getPrincipal();
        return ResponseEntity.ok(this.pharmacistService.getPharmacistProfileFromId(user.getPharmacist().getId()));
    }

    /**
     * Endpoint for fetching details of an individual patient.
     * @return ResponseEntity containing the response model with the details of the requested patient.
     */
    @PatchMapping(ProjectConstants.Routes.Pharmacist.updateProfile)
    public ResponseEntity<ResponseModel<PharmacistDetail>> updatePatientProfile(@RequestBody PharmacistDetail pharmacistDetail) {
        return ResponseEntity.ok(this.pharmacistService.updatePharmacistProfile(pharmacistDetail));
    }

    /**
     * Fetches appointments/prescription for pharmacist based on patient name
     * @param patientName patient name search string
     * @param authentication Current logged in user principle
     * @return List of appointment detail
     */
    @GetMapping(ProjectConstants.Routes.Pharmacist.GetFilteredAppointments)
    public ResponseEntity<ResponseModel<List<AppointmentDetail>>> getFilteredAppointments(@RequestParam("nameSearchString") String patientName, Authentication authentication){
        User user=(User)authentication.getPrincipal();
        return ResponseEntity.ok(this.pharmacistService.getAppointmentsFilteredByPatientName(patientName,user.getPharmacist().getId()));
    }

    /**
     * Change password of pharmacist
     * @param newPassword new password
     * @return ResponseModel
     */
    @PostMapping(ProjectConstants.Routes.Pharmacist.changePassword)
    public ResponseEntity<ResponseModel<String>> changePassword(@RequestParam("new_password") String newPassword,Authentication authentication){
        User user= (User)authentication.getPrincipal();
        return ResponseEntity.ok(this.pharmacistService.changePassword(user.getId(),newPassword));
    }
}
