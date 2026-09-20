package com.example.helloworld;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Value("${HELLO_MESSAGE:Hello World 5}")
    private String message;

    @Value("${DB_PASSWORD:no-password-set}")
    private String dbPassword;

    @GetMapping("/")
    public String hello() {
        return message;
    }

    @GetMapping("/secret-demo")
    public String secretDemo() {
        return "DB password length: " + dbPassword.length();
    }

}
