/*
 * DefaultController.java
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

import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/default")
public class DefaultController extends BaseController {

    @Autowired
    ServletContext context;

    @Autowired
    @Qualifier("messageSource")
    ReloadableResourceBundleMessageSource messageSource;

    @RequestMapping(value = "/wait")
    public String getWaitPage(ModelMap model) {
        return "/default/wait";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String getHomePage(ModelMap model) {

        return "/default/home";
    }

    @RequestMapping(value = "/errors", method = RequestMethod.GET)
    public String getErrors(ModelMap model) {

        return "/default/errors";
    }

}
