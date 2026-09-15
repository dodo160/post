package com.posts.mapper;

import java.util.List;

public interface CommonMapper<MODEL, DTO> {

    DTO toDto(MODEL entity);

    MODEL fromDto(DTO dto);

    List<DTO> toDtoList(List<MODEL> list);

    List<MODEL> fromDtoList(List<DTO> list);
}
