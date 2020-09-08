package com.chat.realtime.web.exception;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 404 에러
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        CommonErrorResponse obj = new CommonErrorResponse();
        obj.setStatus(404);
        obj.setMessage("Not found ! check your request Url");
        obj.setDetails(request.getDescription(false));
        obj.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(obj, HttpStatus.NOT_FOUND);
    }

    /**
     * 400 에러는 세분화해서 처리한다 case 1. PathVariable Missing case 2. RequestParameter
     * Missing case 3. parameter Type MisMatch
     */
    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {
        CommonErrorResponse obj = new CommonErrorResponse();
        obj.setStatus(400);
        obj.setMessage("check path variable !");
        obj.setDetails(request.getDescription(false));
        obj.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(obj, HttpStatus.BAD_REQUEST);
    }

    /**
     * 400에러
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {
        CommonErrorResponse obj = new CommonErrorResponse();
        obj.setStatus(400);
        obj.setMessage("check required parameters!");
        obj.setDetails(request.getDescription(false));
        obj.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(obj, HttpStatus.BAD_REQUEST);
    }

    /**
     * 400에러
     */
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {
        CommonErrorResponse obj = new CommonErrorResponse();
        obj.setStatus(400);
        obj.setMessage("check paratmeter or pathvariable type !");
        obj.setDetails(request.getDescription(false));
        obj.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(obj, HttpStatus.BAD_REQUEST);
    }

    /**
     * 사용자 정의 익셉션
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<Object> handleError(CommonException ex, WebRequest request) {
        CommonErrorResponse obj = new CommonErrorResponse();
        obj.setStatus(ex.getStatus());
        obj.setMessage("error " + ex.getMessage());
        obj.setDetails(request.getDescription(false));
        obj.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(obj, HttpStatus.BAD_REQUEST);
    }


}