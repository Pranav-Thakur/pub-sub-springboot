package com.example.pubsub.dao.model;

import com.example.pubsub.dao.constants.AppUserStatus;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "app_user")
public class AppUserDO {
    @Id
    @GeneratedValue
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;
    private String name;
    @Lob
    private String info;

    @Enumerated(EnumType.STRING)
    private AppUserStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Integer version;
}
