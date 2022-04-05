package com.user.security.poc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserTo extends BaseTo {
    @Schema(description = "Generated User ID", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @Schema(description = "User Name", maxLength = 10, minLength = 5, required = true)
    private String username;
    @Schema(description = "User password",minLength = 5, required = true, pattern = "[a-z1-0]*")
    private String password;
    @Schema(description = "True if active, False if disabled", accessMode = Schema.AccessMode.READ_ONLY, defaultValue = "true")
    private boolean enabled;
    private Set<RoleTo> roles = new HashSet<>();
}
