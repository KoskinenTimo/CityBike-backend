package org.citybike.advice;

import org.citybike.exception.JourneyNotFoundException;
import org.citybike.exception.RequiredResourceNotFoundWithIdException;
import org.citybike.exception.StationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleInvalidArguments(MethodArgumentNotValidException ex) {
        Map<String,String> errorMap = new HashMap<>();
        if(ex.getBindingResult().getFieldErrors().size() == 0) {
            errorMap.put("error", ex.getGlobalError().getDefaultMessage());
            return errorMap;
        } else {
            ex.getBindingResult().getFieldErrors().forEach(error -> {
                errorMap.put(error.getField(),error.getDefaultMessage());
            });
        }
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({StationNotFoundException.class})
    public Map<String,String> handleStationNotFoundException(StationNotFoundException ex) {
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("error", ex.getMessage());
        return errorMap;
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(JourneyNotFoundException.class)
    public Map<String,String> handleJourneyNotFoundException(JourneyNotFoundException ex) {
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("error", ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException .class)
    public Map<String,String> handleNotReadableRequest(HttpMessageNotReadableException ex) {
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("error", ex.getRootCause().getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RequiredResourceNotFoundWithIdException.class)
    public Map<String,String> handleNotReadableRequest(RequiredResourceNotFoundWithIdException ex) {
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("error", ex.getMessage());
        return errorMap;
    }





}
