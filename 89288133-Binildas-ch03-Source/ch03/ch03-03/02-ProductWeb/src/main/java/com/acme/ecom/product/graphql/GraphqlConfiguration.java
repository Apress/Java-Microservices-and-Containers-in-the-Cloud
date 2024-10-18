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
package com.acme.ecom.product.graphql;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:biniljava<[@.]>yahoo.co.in">Binildas C. A.</a>
 */
@Configuration
public class GraphqlConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphqlConfiguration.class);

    @Bean
    public ProductDao productDao(){

            return new ProductDao();
    }

    @Bean
    public ProductCategoryDao productCategoryDao(){

            return new ProductCategoryDao();
    }
}
