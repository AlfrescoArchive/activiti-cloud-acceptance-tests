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

import org.activiti.api.process.model.payloads.StartProcessPayload;
import org.activiti.cloud.api.process.model.CloudProcessInstance;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "ActivitiProcessRuntimeClient",
        url = "${activiti.rb.uri}",
        configuration = ActivitiFeignClientConfiguration.class)
@RequestMapping(produces = {MediaTypes.HAL_JSON_VALUE})
public interface ProcessRuntimeClient extends ActivitiBaseClient {

    String PROCESS_INSTANCES_RELATIVE_PATH = "/v1/process-instances";

    @PostMapping(value = PROCESS_INSTANCES_RELATIVE_PATH)
    CloudProcessInstance startProcess(@RequestBody StartProcessPayload payload);

    @GetMapping(value = PROCESS_INSTANCES_RELATIVE_PATH)
    PagedResources<CloudProcessInstance> getProcessInstances(Pageable pageable);
}
