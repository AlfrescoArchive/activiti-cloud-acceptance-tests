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

public class AuthRequest {

    private String client_id;
    private String grant_type;
    private String username;
    private String password;

    public AuthRequest() {}

    public static AuthRequest of(String client_id, String grant_type, String username, String password) {
        return new AuthRequest(client_id, grant_type, username, password); 
    }

    protected AuthRequest(String client_id, String grant_type, String username, String password) {
        super();
        this.client_id = client_id;
        this.grant_type = grant_type;
        this.username = username;
        this.password = password;
    }

    public String getClient_id() {
        return client_id;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public int hashCode() {
        return Objects.hash(client_id, grant_type, password, username);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AuthRequest other = (AuthRequest) obj;
        return Objects.equals(client_id, other.client_id) && Objects.equals(grant_type, other.grant_type)
                && Objects.equals(password, other.password) && Objects.equals(username, other.username);
    }

    public AuthRequest withClientId(String client_id) {
        this.client_id = client_id;

        return this;
    }

    public AuthRequest withGrantType(String grant_type) {
        this.grant_type = grant_type;

        return this;
    }

    public AuthRequest withUsername(String username) {
        this.username = username;

        return this;
    }

    public AuthRequest withPassword(String password) {
        this.password = password;

        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AuthRequest [client_id=").append(client_id).append(", grant_type=").append(grant_type)
                .append(", username=").append(username).append("]");
        return builder.toString();
    }



}