/*
 * CustomAuthenticationFailureHandler.java
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

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.www.NonceExpiredException;

/**
 * @date 04/08/2013
 *
 * @version 1.0
 * @author Jose Luis Estevez jose.estevez.prieto@gmail.com
 * jestevez@indesoft.org.ve
 */
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private String defaultFailureUrl;
    private String expiredUrl;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        final String failureUrl = getFailureUrl(exception);
        if (failureUrl == null) {
            logger.debug("No failure URL set, sending 401 Unauthorized error");

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed: " + exception.getMessage());
        } else {
            saveException(request, exception);

            if (isUseForward()) {
                logger.debug("Forwarding to " + failureUrl);

                request.getRequestDispatcher(failureUrl).forward(request, response);
            } else {
                logger.debug("Redirecting to " + failureUrl);
                getRedirectStrategy().sendRedirect(request, response, failureUrl);
            }
        }
    }

    @Override
    public void setDefaultFailureUrl(String defaultFailureUrl) {
        super.setDefaultFailureUrl(defaultFailureUrl);
        this.defaultFailureUrl = defaultFailureUrl;
    }

    private String getFailureUrl(AuthenticationException exception) {
        if (exception instanceof NonceExpiredException) {
            return expiredUrl;
        }
        return defaultFailureUrl;
    }

    public void setExpiredUrl(String expiredUrl) {
        this.expiredUrl = expiredUrl;
    }
}
