package com.balita.kafkabasics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
	private static Logger log = LoggerFactory.getLogger(MessageProducer.class);
	
	@Autowired
	private MessageRepository messageRepository;
	
	@KafkaListener(topics = "${kafkabasics.kafka.topic}", groupId = "xyz")
	public void consume(String message) {
		log.info("MESSAGE RECEIVED AT CONSUMER END -> " + message);
		messageRepository.addMessage(message);
	}
}
