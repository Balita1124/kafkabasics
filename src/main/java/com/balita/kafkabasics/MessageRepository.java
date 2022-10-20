package com.balita.kafkabasics;

import java.util.*;

import org.springframework.stereotype.Component;

@Component
public class MessageRepository {

	public List<String> list = new ArrayList<>();

	public void addMessage(String message) {
		list.add(message);
	}

	public String getAllMessages() {
		return list.toString();
	}
}
