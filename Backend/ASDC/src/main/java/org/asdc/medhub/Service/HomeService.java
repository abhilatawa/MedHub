package org.asdc.medhub.Service;

import org.asdc.medhub.Utility.Model.ResponseModel;
import org.asdc.medhub.Service.Interface.IHomeService;
import org.springframework.stereotype.Service;

/**
 * HomeService class containing supporting methods
 */
@Service
public class HomeService implements IHomeService {

    //region PUBLIC METHODS
    /**
     * Generates the test object with data
     * @return ResponseModel<String> with data
     */
    public ResponseModel<String> getTestObject(){
        var x=new ResponseModel<String>();
        x.responseData="Its working fine";
        x.message="Success";
        x.isSuccess=true;
        return x;
    }


    //endregion
}
