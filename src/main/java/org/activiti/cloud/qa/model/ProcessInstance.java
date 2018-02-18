package org.activiti.cloud.qa.model;

public class ProcessInstance {

    private long id;
    private long timestamp;
    private String processDefinitionId;
    private String processInstanceId;
    private String status;

    public String getStatus() {
        return status;
    }

    public long getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }


    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

}
