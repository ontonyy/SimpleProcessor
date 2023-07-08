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
    @NotNull String firstName;
    @NotNull String lastName;
    @NotNull String password;
    @NotNull String email;
    @NotNull String gender;
    @NotNull SimpleAddressRequest address;
}
