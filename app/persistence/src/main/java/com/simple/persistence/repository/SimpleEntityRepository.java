package com.simple.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.simple.persistence.entity.SimpleEntity;

@Repository
public interface SimpleEntityRepository extends MongoRepository<SimpleEntity, String> {
}
