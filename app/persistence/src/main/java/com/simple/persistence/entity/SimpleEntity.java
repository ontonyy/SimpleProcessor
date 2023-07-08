package com.simple.persistence.entity;

import java.time.ZonedDateTime;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.simple.models.enums.Gender;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Document
@Data
@Builder
@EqualsAndHashCode(of = "id")
public class SimpleEntity {
    @Id private String id;

    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Gender gender;
    private SimpleAddressEntity address;
    private Set<TripEntity> trips;
    private ZonedDateTime createdTime;
}
