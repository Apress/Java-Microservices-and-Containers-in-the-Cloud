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
package com.acme.ecom.product;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import com.acme.ecom.product.model.Products;

import se.callista.blog.synch_kafka.request_reply_util.CompletableFutureReplyingKafkaOperations;
import se.callista.blog.synch_kafka.request_reply_util.CompletableFutureReplyingKafkaTemplate;

/**
 * @author <a href="mailto:biniljava<[@.]>yahoo.co.in">Binildas C. A.</a>
 */
@Configuration
public class KafkaConfig {
	
	 @Value("${spring.kafka.bootstrap-servers}")
	  private String bootstrapServers;

	  @Value("${spring.kafka.consumer.group-id}")
	  private String groupId;

	  @Value("${kafka.topic.product.request}")
	  private String requestTopic;

	  @Value("${product.topic.request.numPartitions}")
	  private int numPartitions;

	  @Value("${kafka.topic.product.reply}")
	  private String replyTopic;

	  @Value("${kafka.request-reply.timeout-ms}")
	  private Long replyTimeout;

	  @Bean
	  public Map<String, Object> consumerConfigs() {
	    Map<String, Object> props = new HashMap<>();
	    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
	    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
	    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

	    return props;
	  }

	  @Bean
	  public Map<String, Object> producerConfigs() {
	    Map<String, Object> props = new HashMap<>();
	    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
	    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

	    return props;
	  }

	  @Bean
	  public CompletableFutureReplyingKafkaOperations<String, Products, Products> replyKafkaTemplate() {
	    CompletableFutureReplyingKafkaTemplate<String, Products, Products> requestReplyKafkaTemplate =
	        new CompletableFutureReplyingKafkaTemplate<>(requestProducerFactory(),
	            replyListenerContainer());
	    requestReplyKafkaTemplate.setDefaultTopic(requestTopic);
        requestReplyKafkaTemplate.setDefaultReplyTimeout(Duration.of(replyTimeout, ChronoUnit.MILLIS));
	    return requestReplyKafkaTemplate;
	  }

	  @Bean
	  public ProducerFactory<String, Products> requestProducerFactory() {
	    return new DefaultKafkaProducerFactory<>(producerConfigs());
	  }
	  
	  @Bean
	  public ConsumerFactory<String, Products> replyConsumerFactory() {
	    JsonDeserializer<Products> jsonDeserializer = new JsonDeserializer<>();
	    jsonDeserializer.addTrustedPackages(Products.class.getPackage().getName());
	    return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(),
	        jsonDeserializer);
	  }

	  @Bean
	  public KafkaMessageListenerContainer<String, Products> replyListenerContainer() {
	    ContainerProperties containerProperties = new ContainerProperties(replyTopic);
	    return new KafkaMessageListenerContainer<>(replyConsumerFactory(), containerProperties);
	  }

	  @Bean
	  public KafkaAdmin admin() {
	    Map<String, Object> configs = new HashMap<>();
	    configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
	    return new KafkaAdmin(configs);
	  }

	  @Bean
	  public NewTopic replyTopic() {
	    Map<String, String> configs = new HashMap<>();
	    configs.put("retention.ms", replyTimeout.toString());
	    return new NewTopic(replyTopic, numPartitions, (short) 1).configs(configs);
	  }
}
