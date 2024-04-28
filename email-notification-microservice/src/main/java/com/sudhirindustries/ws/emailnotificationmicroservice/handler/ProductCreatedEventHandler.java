package com.sudhirindustries.ws.emailnotificationmicroservice.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


import com.sudhirindustries.ws.codecommon.ProductCreatedEvent;

@Component
@KafkaListener(topics="product_create_event_topic" , groupId = "product-created-events" )
public class ProductCreatedEventHandler {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	@KafkaHandler
	 public void methodHandle(ProductCreatedEvent productCreatedEvent) {
		logger.info("Event recceived :"+productCreatedEvent);
			 
	 }
}
