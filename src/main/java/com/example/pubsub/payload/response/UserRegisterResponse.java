package com.example.pubsub.payload.response;

import lombok.Data;

import java.util.UUID;

@Data
public class UserRegisterResponse {
    private String name;
    private UUID id;
}
