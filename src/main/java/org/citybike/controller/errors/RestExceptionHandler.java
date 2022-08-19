package org.citybike.controller.errors;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;

//@Order(Ordered.HIGHEST_PRECEDENCE)
//@ControllerAdvice()
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(NoSuchElementException.class)
//    public ResponseEntity<Object> handleNoSuchElementException(HttpServletRequest req,NoSuchElementException e) {
//        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND);
//        response.setMessage("Cannot find resource with this identifier: " + req.getRequestURI());
//        return buildResponseEntity(response);
//
//    }
//
//    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
//    public ResponseEntity<Object> handleSQLIntegrityConstraintViolationException(HttpServletRequest req,SQLIntegrityConstraintViolationException e) {
//        String error = "Unable to submit post: " + e.getMessage();
//        return buildResponseEntity(new ErrorResponse(HttpStatus.BAD_REQUEST, error));
//    }
//
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<Object> handleDataIntegrityViolationException(HttpServletRequest req, DataIntegrityViolationException e) {
//        e.printStackTrace();
//
//        System.out.println(e.getMessage());;
//        String error = "Unable to create new resource, data is not valid";
//        return buildResponseEntity(new ErrorResponse(HttpStatus.BAD_REQUEST, error));
//    }
//
//    private ResponseEntity<Object> buildResponseEntity(ErrorResponse errorResponse) {
//        return new ResponseEntity<>(errorResponse,errorResponse.getStatus());
//    }
}

