# Apache Kafka Spring Boot Example

## I - Creation d'une application spring boot
- Spring web
- Spring for apache kafka
- Spring boot dev tools

## II - Activer kafka dans la classe principale avec @EnableKafka

```java
package com.dev.spring.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class SpringBoot2ApacheKafkaTestApplication {

      public static void main(String[] args) {
         SpringApplication.run(SpringBoot2ApacheKafkaTestApplication.class, args);
      }
}
```

## III - Creer un custom MessageRepository class

```java
package com.dev.spring.kafka.message.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MessageRepository {

       private List<String> list = new ArrayList<>();

       public void addMessage(String message) {
          list.add(message);
       }

       public String getAllMessages() {
          return list.toString();
       }
}
```

## IV - Creer une classe Message Producer

```java
package com.dev.spring.kafka.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

      private Logger log = LoggerFactory.getLogger(MessageProducer.class);

      @Autowired 
      private KafkaTemplate<String, String> kafkaTemplate;

      @Value("${myapp.kafka.topic}")
      private String topic;

      public void sendMessage(String message) {
         log.info("MESSAGE SENT FROM PRODUCER END -> " + message);
         kafkaTemplate.send(topic, message);
      }
}
```

## V - Creer une classe MessageConsumer

```java
package com.dev.spring.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.dev.spring.kafka.message.repository.MessageRepository;

@Component
public class MessageConsumer {

      private Logger log = LoggerFactory.getLogger(MessageConsumer.class);

      @Autowired
      private MessageRepository messageRepo;

      @KafkaListener(topics = "${myapp.kafka.topic}", groupId = "xyz")
      public void consume(String message) {
         log.info("MESSAGE RECEIVED AT CONSUMER END -> " + message);
         messageRepo.addMessage(message);
      }
}

```

## VI - Creer un RestController avec le nom KafkaRestController

```java
package com.dev.spring.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dev.spring.kafka.message.repository.MessageRepository;
import com.dev.spring.kafka.sender.MessageProducer;

@RestController
public class KafkaRestController {

      @Autowired
      private MessageProducer producer;

      @Autowired
      private MessageRepository messageRepo;

      //Send message to kafka
      @GetMapping("/send")
      public String sendMsg(
      @RequestParam("msg") String message) {
          producer.sendMessage(message);
          return "" +"'+message +'" + " sent successfully!";
      }

      //Read all messages
      @GetMapping("/getAll")
      public String getAllMessages() {
         return messageRepo.getAllMessages() ;
      }
}
```

## VII - Creer le fichier proprietiés application.yml

```yaml
server:
  port: 9090

spring:
  kafka:
    producer:
      bootstrap-servers: localhost:9092
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer                
    consumer:
      bootstrap-servers: localhost:9092
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
        
myapp:
    kafka:
      topic: myKafkaTest

```
## VIII - Commandes

### 1 - Demarrer Zookeeper

```bash
bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
```

### 2 - Kafka setup

```bash
.\bin\windows\kafka-server-start.bat .\config\server.properties
```
### 3 - Creer un topic
```bash
bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic kafkaBalita
```

### 4 - Urls pour verification

- http:// localhost:9090/send?msg=I like
- http:// localhost:9090/send?msg=to work on
- http:// localhost:9090/send?msg=Kafka
- http:// localhost:9090/send?msg=with Spring Boot

- http://localhost:9090/getAll

### 4 - Client pour apache kafka

http://www.kafkatool.com/
