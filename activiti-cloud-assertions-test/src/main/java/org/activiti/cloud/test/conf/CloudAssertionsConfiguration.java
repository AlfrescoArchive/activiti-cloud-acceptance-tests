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

package org.activiti.cloud.test.conf;

import java.util.List;

import org.activiti.cloud.client.EventsClient;
import org.activiti.cloud.client.ProcessRuntimeClient;
import org.activiti.cloud.client.QueryTaskClient;
import org.activiti.cloud.client.TaskRuntimeClient;
import org.activiti.cloud.test.operations.CloudEventSource;
import org.activiti.cloud.test.operations.ProcessRuntimeClientOperations;
import org.activiti.cloud.test.operations.QueryTaskSource;
import org.activiti.cloud.test.operations.RuntimeBundleTaskSource;
import org.activiti.cloud.test.operations.TaskRuntimeClientOperations;
import org.activiti.test.EventSource;
import org.activiti.test.TaskSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudAssertionsConfiguration {

    @Bean
    public TaskSource runtimeBundleTaskSource(TaskRuntimeClient taskRuntimeClient){
        return new RuntimeBundleTaskSource(taskRuntimeClient);
    }

    @Bean
    public TaskSource queryTaskSource(QueryTaskClient queryTaskClient){
        return new QueryTaskSource(queryTaskClient);
    }

    @Bean
    public ProcessRuntimeClientOperations processRuntimeClientOperations(ProcessRuntimeClient processRuntimeClient,
                                                                         EventSource eventSource,
                                                                         List<TaskSource> taskSources){
        return new ProcessRuntimeClientOperations(processRuntimeClient, eventSource, taskSources);
    }

    @Bean
    public TaskRuntimeClientOperations taskRuntimeClientOperations(TaskRuntimeClient taskRuntimeClient,
                                                                   EventSource eventSource,
                                                                   List<TaskSource> taskSources) {
        return new TaskRuntimeClientOperations(taskRuntimeClient, eventSource, taskSources);
    }

    @Bean
    public EventSource cloudEventSource(EventsClient eventsClient){
        return new CloudEventSource(eventsClient);
    }


}
