package org.activiti.cloud.qa.steps;

import net.thucydides.core.annotations.Step;
import org.activiti.cloud.qa.model.ConnectorVariable;
import org.activiti.cloud.qa.rest.feign.EnableRuntimeFeignContext;
import org.activiti.cloud.qa.service.ConnectorService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

/**
 * Connector steps
 */
@EnableRuntimeFeignContext
public class ConnectorSteps {

    @Autowired
    private ConnectorService connectorService;

    @Step
    public void getInBoundVariable(String matchVariable) {

        await().untilAsserted(() -> {

            ConnectorVariable variable = connectorService.getConnectorInBoundVariable();

            assertThat(variable).isNotNull();

            assertThat(variable.getInputVariable()).isEqualTo(matchVariable);
        });
    }
}
