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
    @ExceptionHandler(NumberOfActiveOrdersException.class)
    public ResponseEntity<Object> handleNumberOfOrders(NumberOfActiveOrdersException e){
        return new ResponseEntity<>("You can have only one ACTIVE order",HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CarWithGivenPlatesException.class)
    public ResponseEntity<Object> handleCarException(CarWithGivenPlatesException e){
        return new ResponseEntity<>("Car with given plates number already exists in database",HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EmailException.class)
    public ResponseEntity<Object> handleEmailException(EmailException e){
        return new ResponseEntity<>("Given email is already in use",HttpStatus.BAD_REQUEST);
    }
}
