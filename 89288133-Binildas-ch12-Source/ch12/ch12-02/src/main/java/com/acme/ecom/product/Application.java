/*
 * Copyright (c) 2024/2025 Binildas A Christudas & Apress
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.acme.ecom.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:biniljava<[@.]>yahoo.co.in">Binildas C. A.</a>
 */
@SpringBootApplication
@RestController
public class Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    private static volatile long times = 0L;

    @RequestMapping("/")
    public String home() {

    	LOGGER.info("Start");
        ++times;
    	LOGGER.debug("Inside hello.Application.home() : {}", times);
        LOGGER.info("Returning...");
	    return "Hello Docker World : " + times;
    }

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
        LOGGER.info("Started...");
    }
}
