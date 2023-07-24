package com.simple.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.simple.persistence.entity.SimplePersonEntity;

@Repository
public interface SimplePersonEntityRepository extends MongoRepository<SimplePersonEntity, String> {
}
