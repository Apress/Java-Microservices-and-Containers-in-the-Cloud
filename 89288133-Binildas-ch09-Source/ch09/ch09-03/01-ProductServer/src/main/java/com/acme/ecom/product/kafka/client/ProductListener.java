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
package com.acme.ecom.product.kafka.client;   

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.acme.ecom.product.model.Product;
import com.acme.ecom.product.model.ProductOR;
import com.acme.ecom.product.model.Products;
import com.acme.ecom.product.repository.ProductRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:biniljava<[@.]>yahoo.co.in">Binildas C. A.</a>
 */
@Component
public class ProductListener {
	
	@Autowired
	private ProductRepository productRepository;

    @Autowired
    private ProductMapper mapper;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductListener.class);

    @KafkaListener(topics = "${kafka.topic.product.request}", containerFactory = "requestReplyListenerContainerFactory")
    @SendTo
    public Products listenConsumerRecord(ConsumerRecord<String, Products> record){
        
        long secondsToSleep = 3;
        
        LOGGER.info("Start");

        //print all headers
        record.headers().forEach(header -> LOGGER.debug(header.key() + ":" + new String(header.value())));
        
        String key = record.key();
        Products products = record.value();
        LOGGER.debug("Listen; key : " + key);
        LOGGER.debug("Listen; value : " + products);
        
        Products productsToReturn = resolveAndExecute(products);

        LOGGER.info("Ending");
        return productsToReturn;
        
    }
    
    private Products resolveAndExecute(Products products) {
    	
    	LOGGER.info("Start");
    	Products productsToReturn = null;

    	if(products.getOperation().equals(Products.RETREIVE_DETAILS)){
    		productsToReturn = getProduct(products);
    	}
    	else if(products.getOperation().equals(Products.RETREIVE_ALL)){
    		productsToReturn = getAllProducts(products);
    	}
    	else if(products.getOperation().equals(Products.CREATE)){
    		productsToReturn = createProduct(products);
    	}
    	else if(products.getOperation().equals(Products.UPDATE)){
    		productsToReturn = updateProduct(products);
    	}
    	else if(products.getOperation().equals(Products.DELETE)){
    		productsToReturn = deleteProduct(products);
    	}
    	else {
    		LOGGER.debug("Inside else. Undefined Operation!");
    	}
    	LOGGER.info("Ending");
    	return productsToReturn;
    }

    //------------------- Retreive a Product --------------------------------------------------------
    private Products getProduct(Products products) {

    	LOGGER.info("Start");
    	Products productsToReturn = new Products();
    	if((null != products) && (products.getProducts().iterator().hasNext())) {
    		String productId = ((Product) products.getProducts().iterator().next()).getProductId();
    		LOGGER.debug("Fetching Product with productId : {}", productId);

        	ProductOR productOR = productRepository.findById(Long.parseLong(productId)).get();

    		productsToReturn.setOperation(Products.SUCCESS);
            if (productOR == null) {
        		LOGGER.debug("Product with productId : {} not found in repository", productId);
            }
            else {
            	LOGGER.debug("Product with productId : {} found in repository", productId);
            	List<Product> productListToReturn = new ArrayList<Product>();
            	productListToReturn.add(mapper.entityToApi(productOR));
            	productsToReturn.setProducts(productListToReturn);
            }
    	}
    	else {
    		LOGGER.debug("Product cannot be fetched, since param is null or empty");
    		productsToReturn.setOperation(Products.FAILURE);
    	}
    	LOGGER.info("Ending");
        return productsToReturn;
    }

    //------------------- Create a Product --------------------------------------------------------
    private Products createProduct(Products products) {

    	LOGGER.info("Start");
    	Products productsToReturn = new Products();
    	List<Product> productListToReturn = null;
    	Product productToCreate = null;
    	List<ProductOR> productsFound = null;
    	ProductOR productCreatedOR = null;
    	
    	if((null != products) && (products.getProducts().iterator().hasNext())) {

    		productToCreate = products.getProducts().iterator().next();
    		LOGGER.debug("Attempting to create a new Product with code: {}", productToCreate.getCode());
    		
			productsFound = productRepository.findByCode(productToCreate.getCode());
            if (productsFound.size() > 0) {
        		LOGGER.debug("A Product with code {} already exist", productsFound.iterator().next().getCode());
        		productsToReturn.setOperation(Products.FAILURE);
            }
            else {
				productCreatedOR = productRepository.save(mapper.apiToEntity(productToCreate));
            	LOGGER.debug("A Product with id {} created newly", productCreatedOR.getProductId());
            	productsToReturn.setOperation(Products.SUCCESS);
            	productListToReturn = new ArrayList<Product>();
            	productListToReturn.add(mapper.entityToApi(productCreatedOR));
            	productsToReturn.setProducts(productListToReturn);
            }
    	}
    	else {
    		LOGGER.debug("Product cannot be created, since param is null or empty");
    		productsToReturn.setOperation(Products.FAILURE);
    	}
    	LOGGER.info("Ending");
        return productsToReturn;
    }

    //------------------- Update a Product --------------------------------------------------------
    private Products updateProduct(Products products) {

    	LOGGER.info("Start");
    	Products productsToReturn = new Products();
    	List<Product> productListToReturn = null;
    	Product productToUpdate = null;
    	ProductOR productFoundOR = null;
    	ProductOR productUpdatedOR = null;
    	
    	if((null != products) && (products.getProducts().iterator().hasNext())) {

    		productToUpdate = products.getProducts().iterator().next();
    		LOGGER.debug("Attempting to find a Product with id: {} to update", productToUpdate.getProductId());
    		
    		productFoundOR = productRepository.findById(Long.parseLong(productToUpdate.getProductId())).get();
            if (null != productFoundOR) {
        		LOGGER.debug("A Product with id {} exist, attempting to update", productFoundOR.getProductId());
        		productsToReturn.setOperation(Products.SUCCESS);
				productUpdatedOR = productRepository.save(mapper.apiToEntity(productToUpdate));
            	productListToReturn = new ArrayList<Product>();
            	productListToReturn.add(mapper.entityToApi(productUpdatedOR));
            	productsToReturn.setProducts(productListToReturn);
            }
            else {
            	LOGGER.debug("A Product with id {} doesn't exist", productToUpdate.getProductId());
            	productsToReturn.setOperation(Products.FAILURE);
            }
    	}
    	else {
    		LOGGER.debug("Product cannot be updated, since param is null or empty");
    		productsToReturn.setOperation(Products.FAILURE);
    	}
    	LOGGER.info("Ending");
        return productsToReturn;
    }

    //------------------- Delete a Product --------------------------------------------------------
    private Products deleteProduct(Products products) {

    	LOGGER.info("Start");
    	Products productsToReturn = new Products();
    	List<Product> productListToReturn = null;
    	Product productToDelete = null;
    	ProductOR productFoundOR = null;
    	
    	if((null != products) && (products.getProducts().iterator().hasNext())) {

    		productToDelete = products.getProducts().iterator().next();
    		LOGGER.debug("Attempting to find a Product with id: {} to delete", productToDelete.getProductId());
    		
    		productFoundOR = productRepository.findById(Long.parseLong(productToDelete.getProductId())).get();
            if (null != productFoundOR) {
        		LOGGER.debug("A Product with id {} exist, attempting to delete", productFoundOR.getProductId());
        		productRepository.delete(mapper.apiToEntity(productToDelete));
        		productsToReturn.setOperation(Products.SUCCESS);
            	productListToReturn = new ArrayList<Product>();
            	productsToReturn.setProducts(productListToReturn);
            }
            else {
            	LOGGER.debug("A Product with id {} doesn't exist", productToDelete.getProductId());
            	productsToReturn.setOperation(Products.FAILURE);
            }
    	}
    	else {
    		LOGGER.debug("Product cannot be deleted, since param is null or empty");
    		productsToReturn.setOperation(Products.FAILURE);
    	}
    	LOGGER.info("Ending");
        return productsToReturn;
    }

    //------------------- Retreive all Products --------------------------------------------------------
    private Products getAllProducts(Products products) {

    	LOGGER.info("Start");
    	Products productsToReturn = new Products();
		Iterable<ProductOR> iterable = productRepository.findAll();

        List<Product> productListToReturn = new ArrayList<Product> ();
        for(ProductOR productOR:iterable){
            productListToReturn.add(mapper.entityToApi(productOR));
        }
        if(productListToReturn.size() == 0){
    		LOGGER.debug("No products retreived from repository");
        }       
        productListToReturn.forEach(item->LOGGER.debug(item.toString()));

    	productsToReturn.setOperation(Products.SUCCESS);
    	productsToReturn.setProducts(productListToReturn);
    	
    	//delay();
    	LOGGER.info("Ending");
        return productsToReturn;
    }

    private void delay() {
        
        long secondsToSleep = 1;
        LOGGER.info("Start");
        LOGGER.debug(Thread.currentThread().toString());
        LOGGER.debug("Starting to Sleep Seconds : " + secondsToSleep);

        try{
            Thread.sleep(1000 * secondsToSleep);
        }
        catch(Exception e) {
            LOGGER.error("Error : " + e);
        }
        LOGGER.debug("Awakening from Sleep...");
    }
}
