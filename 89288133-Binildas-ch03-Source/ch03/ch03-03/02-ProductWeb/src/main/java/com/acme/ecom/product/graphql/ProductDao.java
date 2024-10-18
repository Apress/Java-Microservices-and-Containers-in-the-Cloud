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

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.ecom.product.model.Product;
import com.acme.ecom.product.service.ProductBusinessService;

/**
 * @author <a href="mailto:biniljava<[@.]>yahoo.co.in">Binildas C. A.</a>
 */
public class ProductDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductDao.class);

	@Autowired
	private ProductBusinessService productBusinessService;

    public List<Product> getProducts(int count, int offset) {


        LOGGER.info("Start");
		List<Product> productList = productBusinessService.getAllProducts();
		LOGGER.info("Ending...");
        return productList.stream().skip(offset).limit(count).collect(Collectors.toList());
    }

    public Product saveProduct(Product product) {

        LOGGER.info("Start");
		Product productNew = productBusinessService.addProduct(product);
		LOGGER.info("Ending...");
		return productNew;
    }

    public List<Product> getProductsForCategory(String name) {

        LOGGER.info("Start");

        ParameterizedTypeReference<List<Product>> responseTypeRef = new ParameterizedTypeReference<List<Product>>() {};
		LOGGER.debug("Fetching all products of category: {}", name);
		List<Product> productList = productBusinessService.getProductsForCategory(name);
		LOGGER.info("Ending...");
        return productList;
    }

}
