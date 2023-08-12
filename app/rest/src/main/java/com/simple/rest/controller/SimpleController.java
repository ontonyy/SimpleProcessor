package com.simple.rest.controller;

import java.util.Set;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "SimpleController", description = "Simple Controller API")
@RequestMapping(path = "/api/simple")
public class SimpleController {
    private final SimpleService simpleService;

    @PostMapping("/create")
    public ResponseEntity<SimpleBigResponseDto> create(@Valid @RequestBody final SimpleCreateRequest createRequest) {
        final SimpleBigResponseDto simpleResponse = simpleService.create(createRequest);
        return ResponseEntity.ok(simpleResponse);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<SimpleBigResponseDto> find(@PathVariable(name = "id") final String id) {
        final SimpleBigResponseDto simpleResponse = simpleService.find(id);
        return ResponseEntity.ok(simpleResponse);
    }

    @GetMapping("/hello")
    public ResponseEntity<SimpleResponseDto> hello() {
        final SimpleResponseDto simpleResponse = simpleService.doSimple();
        return ResponseEntity.ok(simpleResponse);
    }

    @GetMapping("/beans")
    @Operation(summary = "Get beans of application, especially will be config beans")
    public ResponseEntity<Set<String>> getBeanNames() {
        final Set<String> beanNames = simpleService.getBeanNames();
        return ResponseEntity.ok(beanNames);
    }
}
