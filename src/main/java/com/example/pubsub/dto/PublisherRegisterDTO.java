package com.example.pubsub.dto;

import lombok.Data;

import java.util.Map;

@Data
public class PublisherRegisterDTO {
    private String name;
    private Map<String, Object> data;
}
