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

package org.activiti.cloud.conf;

import java.util.List;

import org.activiti.cloud.client.EventsClient;
import org.activiti.cloud.client.ProcessRuntimeClient;
import org.activiti.cloud.client.QueryTaskClient;
import org.activiti.cloud.client.TaskRuntimeClient;
import org.activiti.cloud.steps.operations.CloudEventProvider;
import org.activiti.cloud.steps.operations.ProcessRuntimeClientOperations;
import org.activiti.cloud.steps.operations.QueryTaskProvider;
import org.activiti.cloud.steps.operations.RuntimeBundleTaskProvider;
import org.activiti.cloud.steps.operations.TaskRuntimeClientOperations;
import org.activiti.steps.EventProvider;
import org.activiti.steps.TaskProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudAssertionsConfiguration {

    @Bean
    public TaskProvider runtimeBundleTaskProvider(TaskRuntimeClient taskRuntimeClient){
        return new RuntimeBundleTaskProvider(taskRuntimeClient);
    }

    @Bean
    public TaskProvider queryTaskProvider(QueryTaskClient queryTaskClient){
        return new QueryTaskProvider(queryTaskClient);
    }

    @Bean
    public ProcessRuntimeClientOperations processRuntimeClientOperations(ProcessRuntimeClient processRuntimeClient,
                                                                         EventProvider eventProvider,
                                                                         List<TaskProvider> taskProviders){
        return new ProcessRuntimeClientOperations(processRuntimeClient, eventProvider, taskProviders);
    }

    @Bean
    public TaskRuntimeClientOperations taskRuntimeClientOperations(TaskRuntimeClient taskRuntimeClient,
                                                                   EventProvider eventProvider,
                                                                   List<TaskProvider> taskProviders) {
        return new TaskRuntimeClientOperations(taskRuntimeClient, eventProvider, taskProviders);
    }

    @Bean
    public EventProvider cloudEventProvider(EventsClient eventsClient){
        return new CloudEventProvider(eventsClient);
    }


}
