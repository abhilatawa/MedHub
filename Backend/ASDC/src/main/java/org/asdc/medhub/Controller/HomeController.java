package org.asdc.medhub.Controller;

import org.asdc.medhub.Utility.Constant.ProjectConstants;
import org.asdc.medhub.Utility.Model.ResponseModel;
import org.asdc.medhub.Service.Interface.IHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Home controller class for first point of contact operations
 */
@RestController
@RequestMapping(ProjectConstants.Routes.Home.mainRoute)
public class HomeController {

    /**
     * Home service instance
     */
    private final IHomeService homeService;

    /**
     * Parameterized constructor
     * @param homeService | Service that is injected from beans
     */
    @Autowired
    public HomeController(IHomeService homeService){
        this.homeService=homeService;
    }

    //region PUBLIC METHODS
    /**
     * Sample method for testing
     * @return ResponseModel with test data
     */
    @PostMapping(ProjectConstants.Routes.Home.test)
    public ResponseModel<String> TestMethod(){
        return this.homeService.getTestObject();
    }
    //endregion
}
