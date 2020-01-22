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

import org.activiti.cloud.api.model.shared.events.CloudRuntimeEvent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ActivitiAuditClient",
        url = "${activiti.audit.uri}",
        decode404 = true,
        configuration = ActivitiFeignClientConfiguration.class)
@RequestMapping(produces = MediaTypes.HAL_JSON_VALUE)
public interface EventsClient {


    @GetMapping(value = "/v1/events")
    PagedResources<CloudRuntimeEvent<?,?>> findAll(@RequestParam(value = "search", required = false) String search,
                                                        Pageable pageable);

}
