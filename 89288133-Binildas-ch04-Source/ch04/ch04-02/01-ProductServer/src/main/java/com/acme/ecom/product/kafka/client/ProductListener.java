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
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.Header;
import org.apache.kafka.clients.consumer.ConsumerRecord;

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

    @KafkaListener(topics = "${kafka.topic.request-topic}")
    @SendTo
    public Products listenConsumerRecord(ConsumerRecord<String, String> record){
        
        long secondsToSleep = 6;
        
        LOGGER.info("Start");

        //print all headers
        record.headers().forEach(header -> LOGGER.debug(header.key() + ":" + new String(header.value())));

        String brand = record.key();
        String request = record.value();
        LOGGER.debug("Listen; brand : " + brand);
        LOGGER.debug("Listen; request : " + request);
        List<Product> productList = getAllTheProducts();
        Products products = new Products();
        products.setProducts(productList);
        
        LOGGER.debug("Starting to Sleep Seconds : " + secondsToSleep);
        try{
            Thread.sleep(1000 * secondsToSleep);
        }
        catch(Exception e) {
            LOGGER.error("Error : " + e);
        }
        LOGGER.debug("Awakening from Sleep...");
        LOGGER.info("Ending...");
        
        return products;
    }
    
/*
    @KafkaListener(topics = "${kafka.topic.request-topic}")
    @SendTo
    public Products listenWithHeaders(
        @Payload String request,
        @Header(KafkaHeaders.REPLY_TOPIC) String replyTopic,
        @Header(KafkaHeaders.RECEIVED_TOPIC) String receivedTopic,
        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int receivedPartitionId,
        @Header(KafkaHeaders.CORRELATION_ID) String correlationId,
        @Header(KafkaHeaders.OFFSET) String offset){
        
          long secondsToSleep = 6;
          
          LOGGER.info("Start");
          //System.out.println("Returning products  ->" +request);
          LOGGER.debug("Listen; Request : " + request);
          LOGGER.debug("Listen; replyTopic : " + replyTopic);
          LOGGER.debug("Listen; receivedTopic : " + receivedTopic);
          LOGGER.debug("Listen; receivedPartitionId : " + receivedPartitionId);
          LOGGER.debug("Listen; correlationId : " + correlationId);
          LOGGER.debug("Listen; offset : " + offset);
          //List<Product> productList = productRepository.findAll();
          List<Product> productList = getAllTheProducts();
          Products products=new Products();
          products.setProducts(productList);
          
          LOGGER.debug("Starting to Sleep Seconds : " + secondsToSleep);
          try{
              Thread.sleep(1000 * secondsToSleep);
          }
          catch(Exception e) {
              //Logger.error("Error : " + e);
          }
          LOGGER.debug("Awakening from Sleep...");
          
          return products;
    }
	*/
    
    /*
	@KafkaListener(topics = "${kafka.topic.request-topic}")
	@SendTo
    public Products listen(String request) {
		
		long secondsToSleep = 2;
		
		LOGGER.info("Start");
		//System.out.println("Returning products  ->" +request);
        LOGGER.debug("Listen; Request : " + request);
		//List<Product> productList = productRepository.findAll();
		List<Product> productList = getAllTheProducts();
		Products products=new Products();
		products.setProducts(productList);
		
		LOGGER.debug("Starting to Sleep Seconds : " + secondsToSleep);
		try{
			Thread.sleep(1000 * secondsToSleep);
		}
		catch(Exception e) {
			//Logger.error("Error : " + e);
		}
		LOGGER.debug("Awakening from Sleep...");
		
		return products;
	}
*/
    
	
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
