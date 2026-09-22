package com.example.helloworld;

import java.time.Instant;

/**
 * Event published to Kafka when a greeting is requested.
 * hello-world is the producer — greeting-service is the consumer.
 */
public record GreetingRequestedEvent(
        String requestId,
        String message,
        Instant timestamp
) {}
