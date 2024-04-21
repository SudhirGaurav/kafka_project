package com.sudhirindustries.ws.product_microservice;

import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

	@Bean  // We added bean because , once this method ll execute TopicBuilder on=bj ll be create and added this bean into Spring context . So That this obj will be available for all microservices 
	NewTopic createTopic() {
		return TopicBuilder.name("product_create_event_topic").partitions(3).replicas(3)
				.configs(Map.of("min.insync.replicas","2")).build();
	}
}
