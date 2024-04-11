package com.isw.bookstore.exceptions;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isw.bookstore.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class Exceptions {

    @Autowired
    private ObjectMapper objectMapper ;

    @ExceptionHandler(value = NotFoundException.class)
    protected ResponseEntity<ApiResponse> handleNotFound(NotFoundException ex) throws JsonProcessingException {

        ApiResponse errorResponseHandler = new ApiResponse();
        errorResponseHandler.setRespCode("404");
        errorResponseHandler.setRespDescription(ex.getMessage());
        log.info("BadRequestException === > {}",objectMapper.writeValueAsString(errorResponseHandler));
        return new ResponseEntity<>(errorResponseHandler, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NoResourceFoundException.class)
    protected ResponseEntity<ApiResponse> handleNotFound(NoResourceFoundException ex) throws JsonProcessingException {

        ApiResponse errorResponseHandler = new ApiResponse();
        errorResponseHandler.setRespCode("404");
        errorResponseHandler.setRespDescription("Endpoint request is not available");

        return new ResponseEntity<>(errorResponseHandler, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = EmptyCartException.class)
    protected ResponseEntity<ApiResponse> handleEmptyCart(EmptyCartException ex) throws JsonProcessingException {

        ApiResponse errorResponseHandler = new ApiResponse();
        errorResponseHandler.setRespCode("405");
        errorResponseHandler.setRespDescription(ex.getMessage());
        log.info("EmptyCartException === > {}",objectMapper.writeValueAsString(errorResponseHandler));
        return new ResponseEntity<>(errorResponseHandler, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = BookExistsException.class)
    protected ResponseEntity<ApiResponse> handleBookExist(BookExistsException ex) throws JsonProcessingException {

        ApiResponse errorResponseHandler = new ApiResponse();
        errorResponseHandler.setRespCode("405");
        errorResponseHandler.setRespDescription(ex.getMessage());
        log.info("BookExistsException === > {}",objectMapper.writeValueAsString(errorResponseHandler));
        return new ResponseEntity<>(errorResponseHandler, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = BookStoreException.class)
    protected ResponseEntity<ApiResponse> handleBookStore(BookStoreException ex) throws JsonProcessingException {

        ApiResponse errorResponseHandler = new ApiResponse();
        errorResponseHandler.setRespCode("405");
        errorResponseHandler.setRespDescription(ex.getMessage());
        log.info("BookExistsException === > {}",objectMapper.writeValueAsString(errorResponseHandler));
        return new ResponseEntity<>(errorResponseHandler, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ValidationException.class)
    protected ResponseEntity<ApiResponse> handleValidation(ValidationException ex) throws JsonProcessingException {

        ApiResponse errorResponseHandler = new ApiResponse();
        errorResponseHandler.setRespCode("405");
        errorResponseHandler.setRespDescription(ex.getMessage());
        log.info("ValidationException === > {}",objectMapper.writeValueAsString(errorResponseHandler));
        return new ResponseEntity<>(errorResponseHandler, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse> handleValidation(MethodArgumentNotValidException ex) throws JsonProcessingException {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiResponse errorResponseHandler = new ApiResponse();
        errorResponseHandler.setRespCode("405");
        errorResponseHandler.setRespDescription("Validation Failed");
        errorResponseHandler.setRespBody(errors);
        log.info("ValidationException === > {}",objectMapper.writeValueAsString(errorResponseHandler));
        return new ResponseEntity<>(errorResponseHandler, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<ApiResponse> handleGlobal(Exception ex) throws JsonProcessingException {

        ex.printStackTrace();
        ApiResponse errorResponseHandler = new ApiResponse();
        errorResponseHandler.setRespCode("M500");
        errorResponseHandler.setRespDescription("System malfunction please contact admin");
        log.info("Exception === > {}",objectMapper.writeValueAsString(errorResponseHandler));
        return new ResponseEntity<>(errorResponseHandler, HttpStatus.BAD_REQUEST);
    }
}
