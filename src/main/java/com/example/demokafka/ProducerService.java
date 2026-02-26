package com.example.demokafka;

public interface ProducerService {
    void sendMessage(String key, int id, String text);
}
