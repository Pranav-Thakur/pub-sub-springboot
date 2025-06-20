package com.example.pubsub.payload.request;

import lombok.Data;

import java.util.Map;

@Data
public class UserRegisterRequest {
    private String name;
    private Map<String, Object> data;
}
