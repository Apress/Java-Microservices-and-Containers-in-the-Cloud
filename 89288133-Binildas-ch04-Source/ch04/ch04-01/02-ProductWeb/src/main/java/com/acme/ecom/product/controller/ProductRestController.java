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
import com.acme.ecom.product.model.Products;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author <a href="mailto:biniljava<[@.]>yahoo.co.in">Binildas C. A.</a>
 */
@CrossOrigin
@RestController
public class ProductRestController{

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductRestController.class);

	private static final String REQUEST_PAYLOAD = "All";//Get All Data

	@Autowired
	ReplyingKafkaTemplate<String, String,Products> kafkaTemplate;
	
	  @Value("${kafka.topic.product.request}")
	  private String requestTopic;
	  
	  @Value("${kafka.topic.product.reply}")
	  private String replyTopic;
	
    @RequestMapping(value = "/productsweb", method = RequestMethod.GET ,produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CollectionModel<EntityModel<Product>>> getAllProducts() throws InterruptedException, ExecutionException {

    	LOGGER.info("Start");
		LOGGER.debug("Thread : " + Thread.currentThread());

    	// create a producer record
		ProducerRecord<String, String> record = new ProducerRecord<String, String>(requestTopic, REQUEST_PAYLOAD);
		//ProducerRecord<String, String> record = new ProducerRecord<String, String>(requestTopic, 0, appName, REQUEST_PAYLOAD);
		
		// set a reply topic in header
		record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, replyTopic.getBytes()));
		
		// post in a kafka topic
		RequestReplyFuture<String, String, Products> sendAndReceive = kafkaTemplate.sendAndReceive(record);

		// confirm if the producer produced successfully
		SendResult<String, String> sendResult = sendAndReceive.getSendFuture().get();
		
		//print all the headers
		sendResult.getProducerRecord().headers().forEach(header -> LOGGER.info(header.key() + ":" + header.value().toString()));
		
		// get the consumer record
		ConsumerRecord<String, Products> consumerRecord = sendAndReceive.get();
		
		LOGGER.debug("Reply success -> " + consumerRecord.value());
		LOGGER.debug("Thread : " + Thread.currentThread());
		LOGGER.info("Ending...");
		// return the consumer value
		return new ResponseEntity(consumerRecord.value().getProducts(),HttpStatus.OK);		

    }

}
