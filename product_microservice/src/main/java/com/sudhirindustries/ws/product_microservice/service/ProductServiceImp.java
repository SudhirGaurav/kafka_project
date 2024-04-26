package com.sudhirindustries.ws.product_microservice.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.sudhirindustries.ws.codecommon.ProductCreatedEvent;
import com.sudhirindustries.ws.product_microservice.controller.CreateProductModel;

@Service // This will inform Spring about this class is Servie class . At the time of start Spring appl , onject of this class will be created and ADDED into Spring context. This u can get this object with dependecy injectin and get this object in other class (for Ex: rest controller)
public class ProductServiceImp implements ProductService {
	private final Logger logger= LoggerFactory.getLogger( this.getClass());
	KafkaTemplate<String,ProductCreatedEvent> kafkaTemplate;// This is used t publish event as key-value pair to kafka topic
	
	// KafkaTemplate is already intergrated with Spring fw. this obj ll be created at the time of startup of the project
	public ProductServiceImp(KafkaTemplate<String,ProductCreatedEvent> kafkaTemplate) { // this is constructor based dependency injection
		this.kafkaTemplate = kafkaTemplate;
	}
	
	
	@Override
	public String createProduct(CreateProductModel product) {
		String productid = UUID.randomUUID().toString(); // It ll generate random and unique uuid 
		
		//TODO: persist the data into db table before pushing an event 
		
		//Creating product
		ProductCreatedEvent createdEvent = new ProductCreatedEvent(productid, product.getTitle(),product.getPrice(),product.getQuantity());
		
		//*************************************Asynchronous call*****************************************
		/*
		CompletableFuture<org.springframework.kafka.support.SendResult<String, ProductCreatedEvent>> completableFuture=	kafkaTemplate.send("product_create_event_topic", productid, createdEvent);// This ll send msg asynchronously . Means Producer will not wait for the response from the kafka broker 
		completableFuture.whenComplete((result, exception) ->{
			
			if(exception != null) {
				logger.error("Failed tp send message " +exception.getMessage());
			}else {
				logger.info("message sent successfully : "+result.getRecordMetadata());
			}
			
		} );
		completableFuture.join(); // this line will make above call to synchronous [IMp]
		*/
		//*************************************************************************************
	
		
		//********************************Synchronous call *************************************
		SendResult<String, ProductCreatedEvent> result = null;
		try {
			 result=	kafkaTemplate.send("product_create_event_topic", productid, createdEvent).get();
		} catch (InterruptedException | ExecutionException e) {
			logger.error("Publishng message to kafka topic failed ....");
			e.printStackTrace();
		}

		int partition =result.getRecordMetadata().partition();
		String topic = result.getRecordMetadata().topic();
		Long offset =result.getRecordMetadata().offset();
		logger.info("Event sent to kafka topic : "+ topic+ " partition : "+partition + "offset : "+offset);
		
		//*****************************************************************************************
		logger.info("Returning productid:  : "+productid);

		return productid;
	}

}
