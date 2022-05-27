package com.grande.taxiapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<Object> handleCarNotFound(CarNotFoundException e){
        return new ResponseEntity<>("Car with given parameters doesn't exist", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DriverNotFoundException.class)
    public ResponseEntity<Object> handleDriverNotFound(DriverNotFoundException e){
        return new ResponseEntity<>("Driver with given parameters doesn't exist",HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Object> handleCustomerNotFound(CustomerNotFoundException e){
        return new ResponseEntity<>("Customer with given parameters doesn't exist",HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(OrderTaxiNotFoundException.class)
    public ResponseEntity<Object> handleOrderTaxiNotFound(OrderTaxiNotFoundException e){
        return new ResponseEntity<>("Order with given parameters doesn't exist",HttpStatus.BAD_REQUEST);
    }
}
