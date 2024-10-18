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
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.concurrent.CompletableFuture;
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

	@Autowired
	ReplyingKafkaTemplate<String, String,Products> replyingKafkaTemplate;
	
	  @Value("${kafka.topic.request-topic}")
	  private String requestTopic;
	  
	  @Value("${kafka.topic.requestreply-topic}")
	  private String requestReplyTopic;
	
    @RequestMapping(value = "/productsweb", method = RequestMethod.GET ,produces = {MediaType.APPLICATION_JSON_VALUE})
    public DeferredResult<Products>  getAllProducts() throws InterruptedException, ExecutionException {

    	LOGGER.info("Start");
		DeferredResult<Products> deferredResult = new DeferredResult<>();
		ProducerRecord<String, String> record = new ProducerRecord<String, String>(requestTopic, "All");
		record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, requestReplyTopic.getBytes()));
        record.headers().forEach(header -> LOGGER.debug(header.key() + ":" + header.value().toString()));
		RequestReplyFuture<String, String, Products> sendAndReceive = replyingKafkaTemplate.sendAndReceive(record);
		LOGGER.debug("Thread : " + Thread.currentThread());
		SendResult<String, String> sendResult = sendAndReceive.getSendFuture().get();
		LOGGER.debug("Request success -> " + sendResult);
        sendResult.getProducerRecord().headers().forEach(header -> LOGGER.debug(header.key() + " : " + new String(header.value())));
		LOGGER.debug("Thread : " + Thread.currentThread());
		
        CompletableFuture.supplyAsync(() -> {

			Products products = null;
			try {
				LOGGER.debug(Thread.currentThread().toString());
				products = sendAndReceive.get().value();
				LOGGER.debug(Thread.currentThread().toString());
				return products;
			} catch (InterruptedException | ExecutionException e) {
				LOGGER.error("Error getting result from RequestReplyFuture: ", e);
				return null;
			}
		})
		.thenAcceptAsync(result -> {
			long secondsToSleep = 2;

			LOGGER.debug("Starting to Sleep Seconds: " + secondsToSleep);
			try {
				Thread.sleep(1000 * secondsToSleep);
			} catch (InterruptedException e) {
				LOGGER.error("Error during sleep: " + e.getMessage());
				Thread.currentThread().interrupt();
			}
			LOGGER.debug("Awakening from Sleep...");
			LOGGER.debug(Thread.currentThread().toString());
			deferredResult.setResult(result);
		})
		.exceptionally(ex -> {
			LOGGER.error("Error executing request: ", ex);
			return null;
		});

        /* Below code replaced, when migrated to JDK 17 */
		/*
		sendAndReceive.addCallback(new ListenableFutureCallback<ConsumerRecord<String, Products>>() {

            @Override
            public void onFailure(Throwable ex) {
                LOGGER.debug(Thread.currentThread().toString());
                LOGGER.error(ex.getMessage());
            }

            @Override
            public void onSuccess(ConsumerRecord<String, Products> consumerRecord) {
                long secondsToSleep = 2;

                LOGGER.debug("Starting to Sleep Seconds: " + secondsToSleep);
                try {
                    Thread.sleep(1000 * secondsToSleep);
                } catch (InterruptedException e) {
                    LOGGER.error("Error during sleep: " + e.getMessage());
                    Thread.currentThread().interrupt();
                }
                LOGGER.debug("Awakening from Sleep...");
                LOGGER.debug(Thread.currentThread().toString());
                deferredResult.setResult(consumerRecord.value());
            }
        });
		*/
       
		LOGGER.debug("Thread : " + Thread.currentThread());
		LOGGER.info("Ending...");
		return deferredResult;		
    }
}
