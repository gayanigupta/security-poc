package com.user.security.poc.repository.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
@MappedSuperclass
public abstract class BaseModel {

    @Column(name = "created_by")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String createdBy;

    @Column(name = "updated_by")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)

    private String updatedBy;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "created_date", length = 19)
    private Date createdDate;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "updated_date", length = 19)
    private Date updatedDate;

    @PrePersist
    public void beforeCreate() {
        Date date = new Date();
        this.createdDate = date;
        this.updatedDate = null;
    }

    @PreUpdate
    public void beforeUpdate() {
        this.updatedDate = new Date();
    }


}
