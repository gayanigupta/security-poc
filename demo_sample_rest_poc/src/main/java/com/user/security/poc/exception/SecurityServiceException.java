package com.user.security.poc.exception;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityServiceException extends RuntimeException {
    private static final Logger log = LoggerFactory.getLogger(SecurityServiceException.class);
    private static final String MESSAGES_FILE = "errors.properties";
    private static final ResourceBundle BUNDLE = loadResources();

    private static final String MESSAGE_KEY_PREFIX = "error.";
    private static final String DETAILED_MESSAGE_KEY_SUFFIX = ".details";

    private final SecurityServiceErrorCode code;
    private final int httpStatus;
    private final String[] messageParameters;
    private final String[] detailMessageParameters;

    
    public SecurityServiceException(String message) {
    	super(message);
		this.code = null;
		this.httpStatus = 0;
		this.messageParameters = null;
		this.detailMessageParameters = null;
		
    }
  
    
    public SecurityServiceException(SecurityServiceErrorCode code,
                                    int httpStatus,
                                    Throwable th) {
        this(code, httpStatus, null, null, th);
    }

    public SecurityServiceException(SecurityServiceErrorCode code,
                                    int httpStatus,
                                    String[] messageParameters,
                                    String[] detailMessageParameters) {
        this(code, httpStatus, messageParameters, detailMessageParameters, null);
    }

    public SecurityServiceException(SecurityServiceErrorCode code,
                                    int httpStatus,
                                    String[] messageParameters,
                                    String[] detailMessageParameters,
                                    Throwable cause) {
        super(cause);
        this.code = code;
        this.httpStatus = httpStatus;
        this.messageParameters = messageParameters;
        this.detailMessageParameters = detailMessageParameters;
    }

    private static String getResource(String key) {
        if (BUNDLE == null) {
            return key;
        }
        try {
            return BUNDLE.getString(key);
        } catch (MissingResourceException ex) {
            log.error("Unable to get message description", ex);
        }
        return key;
    }

    public String getDetailMessage() {
        StringBuilder keyBuilder = new StringBuilder(MESSAGE_KEY_PREFIX).append(code.code())
                .append(DETAILED_MESSAGE_KEY_SUFFIX);
        String detailedMessageKey = keyBuilder.toString();
        String detailedMessagePattern = getResource(detailedMessageKey);

        return formatString(detailedMessagePattern, detailMessageParameters);
    }

    private static String formatString(String pattern,
                                       String[] args) {
        if (args == null || args.length == 0) {
            return pattern;
        }
        return MessageFormat.format(pattern, args);
    }

    private static final ResourceBundle loadResources() {
        try (InputStream is = SecurityServiceException.class.getClassLoader()
                .getResourceAsStream(MESSAGES_FILE)) {
            return new PropertyResourceBundle(is);
        } catch (Exception e) {
            log.error("An unexpected error occurred while loading error messages for the dataloaderServiceClient: " + e
                    .getMessage(), e);
        }
        return null;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public SecurityServiceErrorCode getCode() {
        return code;
    }

    public String[] getMessageParameters() {
        return messageParameters;
    }

    public String[] getDetailMessageParameters() {
        return detailMessageParameters;
    }
}
