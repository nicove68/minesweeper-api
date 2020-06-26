package com.minesweeper.api.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.minesweeper.api.exception.rest.RestException;
import com.minesweeper.api.exception.rest.RestResponse;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerErrorHandler {

  private static final Logger logger = LoggerFactory.getLogger(RestControllerErrorHandler.class);
  private static final String ERROR_EXECUTING = "Error executing {}";


  @ExceptionHandler(Exception.class)
  public ResponseEntity<RestResponse> handleException(HttpServletRequest req, Exception ex) {
    logger.error(ERROR_EXECUTING, req.getRequestURI(), ex);

    RestResponse error = new RestResponse(INTERNAL_SERVER_ERROR, ex.getMessage());

    return new ResponseEntity<>(error, INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(RestException.class)
  public ResponseEntity<RestResponse> handleRestException(HttpServletRequest req, RestException ex) {
    logger.error(ERROR_EXECUTING, req.getRequestURI(), ex);

    RestResponse response = ex.getResponse();
    HttpStatus httpStatus = response.getStatus();

    return new ResponseEntity<>(ex.getResponse(), httpStatus);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<RestResponse> handleInvalidFormatException(HttpServletRequest req, RestException ex) {
    logger.error(ERROR_EXECUTING, req.getRequestURI(), ex);

    return new ResponseEntity<>(ex.getResponse(), BAD_REQUEST);
  }
}
