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

package org.activiti.cloud.client;

import org.activiti.cloud.api.task.model.CloudTask;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface TaskReadOnlyClient {

    String TASKS_RELATIVE_PATH = "/v1/tasks";

    @GetMapping(value = TASKS_RELATIVE_PATH)
    PagedResources<CloudTask> getTasks(Pageable pageable);

    @GetMapping(value = TASKS_RELATIVE_PATH + "/{taskId}")
    CloudTask getTaskById(@PathVariable("taskId") String taskId);

    @GetMapping(value = "/v1/process-instances/{processInstanceId}/tasks")
    PagedResources<CloudTask> getTasks(@PathVariable("processInstanceId") String processInstanceId,
                                       Pageable pageable);
    @GetMapping(value = "/v1/process-instances/{processInstanceId}/tasks")
    PagedResources<Resource<CloudTask>> getTasks2(@PathVariable("processInstanceId") String processInstanceId,
                                                 Pageable pageable);

}
