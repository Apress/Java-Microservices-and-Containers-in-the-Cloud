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
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.Header;

import com.acme.ecom.product.model.Product;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductListener.class);

    @KafkaListener(topics = "${kafka.topic.product.request}", containerFactory = "requestReplyListenerContainerFactory")
    @SendTo
    public Products listenWithHeaders(
        @Payload Products requestor,
        @Header(KafkaHeaders.REPLY_TOPIC) String replyTopic,
        @Header(KafkaHeaders.RECEIVED_TOPIC) String receivedTopic,
        @Header(KafkaHeaders.RECEIVED_PARTITION) int receivedPartitionId,
        @Header(KafkaHeaders.CORRELATION_ID) String correlationId,
        @Header(KafkaHeaders.OFFSET) String offset,
		ConsumerRecord<String, Products> record){
                  
          LOGGER.info("Start");

          String key = record.key();
          Products productsRequest = record.value();

          LOGGER.debug("Listen; key : " + key);
		  LOGGER.debug("Listen; replyTopic : " + replyTopic);
          LOGGER.debug("Listen; receivedTopic : " + receivedTopic);
          LOGGER.debug("Listen; receivedPartitionId : " + receivedPartitionId);
          LOGGER.debug("Listen; correlationId : " + correlationId);
          LOGGER.debug("Listen; offset : " + offset);
          Products productsResponse = resolveAndExecute(productsRequest);
          
          LOGGER.info("Ending...");
          
          return productsResponse;
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

    private Products getProduct(Products products) {

    	LOGGER.info("Start");
    	Products productsToReturn = new Products();
    	if((null != products) && (products.getProducts().iterator().hasNext())) {
    		String productId = ((Product) products.getProducts().iterator().next()).getProductId();
    		LOGGER.debug("Fetching Product with productId : {}", productId);
    		Product product = productRepository.findById(productId).get();
    		productsToReturn.setOperation(Products.SUCCESS);
            if (product == null) {
        		LOGGER.debug("Product with productId : {} not found in repository", productId);
            }
            else {
            	LOGGER.debug("Product with productId : {} found in repository", productId);
            	List<Product> productListToReturn = new ArrayList<Product>();
            	productListToReturn.add(product);
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

    private Products createProduct(Products products) {

    	LOGGER.info("Start");
    	Products productsToReturn = new Products();
    	List<Product> productListToReturn = null;
    	Product productToCreate = null;
    	List<Product> productsFound = null;
    	Product productCreated = null;
    	
    	if((null != products) && (products.getProducts().iterator().hasNext())) {

    		productToCreate = products.getProducts().iterator().next();
    		LOGGER.debug("Attempting to create a new Product with code: {}", productToCreate.getCode());
    		
            productsFound = productRepository.findByCode(productToCreate.getCode());
            if (productsFound.size() > 0) {
        		LOGGER.debug("A Product with code {} already exist", productsFound.iterator().next().getCode());
        		productsToReturn.setOperation(Products.FAILURE);
            }
            else {
            	productCreated = productRepository.save(productToCreate);
            	LOGGER.debug("A Product with id {} created newly", productCreated.getProductId());
            	productsToReturn.setOperation(Products.SUCCESS);
            	productListToReturn = new ArrayList<Product>();
            	productListToReturn.add(productCreated);
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

    
    private Products updateProduct(Products products) {

    	LOGGER.info("Start");
    	Products productsToReturn = new Products();
    	List<Product> productListToReturn = null;
    	Product productToUpdate = null;
    	Product productFound = null;
    	Product productUpdated = null;
    	
    	if((null != products) && (products.getProducts().iterator().hasNext())) {

    		productToUpdate = products.getProducts().iterator().next();
    		LOGGER.debug("Attempting to find a Product with id: {} to update", productToUpdate.getProductId());
    		
    		productFound = productRepository.findById(productToUpdate.getProductId()).get();
            if (null != productFound) {
        		LOGGER.debug("A Product with id {} exist, attempting to update", productFound.getProductId());
        		productsToReturn.setOperation(Products.SUCCESS);
        		productUpdated = productRepository.save(productToUpdate);
            	productListToReturn = new ArrayList<Product>();
            	productListToReturn.add(productUpdated);
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
    
    private Products deleteProduct(Products products) {

    	LOGGER.info("Start");
    	Products productsToReturn = new Products();
    	List<Product> productListToReturn = null;
    	Product productToDelete = null;
    	Product productFound = null;
    	Product productUpdated = null;
    	
    	if((null != products) && (products.getProducts().iterator().hasNext())) {

    		productToDelete = products.getProducts().iterator().next();
    		LOGGER.debug("Attempting to find a Product with id: {} to delete", productToDelete.getProductId());
    		
    		productFound = productRepository.findById(productToDelete.getProductId()).get();
            if (null != productFound) {
        		LOGGER.debug("A Product with id {} exist, attempting to delete", productFound.getProductId());
        		productRepository.delete(productToDelete);
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
    
    private Products getAllProducts(Products products) {

    	LOGGER.info("Start");
    	Products productsToReturn = new Products();
    	List<Product> productListToReturn = productRepository.findAll();
    	productsToReturn.setOperation(Products.SUCCESS);
    	productsToReturn.setProducts(productListToReturn);
    	
    	//delay();
    	LOGGER.info("Ending");
        return productsToReturn;
    }

    private void delay() {
        
        long secondsToSleep = 6;

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
