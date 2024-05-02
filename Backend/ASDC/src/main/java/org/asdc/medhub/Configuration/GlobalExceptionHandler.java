package org.asdc.medhub.Configuration;

import org.asdc.medhub.Utility.Model.ResponseModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains global exception handlers
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles global MethodArgumentNotValidException
     * @param exception MethodArgumentNotValidException
     * @return ResponseEntity with field and message of issue
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseModel<Map<String, String>>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception)
    {
        ResponseModel<Map<String, String>> response=new ResponseModel<>();

        //Extracting fields with issue and messages
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            errors.put(((FieldError) error).getField(), error.getDefaultMessage());
        });

        response.message="Some inputs are in invalid format.";
        response.responseData=errors;
        return ResponseEntity.ok(response);
    }
}
