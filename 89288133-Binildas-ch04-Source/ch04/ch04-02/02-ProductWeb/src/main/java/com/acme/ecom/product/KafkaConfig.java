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
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import com.acme.ecom.product.model.Products;

/**
 * @author <a href="mailto:biniljava<[@.]>yahoo.co.in">Binildas C. A.</a>
 */
@Configuration
public class KafkaConfig {
	 @Value("${spring.kafka.bootstrap-servers}")
	  private String bootstrapServers;
	  
	  @Value("${kafka.topic.requestreply-topic}")
	  private String requestReplyTopic;
	  
	  @Value("${spring.kafka.consumer.group-id}")
	  private String consumerGroup;

	  @Value("${kafka.request-reply.timeout-ms}")
	  private String replyTimeout;
	
	  
	  @Bean
	  public Map<String, Object> producerConfigs() {
	    Map<String, Object> props = new HashMap<>();
	    // list of host:port pairs used for establishing the initial connections to the Kakfa cluster
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
        //props.put("request.timeout.ms", 8 * 1000);//Binil
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
	  public ReplyingKafkaTemplate<String, String, Products> replyKafkaTemplate(ProducerFactory<String, String> pf, KafkaMessageListenerContainer<String, Products> container){
          ReplyingKafkaTemplate<String, String, Products> replyKafkaTemplate = new ReplyingKafkaTemplate<>(pf, container);
          replyKafkaTemplate.setDefaultReplyTimeout(Duration.of(9 * 1000, ChronoUnit.MILLIS));
		  return replyKafkaTemplate;
	  }
	  
	  @Bean
	  public KafkaMessageListenerContainer<String, Products> replyContainer(ConsumerFactory<String, Products> cf) {
	        ContainerProperties containerProperties = new ContainerProperties(requestReplyTopic);
	        return new KafkaMessageListenerContainer<>(cf, containerProperties);
	    }
	  
	  @Bean
	  public ConsumerFactory<String, Products> consumerFactory() {
	    return new DefaultKafkaConsumerFactory<>(consumerConfigs(),new StringDeserializer(),new JsonDeserializer<>(Products.class));
	  }
	  
	  @Bean
	  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Products>> kafkaListenerContainerFactory() {
	    
		ConcurrentKafkaListenerContainerFactory<String, Products> factory = new ConcurrentKafkaListenerContainerFactory<>();
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
	    return new NewTopic(requestReplyTopic, 2, (short) 1).configs(configs);
	  }
}
