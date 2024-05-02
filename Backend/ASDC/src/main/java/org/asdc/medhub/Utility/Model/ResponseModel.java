package org.asdc.medhub.Utility.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Structured common response model
 * @param <T> Type of object to send in response
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseModel<T> {

    /**
     * Generic response data field
     */
    public T responseData;

    /**
     * Response message
     */
    public String message;

    /**
     * Indicates the status of operation
     */
    public boolean isSuccess;
}
