package org.activiti.cloud.acc.core.steps.runtime.admin;

import static org.assertj.core.api.Assertions.assertThat;

import net.thucydides.core.annotations.Step;
import org.activiti.api.process.model.builders.MessagePayloadBuilder;
import org.activiti.api.process.model.builders.ReceiveMessagePayloadBuilder;
import org.activiti.api.process.model.builders.StartMessagePayloadBuilder;
import org.activiti.cloud.acc.core.rest.feign.EnableRuntimeFeignContext;
import org.activiti.cloud.acc.core.services.runtime.admin.ProcessRuntimeAdminService;
import org.activiti.cloud.api.process.model.CloudProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedResources;

import java.io.IOException;
import java.util.Map;

@EnableRuntimeFeignContext
public class ProcessRuntimeAdminSteps {

    @Autowired
    private ProcessRuntimeAdminService processRuntimeAdminService;

    @Step
    public void checkServicesHealth() {
        assertThat(processRuntimeAdminService.isServiceUp()).isTrue();
    }

    @Step
    public PagedResources<CloudProcessInstance> getProcessInstances(){
        return processRuntimeAdminService.getProcessInstances();
    }
    
    @Step
    public void deleteProcessInstance(String id) {
        processRuntimeAdminService.deleteProcess(id);
    }
    
    @Step
    public CloudProcessInstance start(String messageName, 
                                      String businessKey, 
                                      Map<String, Object> variables) throws IOException {

        StartMessagePayloadBuilder payload = MessagePayloadBuilder.start(messageName)
                                                                  .withBusinessKey(businessKey)
                                                                  .withVariables(variables);

        return processRuntimeAdminService.message(payload.build());
    }
    
    @Step
    public void receive(String messageName, 
                        String correlationKey, 
                        Map<String, Object> variables) throws IOException {

        ReceiveMessagePayloadBuilder payload = MessagePayloadBuilder.receive(messageName)
                                                                    .withCorrelationKey(correlationKey)
                                                                    .withVariables(variables);

        processRuntimeAdminService.message(payload.build());
    }
}
