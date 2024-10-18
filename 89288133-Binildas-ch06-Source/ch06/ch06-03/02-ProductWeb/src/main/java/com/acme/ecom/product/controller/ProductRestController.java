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
package com.acme.ecom.product.controller;

import com.acme.ecom.product.model.Product;
import com.acme.ecom.product.model.Products;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.requestreply.AggregatingReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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

	@Autowired
	AggregatingReplyingKafkaTemplate<String, String, Products> kafkaTemplate;
	
	  @Value("${kafka.topic.request-topic}")
	  private String requestTopic;
	  
	  @Value("${kafka.topic.requestreply-topic}")
	  private String requestReplyTopic;
	
    @RequestMapping(value = "/productsweb", method = RequestMethod.GET ,produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Product>> getAllProducts() throws InterruptedException, ExecutionException, TimeoutException {

    	LOGGER.info("Start");
		LOGGER.info("Thread : " + Thread.currentThread());
		ProducerRecord<String, String> record = new ProducerRecord<String, String>(requestTopic, "All");
		record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, requestReplyTopic.getBytes()));
		RequestReplyFuture<String, String, Collection<ConsumerRecord<String, Products>>> sendAndReceive = kafkaTemplate.sendAndReceive(record);

		SendResult<String, String> sendResult = sendAndReceive.getSendFuture().get();
		sendResult.getProducerRecord().headers().forEach(header -> LOGGER.trace(header.key() + ":" + header.value().toString()));
		
		sendAndReceive.getSendFuture().get(30, TimeUnit.SECONDS); 
		ConsumerRecord<String,Collection<ConsumerRecord<String, Products>>> consumerRecords = sendAndReceive.get();
		
		LOGGER.debug("Reply success ->"+consumerRecords.value());
		LOGGER.info("Thread : " + Thread.currentThread());
		Collection<ConsumerRecord<String, Products>> consumerRecord = consumerRecords.value();
		List<Product> products =new ArrayList<Product>();
		for(ConsumerRecord<String, Products> temp:consumerRecord) {
			products.addAll(temp.value().getProducts());
		}
		return new ResponseEntity(products,HttpStatus.OK);		
    }
}
