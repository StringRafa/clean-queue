package com.panamby.cleanqueue.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panamby.cleanqueue.model.CleanQueueResponse;
import com.panamby.cleanqueue.model.QueueName;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RabbitMQService {

	@Autowired
    private AmqpAdmin amqpAdmin;
	
    public CleanQueueResponse queueCleaning(QueueName nameQueueObject) {
        
    	log.info(String.format("Queue Cleaning Service started. QUEUES [%s]", nameQueueObject));
    	CleanQueueResponse cleanQueueResponse = new CleanQueueResponse();    	
    	List<String> messagesSuccessfullyDeletedList = new ArrayList<>();
    	List<String> failuresWhenDeletingMessagesList = new ArrayList<>();
    	int purgeQueueMessage = 0;
    	int countMessagesPurged = 0;
    	
    	for (String nameQueue : nameQueueObject.getQueueName()) {
        	
        	try {
        		
				purgeQueueMessage = amqpAdmin.purgeQueue(nameQueue);
	        	
	        	countMessagesPurged += purgeQueueMessage;
	            
	        	String responseMessage = String.format("%s messages successfully deleted from the queue %s", purgeQueueMessage, nameQueue);
        	
	        	messagesSuccessfullyDeletedList.add(responseMessage);
	        	
				log.info(responseMessage);
			} catch (AmqpException e) {
				
				String errorResponseMessage = String.format("Queue %s not found. ERROR_MESSAGE [%s]",
						nameQueue, e.getMessage());
				
				failuresWhenDeletingMessagesList.add(errorResponseMessage);
				
				log.error(errorResponseMessage, e);
			}
		}
        
    	log.info(String.format("Queue Cleaning Service finished. QUEUES [%s] - MESSAGES_PURGED [%s]",
    			nameQueueObject.getQueueName(), countMessagesPurged));
    	
		String responseMessage = String.format("%s messages successfully deleted from the queues.", countMessagesPurged);
		
		cleanQueueResponse = new CleanQueueResponse(messagesSuccessfullyDeletedList, failuresWhenDeletingMessagesList, responseMessage);
		
		return cleanQueueResponse;
    }
}