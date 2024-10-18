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
package com.acme.ecom.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/*
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
*/
import java.util.Collections;

/**
 * @author <a href="mailto:biniljava<[@>.]yahoo.co.in">Binildas C. A.</a>
 */
//@Configuration
//@EnableSwagger2
public class SwaggerConfig {
/*
    @Bean
    public Docket api() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
        return docket;
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "Product REST API",
                "This is a REST API of Products, where you can get/add/remove/modify Products.",
                "v1",
                "Terms of service",
                new Contact("Binildas", "www.github.com", "biniljava<[@>.]yahoo.co.in"),
                "License of API", "API license URL", Collections.emptyList()
        );
    }
*/
}
