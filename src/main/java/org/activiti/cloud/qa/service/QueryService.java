package org.activiti.cloud.qa.service;

import org.activiti.cloud.qa.Config;
import org.activiti.cloud.qa.client.ConnectorHelper;
import org.activiti.cloud.qa.model.AuthToken;
import org.activiti.cloud.qa.model.EventsResponse;
import org.activiti.cloud.qa.model.QueryResponse;
import org.activiti.cloud.qa.serialization.Serializer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class QueryService {
    public static QueryResponse queryProcess(String processInstanceId, AuthToken authToken) throws URISyntaxException, IOException {

        Map<String, String> params = new HashMap<String, String>();
        params.put("processInstanceId", processInstanceId);
        QueryResponse response = Serializer.toQueryResponse(ConnectorHelper.get(Config.getInstance().getProperties().getProperty("query.event.url") , params, authToken));
        return response;
    }
}
