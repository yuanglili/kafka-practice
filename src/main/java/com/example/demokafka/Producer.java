package com.example.demokafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class Producer implements ProducerService {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    public static final String TOPIC = "test-topic";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private int counter = 0;

    public Producer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void sendMessage(String key, int id, String text) {
        try {
            Message msg = Message.builder().id(id).text(text).build();
            String json = objectMapper.writeValueAsString(msg);

            kafkaTemplate.send(TOPIC, key, json)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            logger.error("Failed to send | id={} | reason={}", id, ex.getMessage());
                        } else {
                            logger.info("Message sent | topic={} | partition={} | offset={} | id={} | text={}",
                                    result.getRecordMetadata().topic(),
                                    result.getRecordMetadata().partition(),
                                    result.getRecordMetadata().offset(),
                                    id, text);
                        }
                    });

        } catch (Exception e) {
            logger.error("Failed to  send | id={} | reason={}", id, e.getMessage());
        }
    }

    @Scheduled(fixedRate = 100)
    public void scheduledSend() {
        sendMessage("key-" + counter, counter, "hello-" + counter);
        counter++;
    }

//    @Scheduled(fixedRate = 10000)
//    public void batchSend() {
//        for (int i = 0; i < 10000; i++) {
//            sendMessage("batch-key-" + i, i, "batch-message-" + i);
//        }
//        logger.info("Batch sent 10000 messages");
//    }
}