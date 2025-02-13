package com.deepak.sharma.authservice.events.producer;

import com.deepak.sharma.authservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class UserProducer {
    private final KafkaTemplate<String, User> kafkaTemplate;

    @Value("${kafka.topic.user}")
    private String topic;

    public void sendEventToKafka(User user) {
        Message<User> message = MessageBuilder.withPayload(user).setHeader(KafkaHeaders.TOPIC, topic).build();
        kafkaTemplate.send(message);
    }

    @Autowired
    public UserProducer(KafkaTemplate<String, User> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
}
