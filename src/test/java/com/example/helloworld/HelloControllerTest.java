package com.example.helloworld;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(HelloController.class)
@TestPropertySource(properties = {
    "HELLO_MESSAGE=Hello from ConfigMap!",
    "GREETING_SERVICE_URL=http://localhost:9090"
})
class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // GreetingEventProducer requires KafkaTemplate which is not available in the
    // WebMvcTest slice — mock it so Spring can wire HelloController without a broker
    @MockBean
    private GreetingEventProducer greetingEventProducer;

    private WireMockServer wireMockServer;

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer(9090);
        wireMockServer.start();
        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/greeting"))
            .willReturn(WireMock.aResponse()
                .withStatus(200)
                .withBody("Hi from Greeting Service!")));
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void returnsHelloWorld() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello from ConfigMap! Hi from Greeting Service!"));
    }

}
