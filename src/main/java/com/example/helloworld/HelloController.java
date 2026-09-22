package com.example.helloworld;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

    @Value("${HELLO_MESSAGE:Hello World 5}")
    private String message;

    @Value("${DB_PASSWORD:no-password-set}")
    private String dbPassword;

    @Value("${GREETING_SERVICE_URL:http://greeting-service}")
    private String greetingServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/")
    public String hello() {
        String greeting = restTemplate.getForObject(greetingServiceUrl + "/greeting", String.class);
        return message + " " + greeting;
    }

    @GetMapping("/secret-demo")
    public String secretDemo() {
        return "DB password length: " + dbPassword.length();
    }

}
