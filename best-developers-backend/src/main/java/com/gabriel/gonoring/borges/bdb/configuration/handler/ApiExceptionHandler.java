package com.gabriel.gonoring.borges.bdb.configuration.handler;

import com.gabriel.gonoring.borges.bdb.client.exception.GitHubForbiddenException;
import com.gabriel.gonoring.borges.bdb.dto.error.ErrorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);


    @ExceptionHandler(GitHubForbiddenException.class)
    public ResponseEntity<ErrorDTO> handlerGitHubForbiddenException(GitHubForbiddenException exception){

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ErrorDTO errorDTO = new ErrorDTO();

        errorDTO.setTitle("GitHub blocked your request");
        errorDTO.setStatusCode(httpStatus.value());
        errorDTO.setStatusCodeDescription(httpStatus.toString());

        errorDTO.setDescription(exception.getMessage());

        return new ResponseEntity<>(errorDTO, httpStatus);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> handlerConstraintValidationException(ConstraintViolationException exception){

        String detail = exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));

        LOGGER.warn("Invalid parameter. Message returned is: {}", detail);

        return new ResponseEntity(createInvalidValueError(detail), HttpStatus.BAD_REQUEST);
    }

    private ErrorDTO createInvalidValueError(String description){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ErrorDTO errorDTO = new ErrorDTO();

        errorDTO.setTitle("Invalid values");
        errorDTO.setStatusCode(httpStatus.value());
        errorDTO.setStatusCodeDescription(httpStatus.toString());

        errorDTO.setDescription(description);

        return errorDTO;
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorDTO> authenticationExceptionHandler(AuthenticationException exception){
        LOGGER.warn("login failure. Caused by: {}", exception.toString());

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ErrorDTO errorDTO = new ErrorDTO();

        errorDTO.setTitle("Invalid credentials");
        errorDTO.setStatusCode(httpStatus.value());
        errorDTO.setStatusCodeDescription(httpStatus.toString());

        errorDTO.setDescription(exception.getMessage());

        return new ResponseEntity<>(errorDTO, httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String detail = exception.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", "));

        LOGGER.warn("Invalid parameter. Message returned is: {}", detail);

        return new ResponseEntity<>(createInvalidValueError(detail), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        LOGGER.error("ERROR", ex.getMessage());

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatusCode(status.value());
        errorDTO.setStatusCodeDescription(status.toString());
        errorDTO.setTitle((HttpStatus.INTERNAL_SERVER_ERROR.equals(status))?"Internal server error":"Error");
        errorDTO.setDescription(ex.getMessage());

        return new ResponseEntity(errorDTO, headers, status);
    }

    @ExceptionHandler({ Throwable.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {

        LOGGER.error("ERROR", ex);

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDTO.setStatusCodeDescription(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        errorDTO.setTitle("Internal server error");
        errorDTO.setDescription(ex.getMessage());

        return new ResponseEntity<Object>(errorDTO, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
