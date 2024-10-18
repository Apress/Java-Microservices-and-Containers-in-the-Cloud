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

import com.acme.ecom.product.model.Products;
import com.acme.ecom.product.model.Product;

import se.callista.blog.synch_kafka.request_reply_util.CompletableFutureReplyingKafkaOperations;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.CrossOrigin;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.CompletableFuture;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;

/**
 * @author <a href="mailto:biniljava<[@.]>yahoo.co.in">Binildas C. A.</a>
 */
@CrossOrigin
@RestController
public class ProductRestController{

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductRestController.class);

	  @Autowired
	  private CompletableFutureReplyingKafkaOperations<String, Products, Products> replyingKafkaTemplate;

	  @Autowired
	  private ModelMapper modelMapper;

	  @Value("${kafka.topic.product.request}")
	  private String requestTopic;
	  
	  @Value("${kafka.topic.product.reply}")
	  private String requestReplyTopic;
	
	//------------------- Retreive all Products --------------------------------------------------------
    @RequestMapping(value = "/productsweb", method = RequestMethod.GET ,produces = {MediaType.APPLICATION_JSON_VALUE})
	public DeferredResult<ResponseEntity<CollectionModel<com.acme.ecom.product.hateoas.model.Product>>>  getAllProducts(){

    	LOGGER.info("Start");
		DeferredResult<ResponseEntity<CollectionModel<com.acme.ecom.product.hateoas.model.Product>>> deferredResult = new DeferredResult<>();

		Products productsRequest = new Products();
		productsRequest.setOperation(Products.RETREIVE_ALL);
        
		CompletableFuture<Products> completableFuture =  replyingKafkaTemplate.requestReply(requestTopic, productsRequest);
        
        completableFuture.thenAccept(products -> {
        	
        	List<com.acme.ecom.product.model.Product> productList = products.getProducts();
        	
			Link links[] = { linkTo(methodOn(ProductRestController.class).getAllProducts()).withSelfRel(),
					linkTo(methodOn(ProductRestController.class).getAllProducts()).withRel("getAllProducts") };

			List<com.acme.ecom.product.hateoas.model.Product> list = new ArrayList<com.acme.ecom.product.hateoas.model.Product>();
			for (com.acme.ecom.product.model.Product product : productList) {
				com.acme.ecom.product.hateoas.model.Product productHateoas = convertEntityToHateoasEntity(product);
				list.add(productHateoas
						.add(linkTo(methodOn(ProductRestController.class).getProduct(productHateoas.getProductId()))
								.withSelfRel()));
			}
			list.forEach(item -> LOGGER.debug(item.toString()));
			CollectionModel<com.acme.ecom.product.hateoas.model.Product> result = CollectionModel.of(list, links);
			
			deferredResult.setResult(new ResponseEntity<CollectionModel<com.acme.ecom.product.hateoas.model.Product>>(result, HttpStatus.OK));
        	
        }).exceptionally(ex -> {
        	LOGGER.error(ex.getMessage());
        	return null;
        });
        
        //delay();
        
        LOGGER.info("Ending");
        return deferredResult;
    }
    
    private void delay() {
        
        long secondsToSleep = 6;
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

  //------------------- Retreive a Product --------------------------------------------------------
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public DeferredResult<ResponseEntity<com.acme.ecom.product.hateoas.model.Product>> getProduct(@PathVariable("id") String id) {
    	
    	LOGGER.info("Start");
    	LOGGER.debug("Fetching Product with id: {}", id);
		LOGGER.info("Thread : " + Thread.currentThread());
		DeferredResult<ResponseEntity<com.acme.ecom.product.hateoas.model.Product>> deferredResult = new DeferredResult<>();

		Products productsRequest = new Products();
		productsRequest.setOperation(Products.RETREIVE_DETAILS);
		Product product = new Product();
		product.setProductId(id);
		List<Product> productRequestList = new ArrayList<>();
		productRequestList.add(product);
		productsRequest.setProducts(productRequestList);
		
        CompletableFuture<Products> completableFuture =  replyingKafkaTemplate.requestReply(requestTopic, productsRequest);
        
        completableFuture.thenAccept(products -> {
        	
        	List<Product> productList = products.getProducts();
        	Product productRetreived = null;
        	String productId = null;

            if (productList.iterator().hasNext()) {
            	productRetreived = productList.iterator().next();
            	productId = productRetreived.getProductId();
            	LOGGER.debug("Product with productId : {} retreived from Backend Microservice", productId);

				com.acme.ecom.product.hateoas.model.Product productHateoas = convertEntityToHateoasEntity(productRetreived);
				productHateoas.add(linkTo(methodOn(ProductRestController.class).getProduct(productHateoas.getProductId())).withSelfRel());

            	deferredResult.setResult(new ResponseEntity<com.acme.ecom.product.hateoas.model.Product>(productHateoas, HttpStatus.OK));

            }
            else {
            	LOGGER.debug("Product with productId : {} not retreived from Backend Microservice", id);
            	deferredResult.setResult(new ResponseEntity<com.acme.ecom.product.hateoas.model.Product>(HttpStatus.NOT_FOUND));
            }
           
        }).exceptionally(ex -> {
        	LOGGER.error(ex.getMessage());
        	return null;
        });
    	
        LOGGER.info("Ending");
        return deferredResult;
    }

    //------------------- Create a Product --------------------------------------------------------
    @RequestMapping(value = "/productsweb", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public DeferredResult<ResponseEntity<com.acme.ecom.product.hateoas.model.Product>> addProduct(@RequestBody Product product) {
    	
    	LOGGER.info("Start");
    	LOGGER.debug("Creating Product with code: {}", product.getCode());
    	
		LOGGER.info("Thread : " + Thread.currentThread());
		DeferredResult<ResponseEntity<com.acme.ecom.product.hateoas.model.Product>> deferredResult = new DeferredResult<>();

		Products productsRequest = new Products();
		productsRequest.setOperation(Products.CREATE);
		List<Product> productRequestList = new ArrayList<>();
		productRequestList.add(product);
		productsRequest.setProducts(productRequestList);
        
        CompletableFuture<Products> completableFuture =  replyingKafkaTemplate.requestReply(requestTopic, productsRequest);
        
        completableFuture.thenAccept(products -> {
        	
        	List<Product> productList = products.getProducts();
        	Product productRetreived = null;
        	String productId = null;

            if (productList.iterator().hasNext()) {
            	productRetreived = productList.iterator().next();
            	productId = productRetreived.getProductId();
            	LOGGER.debug("Product with productId : {} created by Backend Microservice", productId);

				com.acme.ecom.product.hateoas.model.Product productHateoas = convertEntityToHateoasEntity(productRetreived);
				productHateoas.add(linkTo(methodOn(ProductRestController.class).getProduct(productHateoas.getProductId())).withSelfRel());
            	deferredResult.setResult(new ResponseEntity<com.acme.ecom.product.hateoas.model.Product>(productHateoas, HttpStatus.OK));

            }
            else {
            	LOGGER.debug("Product with code : {} not created by Backend Microservice", product.getCode());
				deferredResult.setResult(new ResponseEntity<com.acme.ecom.product.hateoas.model.Product>(HttpStatus.CONFLICT));
            }
           
        }).exceptionally(ex -> {
        	LOGGER.error(ex.getMessage());
        	return null;
        });
    	
        LOGGER.info("Ending");
        return deferredResult;
    }

    //------------------- Update a Product --------------------------------------------------------
    @RequestMapping(value = "/productsweb/{productId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public DeferredResult<ResponseEntity<com.acme.ecom.product.hateoas.model.Product>> updateProduct(@PathVariable("productId")String id, @RequestBody Product product) {
    	
    	LOGGER.info("Start");
    	LOGGER.debug("Updating Product with id: {}", id);
        
		LOGGER.info("Thread : " + Thread.currentThread());
		DeferredResult<ResponseEntity<com.acme.ecom.product.hateoas.model.Product>> deferredResult = new DeferredResult<>();

		Products productsRequest = new Products();
		productsRequest.setOperation(Products.UPDATE);
		List<Product> productRequestList = new ArrayList<>();
		productRequestList.add(product);
		productsRequest.setProducts(productRequestList);
        
        CompletableFuture<Products> completableFuture =  replyingKafkaTemplate.requestReply(requestTopic, productsRequest);
        
        completableFuture.thenAccept(products -> {
        	
        	List<Product> productList = products.getProducts();
        	Product productRetreived = null;
        	String productId = null;

            if (productList.iterator().hasNext()) {
            	productRetreived = productList.iterator().next();
            	productId = productRetreived.getProductId();
            	LOGGER.debug("Product with productId : {} updated by Backend Microservice", productId);

				com.acme.ecom.product.hateoas.model.Product productHateoas = convertEntityToHateoasEntity(productRetreived);
				productHateoas.add(linkTo(methodOn(ProductRestController.class).getProduct(productHateoas.getProductId())).withSelfRel());
            	deferredResult.setResult(new ResponseEntity<com.acme.ecom.product.hateoas.model.Product>(productHateoas, HttpStatus.OK));

            }
            else {
            	LOGGER.debug("Product with code : {} not updated by Backend Microservice", id);
				deferredResult.setResult(new ResponseEntity<com.acme.ecom.product.hateoas.model.Product>(HttpStatus.NOT_FOUND));
            }


        }).exceptionally(ex -> {
        	LOGGER.error(ex.getMessage());
        	return null;
        });
        LOGGER.info("Ending");
        return deferredResult;
    }

    //------------------- Delete a Product --------------------------------------------------------
    @RequestMapping(value = "/productsweb/{productId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public DeferredResult<ResponseEntity<Product>> deleteProduct(@PathVariable("productId")String id) {
    	
    	LOGGER.info("Start");
    	LOGGER.debug("Deleting Product with id: {}", id);
        
		LOGGER.info("Thread : " + Thread.currentThread());
		DeferredResult<ResponseEntity<Product>> deferredResult = new DeferredResult<>();

		Products productsRequest = new Products();
		productsRequest.setOperation(Products.DELETE);
		List<Product> productRequestList = new ArrayList<>();
		Product productToDelete = new Product();
		productToDelete.setProductId(id);
		productToDelete.setName("");
		productToDelete.setCode("");
		productToDelete.setTitle("");
		productToDelete.setPrice(0D);
		productRequestList.add(productToDelete);
		productsRequest.setProducts(productRequestList);
        
        CompletableFuture<Products> completableFuture =  replyingKafkaTemplate.requestReply(requestTopic, productsRequest);
        
        completableFuture.thenAccept(products -> {

            if (products.getOperation().contentEquals(Products.SUCCESS)) {
            	LOGGER.debug("Product with productId : {} deleted by Backend Microservice", id);
            	deferredResult.setResult(new ResponseEntity<Product>(HttpStatus.NO_CONTENT));

            }
            else {
            	LOGGER.debug("Product with id : {} suspected not deleted by Backend Microservice", id);
            	deferredResult.setResult(new ResponseEntity<Product>(HttpStatus.NOT_FOUND));
            }
           
        }).exceptionally(ex -> {
        	LOGGER.error(ex.getMessage());
        	return null;
        });
        LOGGER.info("Ending");
        return deferredResult;
    }

	private com.acme.ecom.product.hateoas.model.Product convertEntityToHateoasEntity(com.acme.ecom.product.model.Product product){
		return  modelMapper.map(product,  com.acme.ecom.product.hateoas.model.Product.class);
	}
}
