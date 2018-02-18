package org.activiti.cloud.qa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class EmbeddedProcessInstances {
    @JsonProperty(value = "process-instances")
    private List<ProcessInstance> processInstances;

    public List<ProcessInstance> getProcessInstances() {
        return processInstances;
    }
}
