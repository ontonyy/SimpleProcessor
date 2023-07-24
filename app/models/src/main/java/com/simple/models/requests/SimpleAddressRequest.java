package com.simple.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public final class SimpleAddressRequest {
    private String name;
    private String city;
    private String country;
    private String index;
}
