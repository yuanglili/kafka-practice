package com.example.demokafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);
    private final ObjectMapper objectMapper;

    public Consumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = Producer.TOPIC, groupId = "demo-group")
    public void listen(String json) {
        try {
            Message message = objectMapper.readValue(json, Message.class);
            logger.info("Message received from topic {} id={} text={}", Producer.TOPIC, message.getId(), message.getText());
        } catch (Exception e) {
            logger.error("Failed to deserialize message: {}", e.getMessage());
        }
    }
}