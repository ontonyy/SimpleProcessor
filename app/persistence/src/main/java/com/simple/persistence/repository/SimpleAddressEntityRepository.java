package com.simple.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.simple.persistence.entity.SimpleAddressEntity;

@Repository
public interface SimpleAddressEntityRepository extends MongoRepository<SimpleAddressEntity, String> {
}
