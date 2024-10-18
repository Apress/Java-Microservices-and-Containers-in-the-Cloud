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

import se.callista.blog.synch_kafka.request_reply_util.CompletableFutureReplyingKafkaOperations;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.MediaType;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author <a href="mailto:biniljava<[@>.]yahoo.co.in">Binildas C. A.</a>
 */
@CrossOrigin
@RestController
public class ProductRestController{

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductRestController.class);

    private static final String REQUEST_PAYLOAD = "All";//Get All Data

    @Value("${spring.application.name:product-web}")
    private String appName;

    @Value("${kafka.topic.product.pinnedToPartition:false}") 
    private boolean pinnedToPartition;

    @Autowired
    private CompletableFutureReplyingKafkaOperations<String, String, Products> replyingKafkaTemplate;

    @Value("${kafka.topic.product.request}")
    private String requestTopic;

    @RequestMapping(value = "/productsweb", method = RequestMethod.GET ,produces = {MediaType.APPLICATION_JSON_VALUE})
    public DeferredResult<Products>  getAllProducts() throws InterruptedException, ExecutionException {

    	LOGGER.info("Start");
        LOGGER.debug("Application Name : " + appName);
        LOGGER.debug("PinnedToPartition? : " + pinnedToPartition); 
		LOGGER.debug("Thread : " + Thread.currentThread());
		DeferredResult<Products> deferredResult = new DeferredResult<>();
        CompletableFuture<Products> completableFuture =  null;
        if(pinnedToPartition){
            completableFuture =  replyingKafkaTemplate.requestReply(requestTopic, appName, REQUEST_PAYLOAD);
        }
        else{
            completableFuture =  replyingKafkaTemplate.requestReply(requestTopic, REQUEST_PAYLOAD);
        }
        completableFuture.thenAccept(products -> deferredResult.setResult(products))
            .exceptionally(ex -> {
                LOGGER.error(ex.getMessage());
                return null;
            });

        long secondsToSleep = 4;
        LOGGER.debug("Starting to Sleep Seconds : " + secondsToSleep);
        try{
            Thread.sleep(1000 * secondsToSleep);
        }
        catch(Exception e) {
            LOGGER.error("Error : " + e);
        }
        LOGGER.debug("Awakening from Sleep...");

        LOGGER.debug(Thread.currentThread().toString());
        LOGGER.info("Ending");
        return deferredResult;
    }
}
