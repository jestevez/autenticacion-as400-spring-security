/*
 * UsernamePasswordWithTimeoutAuthenticationFilter.java
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @date 04/08/2013
 *
 * @version 1.0
 * @author Jose Luis Estevez jose.estevez.prieto@gmail.com
 * jestevez@indesoft.org.ve
 */
public class UsernamePasswordWithTimeoutAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private String timeoutParameter = "timeout";
    private boolean postOnly;

    @Override
    public void setPostOnly(boolean postOnly) {
        super.setPostOnly(postOnly);
        this.postOnly = postOnly;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {

        if (postOnly && !"POST".equals(request.getMethod())) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: "
                    + request.getMethod());
        }

        String username = obtainUsername(request);
        String password = obtainPassword(request);

        final String timeout = obtainTimeout(request);

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        username = username.trim();

        final UsernamePasswordWithTimeoutAuthenticationToken authRequest = new UsernamePasswordWithTimeoutAuthenticationToken(
                username, password, timeout);

        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected String obtainTimeout(HttpServletRequest request) {
        return request.getParameter(timeoutParameter);
    }

    public void setTimeoutParameter(String timeoutParameter) {
        this.timeoutParameter = timeoutParameter;
    }
}
