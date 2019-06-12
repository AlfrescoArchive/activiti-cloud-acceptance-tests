/*
 * Copyright 2019 Alfresco, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.cloud.test;

import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.cloud.CloudAssertionsTestApplication;
import org.activiti.cloud.api.task.model.CloudTask;
import org.activiti.cloud.client.TaskRuntimeClient;
import org.activiti.test.matchers.TaskMatchers;
import org.activiti.test.operations.ProcessOperations;
import org.activiti.test.operations.TaskOperations;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedResources;
import org.springframework.test.context.junit4.SpringRunner;

import static org.activiti.test.matchers.BPMNStartEventMatchers.startEvent;
import static org.activiti.test.matchers.EndEventMatchers.endEvent;
import static org.activiti.test.matchers.ManualTaskMatchers.manualTask;
import static org.activiti.test.matchers.ProcessInstanceMatchers.processInstance;
import static org.activiti.test.matchers.ProcessTaskMatchers.taskWithName;
import static org.activiti.test.matchers.ProcessVariableMatchers.processVariable;
import static org.activiti.test.matchers.SequenceFlowMatchers.sequenceFlow;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = CloudAssertionsTestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
public class ActivitiCloudAssertionsTestExample {

    private static final int MAX_ITEMS = 100;
    private static final String USERNAME = "hruser";

    @Autowired
    private ProcessOperations processOperations;

    @Autowired
    private TaskOperations taskOperations;

    @Autowired
    private TaskRuntimeClient taskRuntimeClient;

    @Test
    public void shouldExecuteGenericProcess() {

        processOperations.start(ProcessPayloadBuilder
                                        .start()
                                        .withProcessDefinitionKey("processwit-c6fd1b26-0d64-47f2-8d04-0b70764444a7")
                                        .withBusinessKey("my-business-key")
                                        .withName("my-process-instance-name")
                                        .withVariable("client",
                                                      "the best")
                                        .build())
                .expectEvents(
                        processInstance()
                                .hasBeenStarted(),
                        processVariable("client",
                                        "the best")
                                .hasBeenCreated(),
                        startEvent("StartEvent_1")
                                .hasBeenCompleted(),
                        sequenceFlow("SequenceFlow_108momn")
                                .hasBeenTaken(),
                        manualTask("Task_1dk1vp6")
                                .hasBeenCompleted(),
                        sequenceFlow("SequenceFlow_1lit3dy")
                                .hasBeenTaken(),
                        endEvent("EndEvent_0lms8y3")
                                .hasBeenCompleted(),
                        processInstance()
                                .hasBeenCompleted()

                )
                .expectFields(
                        processInstance()
                                .businessKey("my-business-key"),
                        processInstance()
                                .name("my-process-instance-name")

                )
        ;
    }

    @Test
    public void shouldExecuteProcessWithUserTask() {

        ProcessInstance processInstance = processOperations.start(ProcessPayloadBuilder
                                                                          .start()
                                                                          .withProcessDefinitionKey("usertaskgr-1a8cdf77-0981-45d4-8080-7cf1a80c973b")
                                                                          .withBusinessKey("my-business-key")
                                                                          .withName("my-process-instance-name")
                                                                          .build())
                .expectEvents(
                        processInstance()
                                .hasBeenStarted(),
                        startEvent("StartEvent_1")
                                .hasBeenCompleted(),
                        sequenceFlow("SequenceFlow_052072h")
                                .hasBeenTaken(),
                        taskWithName("Task Group 1")
                                .hasBeenCreated()

                )
                .expectFields(processInstance()
                                      .businessKey("my-business-key"),
                              processInstance()
                                      .name("my-process-instance-name"))
                .expect(processInstance()
                                .hasTask("Task Group 1",
                                         Task.TaskStatus.CREATED))
                .andReturn();

        PagedResources<CloudTask> taskPagedResources = taskRuntimeClient.getTasks(processInstance.getId(),
                                                                                  PageRequest.of(0,
                                                                                                 MAX_ITEMS));

        assertThat(taskPagedResources.getContent()).hasSize(1);

        Task task = taskPagedResources.getContent().iterator().next();
        taskOperations.claim(TaskPayloadBuilder
                                     .claim()
                                     .withTaskId(
                                             task.getId())
                                     .build())
                .expectEvents(TaskMatchers.task()
                                .hasBeenAssigned())
                .expectFields(TaskMatchers.task()
                                .assignee(USERNAME));

        taskOperations.complete(TaskPayloadBuilder
                                        .complete()
                                        .withTaskId(task.getId())
                                        .build())
                .expectEvents(
                        TaskMatchers.task().hasBeenCompleted(),
                        taskWithName("Task Group 2").hasBeenCreated()
                )
                .expect(processInstance()
                                .hasTask("Task Group 1",
                                         Task.TaskStatus.COMPLETED),
                        processInstance()
                                .hasTask("Task Group 2",
                                         Task.TaskStatus.CREATED))
        ;
    }
}