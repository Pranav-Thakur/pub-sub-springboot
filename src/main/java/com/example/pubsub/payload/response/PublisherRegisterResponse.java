package com.example.pubsub.payload.response;

import com.example.pubsub.dao.constants.PublisherStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class PublisherRegisterResponse {
    private UUID publisherId;
    private String publisherName;
    private PublisherStatus publisherStatus;
}
