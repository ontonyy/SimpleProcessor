package com.simple.persistence.mapper;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.simple.models.dto.SimpleBigResponseDto;
import com.simple.models.requests.SimpleCreateRequest;
import com.simple.persistence.entity.SimpleEntity;
import com.simple.persistence.entity.TripEntity;
import com.simple.persistence.mapper.config.SimpleMapperConfig;

@Mapper(config = SimpleMapperConfig.class)
public interface ModelsMapper {
    ModelsMapper INSTANCE = Mappers.getMapper(ModelsMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trips", expression = "java(Set.of())")
    @Mapping(target = "createdTime", expression = "java(java.time.ZonedDateTime.now())")
    SimpleEntity convertToSimpleEntity(SimpleCreateRequest userRequest);

    @Mapping(target = "address", expression = "java(simpleEntity.getAddress().toString())")
    @Mapping(target = "gender", expression = "java(simpleEntity.getGender().toString())")
    @Mapping(target = "createdTime", expression = "java(simpleEntity.getCreatedTime().format(java.time.format.DateTimeFormatter.ISO_DATE_TIME))")
    SimpleBigResponseDto convertToSimpleResponse(SimpleEntity simpleEntity);

    Set<String> convertTripsToStrings(Set<TripEntity> trips);
    String convertTripToString(TripEntity trip);
}
