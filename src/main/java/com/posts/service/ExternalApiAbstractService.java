package com.posts.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.posts.exception.NotFoundException;
import com.posts.mapper.CommonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Validated
public abstract class ExternalApiAbstractService<MODEL, DTO> implements ExternalApiService<MODEL, DTO> {

    @Value("${external.api.service.baseurl}")
    private String baseUrl;

    private Class<DTO> dtoClassType;

    private RestTemplate restTemplate;

    protected ExternalApiAbstractService(final RestTemplate restTemplate) {
        this.dtoClassType = (Class<DTO>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        this.restTemplate = restTemplate;
    }

    abstract String getEntityPath();

    abstract String getValidationMsg(Integer id, String url);

    abstract CommonMapper getMapper();

    private MODEL getResource(final String url) {
        final DTO result = restTemplate.getForObject(url, dtoClassType);
        log.debug("Get resource for url {}", url);
        return (MODEL) getMapper().fromDto(result);
    }

    private List<MODEL> getResources(final String url) {
        final List<DTO> listResources = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<DTO>>() {}).getBody();
        log.debug("Get resources for url {}", url);
        return Objects.nonNull(listResources) ? getMapper().fromDtoList(listResources.stream().map(o -> new ObjectMapper().convertValue(o, dtoClassType)).collect(Collectors.toList())) : Collections.emptyList();
    }

    @Override
    public List<MODEL> getAll() {
        return getResources(baseUrl + getEntityPath());
    }

    @Override
    public MODEL getById(final Integer id) {
        final String url = baseUrl + getEntityPath() + "/" + id;
        try {
            return getResource(url);
        } catch (HttpClientErrorException ex) {
            if (HttpStatus.NOT_FOUND.equals(ex.getStatusCode())) {
                throw new NotFoundException(getValidationMsg(id, url));
            }
            throw ex;
        }
    }
}
