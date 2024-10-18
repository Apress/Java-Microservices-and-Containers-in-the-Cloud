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
package com.acme.ecom.product.controller;

import com.acme.ecom.product.model.Product;
import com.acme.ecom.product.service.ProductBusinessService;

//import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;


/**
 * @author <a href="mailto:biniljava<[@>.]yahoo.co.in">Binildas C. A.</a>
 */
@CrossOrigin
@RestController
public class ProductRestController{

	@Autowired
	private ProductBusinessService productBusinessService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductRestController.class);

    @RequestMapping(value = "/productsweb", method = RequestMethod.GET ,produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Product>> getAllProducts() {

        LOGGER.info("Start");

		List<Product> productList = productBusinessService.getAllProducts();

		LOGGER.info("Ending...");
		return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
    }

    @RequestMapping(value = "/productsweb/{productId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProduct(@PathVariable("productId") String productId) {
        
        LOGGER.info("Start");
		Product product = productBusinessService.getProduct(productId);
		LOGGER.info("Ending...");
        return new ResponseEntity<Product>(product, HttpStatus.OK);

    }
    
    @RequestMapping(value = "/productsweb", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        
        LOGGER.info("Start");
		Product addedProduct = productBusinessService.addProduct(product);
		LOGGER.info("Ending...");
        return new ResponseEntity<Product>(addedProduct, HttpStatus.OK);
    }


    @RequestMapping(value = "/productsweb/{productId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> deleteProduct(@PathVariable("productId")String productId) {
    	 
        LOGGER.info("Start");
		productBusinessService.deleteProduct(productId);
		LOGGER.info("Ending...");
        return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
	}

    @RequestMapping(value = "/productsweb/{productId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> updateProduct(@PathVariable("productId")String productId, @RequestBody Product product) {
    	 
        LOGGER.info("Start");
		Product updatedProduct = productBusinessService.updateProduct(productId, product);
		LOGGER.info("Ending...");
		return new ResponseEntity<Product>(updatedProduct, HttpStatus.OK);
	}
}
