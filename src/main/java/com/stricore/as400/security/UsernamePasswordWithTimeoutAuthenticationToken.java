/*
 * UsernamePasswordWithTimeoutAuthenticationToken.java
 *
 * Copyright 2013 Fundación Indesoft indesoft.org.ve.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stricore.as400.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * @date 04/08/2013
 *
 * @version 1.0
 * @author Jose Luis Estevez jose.estevez.prieto@gmail.com
 * jestevez@indesoft.org.ve
 */
public class UsernamePasswordWithTimeoutAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String timeout;

    public UsernamePasswordWithTimeoutAuthenticationToken(Object principal,
            Object credentials) {
        super(principal, credentials);
        this.timeout = null;
    }

    public UsernamePasswordWithTimeoutAuthenticationToken(Object principal,
            Object credentials, String timeout) {
        this(principal, credentials);
        this.timeout = timeout;
    }

    public String getTimeout() {
        return timeout;
    }
}