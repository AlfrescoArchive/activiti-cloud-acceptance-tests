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

package org.activiti.cloud.steps.operations;

import java.util.ArrayList;
import java.util.List;

import org.activiti.api.model.shared.event.RuntimeEvent;
import org.activiti.cloud.api.model.shared.events.CloudRuntimeEvent;
import org.activiti.cloud.client.EventsClient;
import org.activiti.steps.EventProvider;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.PagedResources;

public class CloudEventProvider implements EventProvider {

    private static final int PAGE_SIZE = 1000;
    private EventsClient eventsClient;

    public CloudEventProvider(EventsClient eventsClient) {
        this.eventsClient = eventsClient;
    }

    @Override
    public List<RuntimeEvent<?, ?>> getEvents() {
        PagedResources<CloudRuntimeEvent<?, ?>> eventResources = eventsClient.findAll(null,
                                                                                      PageRequest.of(0,
                                                                                                     PAGE_SIZE,
                                                                                                     Sort.Direction.DESC, "timestamp"));
        return new ArrayList<>(eventResources.getContent());
    }
}
