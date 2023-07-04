package com.simple.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simple.models.requests.SimpleCreateRequest;
import com.simple.models.responses.SimpleResponse;
import com.simple.service.SimpleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class SimpleController {

    private final SimpleService simpleService;

    @PostMapping("/create")
    public ResponseEntity<SimpleResponse> create(@Valid @RequestBody final SimpleCreateRequest userRequest) {
        final SimpleResponse simpleResponse = simpleService.createSimpleEntity(userRequest);
        return ResponseEntity.ok(simpleResponse);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<SimpleResponse> find(@PathVariable(name = "id") final Long id) {
        final SimpleResponse simpleResponse = simpleService.findSimpleEntity(id);
        return ResponseEntity.ok(simpleResponse);
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello world!");
    }
}
