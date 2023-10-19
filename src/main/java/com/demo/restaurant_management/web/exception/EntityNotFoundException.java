package com.demo.restaurant_management.web.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EntityNotFoundException extends RuntimeException {

    private String entityName;
    private String entityId;

    public EntityNotFoundException() {
        super("err.api.entity-not-found", null);
    }

    public EntityNotFoundException(String entityName, String entityId) {
        super("err.api.entity-not-found", null);
        this.entityId = entityId;
        this.entityName = entityName;
    }
}
