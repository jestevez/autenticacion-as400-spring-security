/*
 * AuthController.java
 *
 * Copyright 2012 SIOS www.sios.com.ve.
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
package com.stricore.as400.security.controller;

import com.stricore.as400.security.CustomAuthenticationProvider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @date 27/08/2012
 *
 * @version 1.0
 * @author Jose Luis Estevez jestevez@indesoft.org.ve
 * jose.estevez.prieto@gmail.com
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    CustomAuthenticationProvider authenticationProvider;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage(ModelMap model, HttpServletRequest request, HttpSession session) {
        String timeout = authenticationProvider.calculateNonce();
        session.setAttribute("timeout", timeout);
        model.addAttribute("timeout", timeout);

        try {
            if (request.getParameter("message") != null) {
                String message = "";
                long error = Long.parseLong(request.getParameter("message"));
                if (error == 1) {
                    message = "Usuario o contraseña no válidos!";
                } else if (error == 2) {
                    message = "Sesión cerrada con éxito";
                } else if (error == 3) {
                    message = "Su sesión expiro!";
                } else if (error == 4) {
                    message = "Su sesión expiro!";
                } else if (error == 5) {
                    message = "Su sesión se ha cerrado por que ha sido abierta desde otro sitio!";
                }
                request.getSession().setAttribute("levelerror", message);
            }
        } catch (Exception e) {
        }
        return "/auth/login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String getLogoutPage(ModelMap model, HttpServletRequest request, HttpSession session) {
        session.removeAttribute("username");
        session.invalidate();

        return "/auth/logout";
    }

    @RequestMapping(value = "/denied", method = RequestMethod.GET)
    public String getDeniedPage(@RequestParam(value = "error", required = false) boolean error,
            ModelMap model) {

        return "/auth/denied";
    }

}
