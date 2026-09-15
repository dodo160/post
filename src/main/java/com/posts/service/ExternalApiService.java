package com.posts.service;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface ExternalApiService<MODEL, DTO> {

    MODEL getById(@NotNull Integer id);

    List<MODEL> getAll();
}
