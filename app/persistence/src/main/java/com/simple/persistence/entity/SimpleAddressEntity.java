package com.simple.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Document("Address")
@Data
@Builder
@EqualsAndHashCode(of = "id")
public class SimpleAddressEntity {
    @Id
    private String id;

    private String name;
    private String city;
    private String country;
    private String index;
}
