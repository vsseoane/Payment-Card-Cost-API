package com.etraveli.paymentCardsCostTest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<String> handleValidationException(BindException ex) {
        return ResponseEntity.badRequest().body("Validation error: " + ex.getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(CountryCostMatrixNotFoundException.class)
    public ResponseEntity<ErrorObject> handleCountryCostMatrixNotFoundException(CountryCostMatrixNotFoundException ex,
                                                                                WebRequest request) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode((HttpStatus.NOT_FOUND.value()));
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());
        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(PaymentCardCostCalculationUnProcessAbleContentException.class)
    public ResponseEntity<ErrorObject> handlePaymentCardCostCalculationNotFoundException(CountryCostMatrixNotFoundException ex,
                                                                                WebRequest request) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode((HttpStatus.NOT_FOUND.value()));
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());
        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);

    }
}
