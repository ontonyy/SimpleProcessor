package com.simple.models.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SimpleCreateRequest {
    @NotNull private String firstName;
    @NotNull private String lastName;
    @NotNull private String password;
    @NotNull private String email;
    @NotNull private String gender;
    @NotNull private SimpleAddressRequest address;
}
