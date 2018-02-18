package org.activiti.cloud.qa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QueryResponse {

    @JsonProperty(value = "_embedded")
    private EmbeddedProcessInstances embeddedInstances;

    public EmbeddedProcessInstances getEmbeddedEvents() {
        return embeddedInstances;
    }
}
