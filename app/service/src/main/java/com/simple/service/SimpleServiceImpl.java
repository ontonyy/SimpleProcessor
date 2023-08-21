package com.simple.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.simple.models.dto.SimpleAddressDto;
import com.simple.models.dto.SimpleBigResponseDto;
import com.simple.models.dto.SimplePersonDto;
import com.simple.models.dto.SimpleResponseDto;
import com.simple.models.enums.SimpleKafkaMessageType;
import com.simple.models.kafka.SimpleKafkaRequest;
import com.simple.models.requests.SimpleAddressRequest;
import com.simple.models.requests.SimpleCreateRequest;
import com.simple.persistence.entity.SimpleAddressEntity;
import com.simple.persistence.entity.SimplePersonEntity;
import com.simple.persistence.repository.SimpleAddressEntityRepository;
import com.simple.persistence.repository.SimplePersonEntityRepository;
import com.simple.service.api.cache.SimpleBeanRedisCache;
import com.simple.service.api.cache.SimpleRedisCache;
import com.simple.service.api.publisher.SimpleKafkaPublisher;
import com.simple.service.api.service.SimpleService;
import com.simple.service.mapper.SimpleModelsMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleServiceImpl implements SimpleService {
    private final SimpleKafkaPublisher kafkaPublisher;
    private final SimpleRedisCache redisCache;
    private final SimpleBeanRedisCache beanRedisCache;
    private final SimplePersonEntityRepository personRepository;
    private final SimpleAddressEntityRepository addressRepository;

    @Override
    public SimpleBigResponseDto create(final SimpleCreateRequest createRequest) {
        log.info("Creating new entity from: {}", createRequest);
        kafkaPublisher.publish(createRequest, SimpleKafkaMessageType.SIMPLE_CREATE_REQUEST);

        final SimpleAddressEntity savedAddress = saveAddress(createRequest.getAddress());
        final SimplePersonEntity savedPerson = savePerson(createRequest, savedAddress.getId());

        return createBigResponse(savedPerson, savedAddress);
    }

    @Override
    public SimpleBigResponseDto find(final String id) {
        log.info("Executed finding Simple entity");
        final SimpleKafkaRequest simpleRequest = new SimpleKafkaRequest(String.valueOf(id));
        kafkaPublisher.publish(simpleRequest, SimpleKafkaMessageType.SIMPLE_REQUEST);

        final Optional<SimplePersonEntity> person = personRepository.findById(id);

        if (person.isPresent()) {
            final Optional<SimpleAddressEntity> address = addressRepository.findById(person.get().getAddressId());

            if (address.isPresent()) {
                return createBigResponse(person.get(), address.get());
            } else {
                log.warn("Cannot find address with id {}", person.get().getAddressId());
            }
        } else {
            log.warn("Cannot find person with id {}", id);
        }

        return null;
    }

    @Override
    public void doSimpleRequest(final SimpleKafkaRequest kafkaRequest) {
        log.info("Trying to find simple kafka request in db {}", kafkaRequest);
    }

    @Override
    public void doSimpleCreateRequest(final SimpleCreateRequest createRequest) {
        log.info("Trying to find simple create request in db {}", createRequest);
    }

    @Override
    public SimpleResponseDto doSimple() {
        final String message = "Hello, hello, I am good guy, I think ...";
        final SimpleKafkaRequest kafkaRequest = new SimpleKafkaRequest(message);
        kafkaPublisher.publish(kafkaRequest, SimpleKafkaMessageType.SIMPLE_REQUEST);
        redisCache.hello();
        return new SimpleResponseDto(message);
    }

    @Override
    public Set<String> getBeanNames() {
        return beanRedisCache.get();
    }

    private SimpleAddressEntity saveAddress(final SimpleAddressRequest addressRequest) {
        final SimpleAddressEntity address = SimpleModelsMapper.INSTANCE.convertToAddressEntity(addressRequest);
        return addressRepository.save(address);
    }

    private SimplePersonEntity savePerson(final SimpleCreateRequest createRequest, final String addressId) {
        final SimplePersonEntity person = SimpleModelsMapper.INSTANCE.convertToPersonEntity(createRequest, addressId);
        return personRepository.save(person);
    }

    private SimpleBigResponseDto createBigResponse(final SimplePersonEntity personEntity, final SimpleAddressEntity addressEntity) {
        final SimplePersonDto personDto = SimpleModelsMapper.INSTANCE.convertToPersonDto(personEntity);
        final SimpleAddressDto addressDto = SimpleModelsMapper.INSTANCE.convertToAddressDto(addressEntity);

        return SimpleModelsMapper.INSTANCE.convertToBigResponse(personDto, addressDto, personEntity.getCreatedTime());
    }
}
