package com.example.helloworld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class GreetingEventProducer {

    private static final Logger log = LoggerFactory.getLogger(GreetingEventProducer.class);
    private static final String TOPIC = "greeting-events";

    private final KafkaTemplate<String, GreetingRequestedEvent> kafkaTemplate;

    public GreetingEventProducer(KafkaTemplate<String, GreetingRequestedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(GreetingRequestedEvent event) {
        kafkaTemplate.send(TOPIC, event.requestId(), event);
        log.info("Published GreetingRequestedEvent to topic '{}': requestId={}", TOPIC, event.requestId());
    }
}
