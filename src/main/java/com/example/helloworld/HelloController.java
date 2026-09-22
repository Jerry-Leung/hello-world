package com.example.helloworld;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.UUID;

@RestController
public class HelloController {

    @Value("${HELLO_MESSAGE:Hello World 5}")
    private String message;

    @Value("${DB_PASSWORD:no-password-set}")
    private String dbPassword;

    @Value("${GREETING_SERVICE_URL:http://greeting-service}")
    private String greetingServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final GreetingEventProducer greetingEventProducer;

    public HelloController(GreetingEventProducer greetingEventProducer) {
        this.greetingEventProducer = greetingEventProducer;
    }

    @GetMapping("/")
    public String hello() {
        // 1. Synchronous REST call to greeting-service (existing behaviour)
        String greeting = restTemplate.getForObject(greetingServiceUrl + "/greeting", String.class);

        // 2. Publish async event to Kafka
        greetingEventProducer.publish(new GreetingRequestedEvent(
                UUID.randomUUID().toString(),
                message,
                Instant.now()
        ));

        return message + " " + greeting;
    }

    @GetMapping("/secret-demo")
    public String secretDemo() {
        return "DB password length: " + dbPassword.length();
    }

}
