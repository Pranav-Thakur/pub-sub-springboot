package com.example.pubsub.controller;

import com.example.pubsub.payload.request.UserSubscribeTopicRequest;
import com.example.pubsub.payload.request.UserRegisterRequest;
import com.example.pubsub.payload.response.UserRegisterResponse;
import com.example.pubsub.payload.response.UserSubscribeTopicResponse;
import com.example.pubsub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class SubscriberController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public UserRegisterResponse register(@RequestBody UserRegisterRequest request) {
        return service.register(request);
    }

    @PostMapping("/{id}/subscribe")
    public UserSubscribeTopicResponse subscribe(@PathVariable UUID id, @RequestBody UserSubscribeTopicRequest request) {
        return service.subscribe(id, request);
    }

    @GetMapping(value = "/consume", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> subscribe(@RequestParam UUID subscriberId) {
        return Flux.create(emitter -> {
            service.consume(subscriberId, emitter);
        });
    }
}
