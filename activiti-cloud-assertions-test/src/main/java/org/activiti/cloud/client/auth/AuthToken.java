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

package org.activiti.cloud.client.auth;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthToken {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("expires_in")
    private Long expiresIn;
    @JsonProperty("refresh_expires_in")
    private Long refreshExpiresIn;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("not-before-policy")
    private Long notBeforePolicy;
    @JsonProperty("session_state")
    private String sessionState;
    @JsonProperty("scope")
    private String scope;

    AuthToken() {}

    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public String toString() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public Long getRefreshExpiresIn() {
        return refreshExpiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }

    public Long getNotBeforPolicy() {
        return notBeforePolicy;
    }

    public String getSessionState() {
        return sessionState;
    }

    public String getScope() {
        return scope;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessToken, expiresIn, notBeforePolicy, refreshExpiresIn, refreshToken, scope,
                            sessionState, tokenType);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AuthToken other = (AuthToken) obj;
        return Objects.equals(accessToken, other.accessToken) && Objects.equals(expiresIn, other.expiresIn)
                && Objects.equals(notBeforePolicy, other.notBeforePolicy)
                && Objects.equals(refreshExpiresIn, other.refreshExpiresIn)
                && Objects.equals(refreshToken, other.refreshToken) && Objects.equals(scope, other.scope)
                && Objects.equals(sessionState, other.sessionState) && Objects.equals(tokenType, other.tokenType);
    }
}