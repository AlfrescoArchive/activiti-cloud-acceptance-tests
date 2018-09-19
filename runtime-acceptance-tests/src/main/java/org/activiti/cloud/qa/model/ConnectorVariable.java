package org.activiti.cloud.qa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConnectorVariable {

    @JsonProperty("input-variable-name-1")
    private String inputVariable;

    public String getInputVariable() {
        return inputVariable;
    }
}
