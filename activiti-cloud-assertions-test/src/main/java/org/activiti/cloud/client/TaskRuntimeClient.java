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

import org.activiti.api.task.model.payloads.CompleteTaskPayload;
import org.activiti.cloud.api.task.model.CloudTask;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "TaskRuntimeClient",
        url = "${activiti.rb.uri}",
        configuration = ActivitiFeignClientConfiguration.class)
@RequestMapping(produces = {MediaTypes.HAL_JSON_VALUE})
public interface TaskRuntimeClient extends TaskReadOnlyClient {

    @PostMapping(value = TASKS_RELATIVE_PATH + "/{taskId}/claim")
    CloudTask claimTask(@PathVariable("taskId") String taskId);

    @PostMapping(value = TASKS_RELATIVE_PATH + "/{taskId}/complete")
    CloudTask completeTask(@PathVariable("taskId") String taskId,
                                     @RequestBody(required = false) CompleteTaskPayload completeTaskPayload);


}
