package com.simple.persistence.entity;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.simple.models.enums.Gender;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Document("Person")
@Builder
@EqualsAndHashCode(of = "id")
public class SimplePersonEntity {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String password;
    private Gender gender;

    @Indexed(unique = true)
    private String email;

    @CreatedDate
    private LocalDateTime createdTime;

    private String addressId;
    private Set<String> tripIds;
}
