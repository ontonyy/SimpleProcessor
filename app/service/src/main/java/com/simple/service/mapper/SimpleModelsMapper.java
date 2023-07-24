package com.simple.service.mapper;

import java.time.LocalDateTime;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.simple.models.dto.SimpleAddressDto;
import com.simple.models.dto.SimpleBigResponseDto;
import com.simple.models.dto.SimplePersonDto;
import com.simple.models.requests.SimpleAddressRequest;
import com.simple.models.requests.SimpleCreateRequest;
import com.simple.persistence.entity.SimpleAddressEntity;
import com.simple.persistence.entity.SimplePersonEntity;
import com.simple.persistence.entity.SimpleTripEntity;
import com.simple.service.mapper.config.SimpleMapperConfig;

@Mapper(config = SimpleMapperConfig.class)
public interface SimpleModelsMapper {
    SimpleModelsMapper INSTANCE = Mappers.getMapper(SimpleModelsMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tripIds", expression = "java(Set.of())")
    @Mapping(target = "createdTime", expression = "java(java.time.LocalDateTime.now())")
    SimplePersonEntity convertToPersonEntity(SimpleCreateRequest createRequest, String addressId);

    @Mapping(target = "id", ignore = true)
    SimpleAddressEntity convertToAddressEntity(SimpleAddressRequest addressRequest);

    SimpleAddressDto convertToAddressDto(SimpleAddressEntity addressEntity);

    SimplePersonDto convertToPersonDto(SimplePersonEntity personEntity);

    @Mapping(target = "createdTime", expression = "java(createdTime.format(java.time.format.DateTimeFormatter.ISO_DATE_TIME))")
    SimpleBigResponseDto convertToBigResponse(SimplePersonDto person, SimpleAddressDto address, LocalDateTime createdTime);

    Set<String> convertTripsToStrings(Set<SimpleTripEntity> trips);
    String convertTripToString(SimpleTripEntity trip);
}
