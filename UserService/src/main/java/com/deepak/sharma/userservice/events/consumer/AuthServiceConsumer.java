package com.deepak.sharma.userservice.events.consumer;

import com.deepak.sharma.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

@RequiredArgsConstructor
@Slf4j
public class AuthServiceConsumer {

    private final UserRepository userRepository;

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(Object eventData) {
        try {
            log.info("Consumed event: {}", eventData);
        } catch (Exception e) {
            log.error("Error while consuming event", e);
        }
    }
}
