
package com.user.security.poc.exception;

import java.util.Arrays;

public enum SecurityServiceErrorCode {
    INTERNAL_ERROR(1),
    GENERIC_ERROR(2),
    MISSING_FIELD(3),
    NO_USER_FOUND_WITH_ID(4),
    DUPLICATE_USER_NAME(5),
    CONSTRAINT_VIOLATION(99991);
    private final int code;

    SecurityServiceErrorCode(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }

    public static SecurityServiceErrorCode findByCode(final int code) {
        return Arrays.stream(values()).filter(value -> value.code == code).findFirst().orElse(null);
    }
}
