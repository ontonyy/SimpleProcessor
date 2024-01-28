package com.simple.persistence.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Document("Trip")
@Data
@Builder
@EqualsAndHashCode(of = "id")
public class SimpleTripEntity {
    @Id
    private String id;

    private String from;
    private String to;
    private Long amount;
}
