package org.activiti.cloud.qa.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum QueryStatus {
    COMPLETED("COMPLETED");

    private final String type;

    QueryStatus(String type) {
        this.type = type;
    }

    @JsonValue
    public String getType() {
        return this.type;
    }
}
