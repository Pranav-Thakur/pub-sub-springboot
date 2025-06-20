package com.example.pubsub.controller;

import com.example.pubsub.payload.request.PublisherCreateTopicRequest;
import com.example.pubsub.payload.request.PublisherMessageTopicRequest;
import com.example.pubsub.payload.request.PublisherRegisterRequest;
import com.example.pubsub.payload.response.PublisherCreateTopicResponse;
import com.example.pubsub.payload.response.PublisherMessageTopicResponse;
import com.example.pubsub.payload.response.PublisherRegisterResponse;
import com.example.pubsub.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/publisher")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @PostMapping("/register")
    public PublisherRegisterResponse publish(@RequestBody PublisherRegisterRequest registerRequest) {
        return publisherService.register(registerRequest);
    }

    @PostMapping("/{id}/create-topic")
    public PublisherCreateTopicResponse createTopic(@PathVariable UUID id, @RequestBody PublisherCreateTopicRequest request) {
        return publisherService.createTopic(id, request);
    }

    @PostMapping("/{id}/publish")
    public PublisherMessageTopicResponse publishMessageToTopic(@PathVariable UUID id, @RequestBody PublisherMessageTopicRequest request) {
        return publisherService.publishMessage(id, request);
    }
}
