package com.balita.kafkabasics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class KafkabasicsApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkabasicsApplication.class, args);
	}

}
