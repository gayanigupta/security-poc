package com.user.security.poc.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_EMPTY)
@Data
public class BaseTo implements Serializable {
    @Schema(description = "Generated User ID", accessMode = Schema.AccessMode.READ_ONLY)
    private String createdBy;
    @Schema(description = "Generated User ID", accessMode = Schema.AccessMode.READ_ONLY)
    private String updatedBy;
    @Schema(description = "Generated User ID", accessMode = Schema.AccessMode.READ_ONLY)
    private Date createdDate;
    @Schema(description = "Generated User ID", accessMode = Schema.AccessMode.READ_ONLY)
    private Date updatedDate;
}

