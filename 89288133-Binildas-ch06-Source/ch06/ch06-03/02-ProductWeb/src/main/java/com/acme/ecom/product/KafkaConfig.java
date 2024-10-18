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
package com.acme.ecom.product;

import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.listener.GenericMessageListenerContainer;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.requestreply.AggregatingReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.ecom.product.model.Products;

/**
 * @author <a href="mailto:biniljava<[@.]>yahoo.co.in">Binildas C. A.</a>
 */
@Configuration
public class KafkaConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConfig.class);

	 @Value("${spring.kafka.bootstrap-servers}")
	  private String bootstrapServers;

	  @Value("${product.topic.request.numPartitions}")
	  private int numPartitions;
	  
	  @Value("${kafka.topic.requestreply-topic}")
	  private String requestReplyTopic;
	  
	  @Value("${spring.kafka.consumer.group-id}")
	  private String consumerGroup;

	  @Value("${kafka.request-reply.timeout-ms}")
	  private String replyTimeout;

	  @Value("${product.topic.reply.numProviders}")
	  private int numProviders;

	  @Value("${product.topic.reply.aggregatingTimeout}")
	  private long aggregatingTimeout;

	  @Bean
	  public Map<String, Object> producerConfigs() {

	    Map<String, Object> props = new HashMap<>();
	    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
	        bootstrapServers);
	    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
	        StringSerializer.class);
	    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
	    return props;
	  }
	  
	  @Bean
	  public Map<String, Object> consumerConfigs() {

	    Map<String, Object> props = new HashMap<>();
	    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
	    props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);
	    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.acme.ecom.product.model");

	    return props;
	  }

	  @Bean
	  public ProducerFactory<String,String> producerFactory() {

	    return new DefaultKafkaProducerFactory<>(producerConfigs());
	  }
	  
	  @Bean
	  public KafkaTemplate<String, String> kafkaTemplate() {

	    return new KafkaTemplate<>(producerFactory());
	  }
	  
	  @Bean
	  public AggregatingReplyingKafkaTemplate<String, String, Products> replyKafkaTemplate(ProducerFactory<String, String> pf, 
			  KafkaMessageListenerContainer<String, Collection<ConsumerRecord<String, Products>>> replyContainer){
		  
		  AggregatingReplyingKafkaTemplate<String, String, Products> replyTemplate = new AggregatingReplyingKafkaTemplate<>(pf, replyContainer,(list, timeout) -> {

				LOGGER.debug("list.size() : {}; timeout : {}", list.size(), timeout);
				return (list.size() == numProviders) || (timeout);
			});
		  
		  replyTemplate.setReturnPartialOnTimeout​(true);
		  replyTemplate.setSharedReplyTopic(true);
		  replyTemplate.setDefaultReplyTimeout(java.time.Duration.ofSeconds(aggregatingTimeout));
		  return replyTemplate;
	  }

	  @Bean
	    public KafkaMessageListenerContainer<String, Collection<ConsumerRecord<String, Products>>>  replyContainer(ConsumerFactory<String, Collection<ConsumerRecord<String, Products>>> cf) {
	        
			ContainerProperties containerProperties = new ContainerProperties(requestReplyTopic);
	        containerProperties.setAckMode(AckMode.MANUAL);
	      	        return new KafkaMessageListenerContainer<String, Collection<ConsumerRecord<String, Products>>>(cf,
	                containerProperties);
	    } 
	   
	  @Bean
	  public ConsumerFactory<String, Collection<ConsumerRecord<String, Products>>> consumerFactory() {

		  return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	  }
	  
	  @Bean
	  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Collection<ConsumerRecord<String, Products>>>> kafkaListenerContainerFactory() {
		
		ConcurrentKafkaListenerContainerFactory<String, Collection<ConsumerRecord<String, Products>>> factory = new ConcurrentKafkaListenerContainerFactory<>();
	    factory.setConsumerFactory(consumerFactory());
	    factory.setReplyTemplate(kafkaTemplate());
	    return factory;
	  }
	  
	  @Bean
	  public KafkaAdmin admin() {

	    Map<String, Object> configs = new HashMap<>();
	    configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
	    return new KafkaAdmin(configs);
	  }

	  @Bean
	  public NewTopic requestTopic() {

	    Map<String, String> configs = new HashMap<>();
	    configs.put("retention.ms", replyTimeout.toString());
	    return new NewTopic(requestReplyTopic, numPartitions, (short) 1).configs(configs);
	  }
}
