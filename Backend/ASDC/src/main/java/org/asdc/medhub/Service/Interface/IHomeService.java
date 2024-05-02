package org.asdc.medhub.Service.Interface;

import org.asdc.medhub.Utility.Model.ResponseModel;

/**
 * Interface for HomeService
 */
public interface IHomeService {

    /**
     * Generates the test object
     * @return ResponseMode<String> object
     */
    ResponseModel<String> getTestObject();
}
