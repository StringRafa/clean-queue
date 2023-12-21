package com.panamby.cleanqueue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.panamby.cleanqueue.model.QueueName;
import com.panamby.cleanqueue.service.RabbitMQService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RabbitMQController {
	
	@Autowired
	private RabbitMQService rabbitMQService;

    @PostMapping("/queue-cleaning")
    public ResponseEntity<String> queueCleaning(@RequestBody QueueName nameQueue) {
        
    	log.info(String.format("Queue Cleaning Controller started. QUEUE [%s]", nameQueue));
    	
        return ResponseEntity.ok(rabbitMQService.queueCleaning(nameQueue));
    }
}
   