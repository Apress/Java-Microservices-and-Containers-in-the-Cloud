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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.ecom.product.model.ProductCategory;
import com.acme.ecom.product.service.ProductBusinessService;

/**
 * @author <a href="mailto:biniljava<[@.]>yahoo.co.in">Binildas C. A.</a>
 */
public class ProductCategoryDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductCategoryDao.class);

	@Value("${acme.PRODUCT_CATEGORY_URL}")
	private String PRODUCT_CATEGORY_URL;

	@Autowired
	private ProductBusinessService productBusinessService;

    public ProductCategory getProductCategory(String productCategoryName) {

        LOGGER.info("Start");
		String uri = PRODUCT_CATEGORY_URL + "/" + productCategoryName;
		LOGGER.debug("Fetching Category of product with category name: {}", productCategoryName);
		ProductCategory productCategory = productBusinessService.getProductCategory(productCategoryName);
		LOGGER.info("Ending...");
        return productCategory;
    }
}
