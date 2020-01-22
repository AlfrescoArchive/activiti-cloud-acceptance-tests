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

import java.util.ArrayList;
import java.util.List;

import org.activiti.api.task.model.Task;
import org.activiti.cloud.api.task.model.CloudTask;
import org.activiti.cloud.client.TaskRuntimeClient;
import org.activiti.test.TaskSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedResources;

public class RuntimeBundleTaskSource implements TaskSource {

    private static final int MAX_ITEMS = 1000;
    private TaskRuntimeClient taskRuntimeClient;

    public RuntimeBundleTaskSource(TaskRuntimeClient taskRuntimeClient) {
        this.taskRuntimeClient = taskRuntimeClient;
    }

    @Override
    public List<Task> getTasks(String processInstanceId) {
        PagedResources<CloudTask> tasks = taskRuntimeClient.getTasks(processInstanceId,
                                                                     PageRequest.of(0,
                                                                                    MAX_ITEMS));
        return new ArrayList<>(tasks.getContent());
    }

    @Override
    public boolean canHandle(Task.TaskStatus taskStatus) {
        switch (taskStatus) {
            case CREATED:
            case ASSIGNED:
            case SUSPENDED:
                return true;
        }
        return false;
    }
}
