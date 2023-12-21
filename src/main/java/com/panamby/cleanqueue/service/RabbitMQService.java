package com.panamby.cleanqueue.service;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panamby.cleanqueue.model.QueueName;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RabbitMQService {

	@Autowired
    private AmqpAdmin amqpAdmin;
	
    public String queueCleaning(QueueName nameQueueObject) {
        
    	log.info(String.format("Queue Cleaning Service started. QUEUES [%s]", nameQueueObject));
    	int purgeQueueMessage = 0;
    	int countMessagesPurged = 0;
    	
    	for (String nameQueue : nameQueueObject.getQueueName()) {
        	
        	purgeQueueMessage = amqpAdmin.purgeQueue(nameQueue);
        	countMessagesPurged += purgeQueueMessage;
            
        	log.info(String.format("%s messages successfully deleted from the queue %s", purgeQueueMessage, nameQueue));
		}
        
    	log.info(String.format("Queue Cleaning Service finished. QUEUES [%s] - MESSAGES_PURGED [%s]",
    			nameQueueObject.getQueueName(), countMessagesPurged));
    	
		return String.format("%s messages successfully deleted from the queues %s", countMessagesPurged, nameQueueObject.getQueueName());
    }
}