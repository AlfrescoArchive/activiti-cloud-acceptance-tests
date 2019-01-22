package org.activiti.cloud.acc.core.steps.notifications;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import net.thucydides.core.annotations.Step;
import org.activiti.cloud.acc.core.rest.RuntimeDirtyContextHandler;
import org.activiti.cloud.acc.core.rest.feign.EnableRuntimeFeignContext;
import org.activiti.cloud.acc.core.services.notifications.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.ReplayProcessor;
import reactor.test.StepVerifier;

@EnableRuntimeFeignContext
public class NotificationsSteps {
    
    private static final String WS_GRAPHQL_URI = "/ws/graphql";
    private static final String GRAPHQL_WS = "graphql-ws";
    private static final String HRUSER = "hruser";
    private static final String AUTHORIZATION = "Authorization";
    private static final String TESTADMIN = "testadmin";
    private static final String TASK_NAME = "task1";
    private static final String GRPAPHQL_URL = "/graphql";
    private static final Duration TIMEOUT = Duration.ofMillis(20000);
    

    @Autowired
    private RuntimeDirtyContextHandler dirtyContextHandler;

    @Autowired
    private NotificationsService processService;

    @Step
    public void checkServicesHealth() {
        assertThat(processService.isServiceUp()).isTrue();
    }

    @Step
    public ReplayProcessor<String> subscribe(String host,String port,String auth) {
        ReplayProcessor<String> data = ReplayProcessor.create();
        return data;
        
//        WebsocketSender client = HttpClient.create()
//                .baseUrl("ws://"+host+":" + port)
//                .wiretap(true)
//                .headers(h -> h.add(AUTHORIZATION, auth))
//                .websocket(GRAPHQL_WS)
//                .uri(WS_GRAPHQL_URI);
//        
//        String startMessage = "{\"id\":\"1\",\"type\":\"start\",\"payload\":{\"query\":\"subscription {\\n engineEvents(appName: \\\"default-app\\\") {\\n appName\\n serviceName\\n }\\n}\",\"variables\":null}}";
//        
//        // start subscription
//        client.handle((i, o) -> {
//                o.options(NettyPipeline.SendOptions::flushOnEach)
//                .sendString(Mono.just(startMessage))
//                .then()
//                .log("start")
//                .subscribe();
//    
//                    return i.receive()
//                        .asString()
//                        .log("data")
//                        .take(2)
//                        .subscribeWith(data);
//                }) // stop subscription
//                .collectList()
//                .subscribe();
//
//       // start process instance
//       // complete
//       // expect complete 
//                
//        return data;
    }
    
    @Step
    public void verifySubscribe(ReplayProcessor<String> data) {
        
        String ackMessage = "{\"payload\":{},\"id\":null,\"type\":\"connection_ack\"}";
        String kaMessage = "{\"payload\":{},\"id\":null,\"type\":\"ka\"}";
        String dataMessage = "{\"payload\":{\"data\":{\"engineEvents\":{\"appName\":\"default-app\",\"serviceName\":\"rb-my-app\"}}},\"id\":\"1\",\"type\":\"data\"}";
        String completeMessage = "{\"payload\":{},\"id\":\"1\",\"type\":\"complete\"}";

        StepVerifier.create(data)
        .expectNext(dataMessage)
        .expectNext(dataMessage)
        .expectComplete()
        .verify(TIMEOUT);
    }
}
