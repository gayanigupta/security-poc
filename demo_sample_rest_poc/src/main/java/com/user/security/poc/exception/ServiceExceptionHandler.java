package com.user.security.poc.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Special controller handler for exceptions
 */
@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(ServiceExceptionHandler.class);
    @Value("${exceptions.include-stacktrace}")
    boolean includeStackTrace;

    @ExceptionHandler(SecurityServiceException.class)
    @ResponseBody
    protected ResponseEntity<Object> handleControllerException(HttpServletRequest req, Throwable ex) {
        return convert(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return convert(ex, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<Object> convert(Throwable ex, HttpStatus defaultStatus) {
        //To appear in the log
        log.error(ex.getMessage(),ex);
        Map<String, Object> responseBody = getErrorAttributes(ex);
        if (ex instanceof SecurityServiceException) {
            SecurityServiceException serviceException = (SecurityServiceException) ex;
            return new ResponseEntity<>(responseBody, HttpStatus.valueOf(serviceException.getHttpStatus()));
        }
        return new ResponseEntity<>(responseBody, defaultStatus);
    }

    private Map<String, Object> getErrorAttributes(Throwable error) {
        Map<String, Object> errorAttributes = new LinkedHashMap<>();
        errorAttributes.put("severity", "Error");
        if (error != null) {
            errorAttributes.put("errorMessage", error.getMessage());
            if (error instanceof SecurityServiceException) {
                SecurityServiceException serviceException = (SecurityServiceException) error;
                errorAttributes.put("errorCode", serviceException.getCode()
                        .code());
                errorAttributes.put("errorDetailMessage", serviceException.getDetailMessage());
            }
            fillInnerErrorData(errorAttributes, error, includeStackTrace);
        }
        return errorAttributes;
    }


    private void fillInnerErrorData(Map<String, Object> errorAttributes, Throwable error, boolean includeStackTrace) {
        Map<String, Object> innerErrorData = new LinkedHashMap<>();
        innerErrorData.put("exception", error.getClass()
                .getCanonicalName() + ":" + error.getMessage());
        if (includeStackTrace) {
            fillStack(innerErrorData, error);
        }
        Throwable cause = error.getCause();
        if (cause != null && cause != error) {
            fillInnerErrorData(innerErrorData, cause, includeStackTrace);
        }
        errorAttributes.put("innerErrorData", innerErrorData);
    }

    private void fillStack(Map<String, Object> errorAttributes, Throwable error) {
        StackTraceElement[] trace = error.getStackTrace();
        if (trace != null) {
            List<String> result = new ArrayList<>();
            for (StackTraceElement next : trace) {
                result.add(next.getClassName() + "." + next.getMethodName() + " at " + next.getFileName() + ", line " + next.getLineNumber());
            }
            errorAttributes.put("stack", result);
        }
    }
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return convert (ex,status);
    }
}
