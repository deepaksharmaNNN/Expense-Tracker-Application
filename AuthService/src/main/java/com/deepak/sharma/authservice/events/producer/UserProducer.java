package com.deepak.sharma.authservice.events.producer;

import com.deepak.sharma.authservice.dto.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class UserProducer {
    private final KafkaTemplate<String, UserInfoDto> kafkaTemplate;

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    public void sendEventToKafka(UserInfoDto user) {
        Message<UserInfoDto> message = MessageBuilder.withPayload(user).setHeader(KafkaHeaders.TOPIC, topicName).build();
        kafkaTemplate.send(message);
    }

    @Autowired
    public UserProducer(KafkaTemplate<String, UserInfoDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
}
