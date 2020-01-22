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

package org.activiti.cloud.test.operations;

import java.util.List;

import org.activiti.api.task.model.payloads.ClaimTaskPayload;
import org.activiti.api.task.model.payloads.CompleteTaskPayload;
import org.activiti.cloud.api.task.model.CloudTask;
import org.activiti.cloud.client.TaskRuntimeClient;
import org.activiti.test.EventSource;
import org.activiti.test.TaskSource;
import org.activiti.test.assertions.TaskAssertions;
import org.activiti.test.assertions.TaskAssertionsImpl;
import org.activiti.test.operations.TaskOperations;

public class TaskRuntimeClientOperations implements TaskOperations {

    private TaskRuntimeClient taskRuntimeClient;
    private EventSource eventSource;
    private List<TaskSource> taskSources;

    public TaskRuntimeClientOperations(TaskRuntimeClient taskRuntimeClient,
                                       EventSource eventSource,
                                       List<TaskSource> taskSources) {
        this.taskRuntimeClient = taskRuntimeClient;
        this.eventSource = eventSource;
        this.taskSources = taskSources;
    }

    @Override
    public TaskAssertions claim(ClaimTaskPayload claimTaskPayload) {
        CloudTask cloudTask = taskRuntimeClient.claimTask(claimTaskPayload.getTaskId());
        return buildTaskAssertions(cloudTask);
    }

    private TaskAssertionsImpl buildTaskAssertions(CloudTask cloudTask) {
        return new TaskAssertionsImpl(cloudTask,
                                      taskSources,
                                      eventSource);
    }

    @Override
    public TaskAssertions complete(CompleteTaskPayload completeTaskPayload) {
        //workaround for https://github.com/Activiti/Activiti/issues/2756
        CloudTask cloudTask = taskRuntimeClient.getTaskById(completeTaskPayload.getTaskId());
        taskRuntimeClient.completeTask(completeTaskPayload.getTaskId(),
                                                             completeTaskPayload);
        return buildTaskAssertions(cloudTask);
    }
}
