package com.simple.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simple.models.dto.SimpleBigResponseDto;
import com.simple.models.dto.SimpleResponseDto;
import com.simple.models.requests.SimpleCreateRequest;
import com.simple.service.api.service.SimpleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/simple")
public class SimpleController {
    private final SimpleService simpleService;

    @PostMapping("/create")
    public ResponseEntity<SimpleBigResponseDto> create(@Valid @RequestBody final SimpleCreateRequest userRequest) {
        final SimpleBigResponseDto simpleResponse = simpleService.createSimpleEntity(userRequest);
        return ResponseEntity.ok(simpleResponse);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<SimpleBigResponseDto> find(@PathVariable(name = "id") final Long id) {
        final SimpleBigResponseDto simpleResponse = simpleService.findSimpleEntity(id);
        return ResponseEntity.ok(simpleResponse);
    }

    @GetMapping("/hello")
    public ResponseEntity<SimpleResponseDto> hello() {
        final SimpleResponseDto simpleResponse = simpleService.doSimple();
        return ResponseEntity.ok(simpleResponse);
    }
}
