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
package com.acme.ecom.product.service;


import org.springframework.stereotype.Service;

import com.acme.ecom.product.model.Product;
import com.acme.ecom.product.model.ProductCategory;

import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;

import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * A Business Service class, intended to hold all business processing, and
 * further intended to be reused by all Driver Ports
 *
 * @author <a href="mailto:biniljava<[@>.]yahoo.co.in">Binildas C. A.</a>
 */
@Service
public class ProductBusinessService{

	@Value("${acme.PRODUCT_SERVICE_URL}")
	private String PRODUCT_SERVICE_URL;

	@Value("${acme.PRODUCT_SERVICE_BY_CAT_URL}")
	private String PRODUCT_SERVICE_BY_CAT_URL;

	@Value("${acme.PRODUCT_CATEGORY_URL}")
	private String PRODUCT_CATEGORY_URL;

	@Autowired
	private RestTemplate restTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductBusinessService.class);

    public List<Product> getAllProducts() {

        LOGGER.info("Start. I am instance: {}", this);
        ParameterizedTypeReference<List<Product>> responseTypeRef = new ParameterizedTypeReference<List<Product>>() {};
		ResponseEntity<List<Product>> entity = restTemplate.exchange(PRODUCT_SERVICE_URL, HttpMethod.GET,
				(HttpEntity<Product>) null, responseTypeRef);
		List<Product> productList = entity.getBody();
		LOGGER.info("Ending...");
		return productList;
    }

    public Product getProduct(String productId) {
        
        LOGGER.info("Start. I am instance: {}", this);
		String uri = PRODUCT_SERVICE_URL + "/" + productId;
		Product product = restTemplate.getForObject(uri, Product.class);
		LOGGER.info("Ending...");
        return product;

    }
    
    public Product addProduct(Product product) {
        
        LOGGER.info("Start. I am instance: {}", this);
		Product productNew = restTemplate.postForObject( PRODUCT_SERVICE_URL, product, Product.class);
		LOGGER.info("Ending...");
        return productNew;
    }

	public void deleteProduct(String productId) {
    	 
        LOGGER.info("Start. I am instance: {}", this);
		restTemplate.delete(PRODUCT_SERVICE_URL + "/" + productId);
		LOGGER.info("Ending.");
	}

	public Product updateProduct(String productId, Product product) {
    	 
        LOGGER.info("Start. I am instance: {}", this);
		String uri = PRODUCT_SERVICE_URL + "/" + productId;		
		LOGGER.debug("Attempting to update Product with ID : {} ...", productId);
		restTemplate.put(uri, product, Product.class);
		LOGGER.debug("Product with ID : {} updated. Retreiving updated product back...", productId);
		Product updatedProduct = restTemplate.getForObject(uri, Product.class);
		LOGGER.info("Ending...");
		return updatedProduct;
	}

    public List<Product> getProductsForCategory(String name) {

        LOGGER.info("Start. I am instance: {}", this);
        ParameterizedTypeReference<List<Product>> responseTypeRef = new ParameterizedTypeReference<List<Product>>() {};
		LOGGER.debug("Fetching all products of category: {}", name);
        ResponseEntity<List<Product>> entity = restTemplate.exchange(PRODUCT_SERVICE_BY_CAT_URL + "/" + name, HttpMethod.GET,
				(HttpEntity<Product>) null, responseTypeRef);
		List<Product> productList = entity.getBody();
		LOGGER.info("Ending...");
        return productList;
    }

    public ProductCategory getProductCategory(String productCategoryName) {

        LOGGER.info("Start. I am instance: {}", this);
		String uri = PRODUCT_CATEGORY_URL + "/" + productCategoryName;
		LOGGER.debug("Fetching Category of product with category name: {}", productCategoryName);
        ProductCategory productCategory = restTemplate.getForObject(uri, ProductCategory.class);
		LOGGER.info("Ending...");
        return productCategory;
    }
}
