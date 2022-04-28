package com.hospital.management.advice;

import com.hospital.management.apimodel.response.APIResponse;
import com.hospital.management.apimodel.response.BaseErrorResponse;
import com.hospital.management.enums.ResponseCode;
import com.hospital.management.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorControllerAdvice {

    private final BaseErrorResponse baseErrorResponse;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIResponse handleJavaxValidationException(MethodArgumentNotValidException e) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : e.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        String errorMessage = details.toString();
        return baseErrorResponse.buildErrorResponse(ResponseCode.BAD_REQUEST.getCode(), errorMessage, e);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public APIResponse handleBadRequestApiException(BadRequestException e) {
        return baseErrorResponse.buildErrorResponse(e.getCode(), e.getMessage(), e);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return baseErrorResponse
                .buildErrorResponse(ResponseCode.BAD_REQUEST.getCode(), "The request could not be completed due to malformed syntax. Kindly check and try again.", e);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return baseErrorResponse.buildErrorResponse(ResponseCode.BAD_REQUEST.getCode(), e.getMessage(), e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public APIResponse handleGeneralException(Exception e) {
        log.error("Server error", e);
        return baseErrorResponse.buildErrorResponse("Oops, It's not you. Please try again later");
    }

}
