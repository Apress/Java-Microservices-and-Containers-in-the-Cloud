/*
 * Copyright (c) 2023/2024 Binildas A Christudas & Packt
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import com.acme.ecom.product.model.Product;
import com.acme.ecom.product.model.Products;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:biniljava<[@.]>yahoo.co.in">Binildas C. A.</a>
 */
@Component
public class ProductListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductListener.class);
	
	private String consumerGroup;
	 
	@Value("${com.acme.ecom.product.kafka.client.productlistener.sleeptimeout}")
	private long secondsToSleep = 5l;
	
	@KafkaListener(topics = "${kafka.topic.request-topic}")
	@SendTo
    public Products listen(String request) {
		
		LOGGER.info("Start");
		LOGGER.debug("Request : " + request);
		List<Product> productList = getAllTheProducts();
		Products products = new Products();
		products.setProducts(productList);
	
		LOGGER.info("Starting Sleep Seconds : " + secondsToSleep);
		try{
			Thread.sleep(1000 * secondsToSleep);
		}
		catch(Exception e) {
			//Logger.error("Error : " + e);
		}
		LOGGER.info("Awakening from Sleep...");
		LOGGER.info("Ending...");
		return products;
	}


    private Product getTheProduct(String id){

    	Product product = new Product();
    	product.setProductId(id);
    	product.setName("Kamsung D3");
    	product.setCode("KAMSUNG-TRIOS");
    	product.setTitle("Kamsung Trios 12 inch , black , 12 px ....");
    	product.setPrice(12000.00);
    	return product;
	}

    private List<Product> getAllTheProducts(){

		//LOGGER.info("Start");

		List<Product> products = new ArrayList<Product>();

    	Product product = new Product();
    	product.setProductId("1");
    	product.setName("Kamsung D3");
    	product.setCode("KAMSUNG-TRIOS");
    	product.setTitle("Kamsung Trios 12 inch , black , 12 px ....");
    	product.setPrice(12000.00);
    	products.add(product);

    	product = new Product();
    	product.setProductId("2");
    	product.setName("Lokia Pomia");
    	product.setCode("LOKIA-POMIA");
    	product.setTitle("Lokia 12 inch , white , 14px ....");
    	product.setPrice(9000.00);
    	products.add(product);

    	//TODO: Add rest of products and catagories...........
		//LOGGER.info("Ending...");
		return products;
    }
}
