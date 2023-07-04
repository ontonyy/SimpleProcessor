package com.simple.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.simple.persistence.entity.SimpleEntity;

public interface SimpleRepository extends MongoRepository<SimpleEntity, String> {

}
