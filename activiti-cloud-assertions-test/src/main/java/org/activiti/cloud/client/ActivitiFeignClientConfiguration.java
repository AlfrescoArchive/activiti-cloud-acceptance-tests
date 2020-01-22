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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.Decoder;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.hateoas.hal.ResourcesMixin;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

public class ActivitiFeignClientConfiguration {


    /**
     * Configures Hal Media link Id attribute deserialization
     */
    public abstract class IdResourcesSupportMixin extends ResourcesMixin {

        @Override
        @JsonIgnore(false)
        public abstract Link getId();
    }

    /**
     * Configures Hal Media entity Id attribute deserialization
     */
    public abstract class IdSupportMixin {

        @JsonIgnore(false)
        public abstract String getId();
    }


    /**
     * Configures Feign Decoder to support Activiti Hal Media Format
     */
    @Bean
    public Decoder feignDecoder(ObjectMapper mapper) {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                         false)
                .registerModule(new Jackson2HalModule());

        return new ResponseEntityDecoder(new JacksonDecoder(mapper));
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public LoginService loginService(ActivitiResourceOwnerPasswordResourceDetails activitiResourceOwnerPasswordResourceDetails) {
        return new LoginService(activitiResourceOwnerPasswordResourceDetails);
    }

    /**
     * Configures Activiti Rest Client to apply Access Token for each Api request
     */
    @Bean
    public RequestInterceptor requestTokenBearerInterceptor(LoginService loginService) {
        OAuth2RestTemplate oauth2RestTemplate = new OAuth2RestTemplate(loginService.getCurrentOAuth2ProtectedResourceDetails(),
                                                                       new DefaultOAuth2ClientContext(new DefaultAccessTokenRequest()));
        return requestTemplate -> {
            OAuth2AccessToken accessToken = oauth2RestTemplate.getAccessToken();

            requestTemplate.header("Authorization",
                                   "Bearer " + accessToken.getValue());
        };
    }

    @Bean
    public Encoder encoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new PageableQueryEncoder(new SpringEncoder(messageConverters));
    }

    /**
     * This encoder adds support for pageable, which will be applied to the query parameters.
     */
    private class PageableQueryEncoder implements Encoder {

        private final Encoder delegate;

        PageableQueryEncoder(Encoder delegate) {
            this.delegate = delegate;
        }

        @Override
        public void encode(Object object,
                           Type bodyType,
                           RequestTemplate template) throws EncodeException {

            if (object instanceof Pageable) {
                Pageable pageable = (Pageable) object;
                template.query("page",
                               String.valueOf(pageable.getPageNumber()));
                template.query("size",
                               String.valueOf(pageable.getPageSize()));

                if (pageable.getSort() != null) {
                    Collection<String> existingSorts = template.queries().get("sort");
                    List<String> sortQueries = existingSorts != null ? new ArrayList<>(existingSorts) : new ArrayList<>();
                    for (Sort.Order order : pageable.getSort()) {
                        sortQueries.add(order.getProperty() + "," + order.getDirection());
                    }
                    template.query("sort",
                                   sortQueries);
                }
            } else {
                delegate.encode(object,
                                bodyType,
                                template);
            }
        }
    }
}