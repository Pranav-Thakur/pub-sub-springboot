package com.example.pubsub;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@AutoConfigureWebTestClient
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PubSubFlowTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    static UUID publisherId;
    static UUID topicId;
    static UUID userId;
    static UUID subscriberId;

    @Test
    @Order(1)
    void registerPublisher() throws Exception {
        String body = "{"
                + "\"name\": \"Pranav Super Hero Inc\","
                + "\"data\": {"
                + "\"for\": \"myself\","
                + "\"to\": \"me\""
                + "}"
                + "}";

        MvcResult result = mockMvc.perform(post("/api/v1/publisher/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        publisherId = UUID.fromString(json.get("publisherId").asText());
        Assertions.assertNotNull(publisherId);
    }

    @Test
    @Order(2)
    void createTopic() throws Exception {
        String body = "{"
                + "\"topicName\": \"my-test-topic\","
                + "\"data\": {"
                + "\"buffer\": \"1gb\","
                + "\"loader\": \"1tb\""
                + "}"
                + "}";

        MvcResult result = mockMvc.perform(post("/api/v1/publisher/" + publisherId + "/create-topic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        topicId = UUID.fromString(json.get("topicId").asText());
        Assertions.assertNotNull(topicId);
    }

    @Test
    @Order(3)
    void registerUser() throws Exception {
        String body = "{"
                + "\"name\": \"Kumar Pranav\","
                + "\"data\": {"
                + "\"title\": \"Mr Thakur\","
                + "\"buddhi\": \"140 IQ\""
                + "}"
                + "}";

        MvcResult result = mockMvc.perform(post("/api/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        userId = UUID.fromString(json.get("id").asText());
        Assertions.assertNotNull(userId);
    }

    @Test
    @Order(4)
    void subscribeUserToTopic() throws Exception {
        String body = String.format("{"
                + "\"topicId\": \"%s\","
                + "\"data\": {"
                + "\"subs\": \"to listen to topics\","
                + "\"okay\": \"then listen\""
                + "}"
                + "}", topicId);

        MvcResult result = mockMvc.perform(post("/api/v1/user/" + userId + "/subscribe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk()).andReturn();
        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        subscriberId = UUID.fromString(json.get("subscriberId").asText());
    }

    //@Test
    //@Order(5)
    void shouldReceiveSSEMessageFromFluxSink() throws Exception {
        // Start subscription in the background
        Flux<String> result = webTestClient.get()
                .uri("/api/v1/user/consume?subscriberId=" + subscriberId)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .returnResult(String.class)
                .getResponseBody();

        // Wait a moment for subscription to stabilize
        Thread.sleep(200);

        publishMessage();

        StepVerifier.create(result)
                .expectNext("Hello from test!")
                .thenCancel()
                .verify();
    }

    @Test
    @Order(6)
    void publishMessage() throws Exception {
        String body = String.format("{"
                + "\"topicId\": \"%s\","
                + "\"message\": \"Hello from test!\","
                + "\"data\": {"
                + "\"buffer\": \"1gb\","
                + "\"loader\": \"1tb\""
                + "}"
                + "}", topicId);

        mockMvc.perform(post("/api/v1/publisher/" + publisherId + "/publish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }
}
