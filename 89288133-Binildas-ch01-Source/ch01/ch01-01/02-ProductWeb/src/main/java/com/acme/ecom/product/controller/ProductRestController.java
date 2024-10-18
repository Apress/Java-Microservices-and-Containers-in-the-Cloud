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
import com.acme.ecom.product.db.InMemoryDB;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
 * @author <a href="mailto:biniljava<[@.]>yahoo.co.in">Binildas C. A.</a>
 */
@CrossOrigin
@RestController
public class ProductRestController{

	@Autowired
	private InMemoryDB inMemoryDB;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductRestController.class);

    @RequestMapping(value = "/productsweb", method = RequestMethod.GET ,produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Product>> getAllProducts() {

    	LOGGER.info("Start");
        List<Product> products = inMemoryDB.getAllProducts();
        if(products.isEmpty()){
    		LOGGER.debug("No products retreived from in-memory repository");
            return new ResponseEntity<List<Product>>(HttpStatus.NOT_FOUND);
        }
        List<Product> list = new ArrayList<Product> ();
        for(Product product:products){
            list.add(product);
        }
        list.forEach(item->LOGGER.debug(item.toString()));
    	LOGGER.info("Ending");
        return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/productsweb/{productId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProduct(@PathVariable("productId") String productId) {
        
    	LOGGER.info("Start");
    	LOGGER.debug("Fetching Product with id: {}", productId);

        Product product = inMemoryDB.getProduct(productId);
        if (product == null) {
    		LOGGER.debug("Product with id: {} not found in in-memory repository", productId);
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
    	LOGGER.info("Ending");
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/productsweb", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        
    	LOGGER.info("Start");
    	LOGGER.debug("Creating Product with id: {}", product.getProductId());

        Product productFound = inMemoryDB.getProduct(product.getProductId());
        if (null != productFound) {
    		LOGGER.debug("A Product with code {} already exist", product.getCode());
            return new ResponseEntity<Product>(HttpStatus.CONFLICT);
        }

        inMemoryDB.addProduct(product);
    	LOGGER.info("Ending");
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }


    @RequestMapping(value = "/productsweb/{productId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> deleteProduct(@PathVariable("productId")String productId) {
    	 
    	LOGGER.info("Start");
    	LOGGER.debug("Fetching & Deleting Product with id: {}", productId);
        Product productFound = inMemoryDB.getProduct(productId);
        if (productFound == null) {
    		LOGGER.debug("Product with id: {} not found, hence not deleted", productId);
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }

        inMemoryDB.deleteProduct(productId);
    	LOGGER.info("Ending");
        return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
	}

    @RequestMapping(value = "/productsweb/{productId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> updateProduct(@PathVariable("productId")String productId, @RequestBody Product product) {
    	 
    	LOGGER.info("Start");
    	LOGGER.debug("Updating Product with id: {}", productId);

        Product currentProduct = inMemoryDB.getProduct(productId);

        if (currentProduct == null) {
    		LOGGER.debug("Product with id: {} not found", productId);
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }

        currentProduct.setName(product.getName());
        currentProduct.setCode(product.getCode());
        currentProduct.setTitle(product.getTitle());
        currentProduct.setPrice(product.getPrice());

		//We have already updated product in-memory by reference, still...
		Product newProduct = inMemoryDB.updateProduct(currentProduct);
    	LOGGER.info("Ending");
        return new ResponseEntity<Product>(newProduct, HttpStatus.OK);
	}

    @RequestMapping(value = "/productsweb/{productId}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> patchProduct(@PathVariable("productId")String productId, @RequestBody Product product) {
    	 
    	LOGGER.info("Start");
    	LOGGER.debug("Updating Price of Product with id: {}", productId);

        Product currentProduct = inMemoryDB.getProduct(productId);

        if (currentProduct == null) {
    		LOGGER.debug("Product with id: {} not found", productId);
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }

        if(product.getName() != null){
            currentProduct.setName(product.getName());
        }
        if(product.getCode() != null){
            currentProduct.setCode(product.getCode());
        }
        if(product.getTitle() != null){
            currentProduct.setTitle(product.getTitle());
        }
        if(product.getPrice() > 0.0){
            currentProduct.setPrice(product.getPrice());
        }

		Product newProduct = inMemoryDB.updateProduct(currentProduct);
    	LOGGER.info("Ending");
        return new ResponseEntity<Product>(newProduct, HttpStatus.OK);
	}
}
