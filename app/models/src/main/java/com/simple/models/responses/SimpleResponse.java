package com.simple.models.responses;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String gender;
    private String sub;
    private Set<String> trips;
    private String createdTime;
}
