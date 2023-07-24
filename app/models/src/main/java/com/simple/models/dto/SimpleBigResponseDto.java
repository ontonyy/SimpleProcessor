package com.simple.models.dto;

public record SimpleBigResponseDto(SimplePersonDto person, SimpleAddressDto address, String createdTime) {
}
