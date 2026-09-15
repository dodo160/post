package com.posts.service;

import com.posts.dto.UserDTO;
import com.posts.mapper.UserMapper;
import com.posts.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserExternalApiServiceImpl extends ExternalApiAbstractService<User, UserDTO> {

    private static final String ENTITY = "users";

    private final UserMapper userMapper;

    public UserExternalApiServiceImpl(final UserMapper userMapper,  final RestTemplate restTemplate) {
        super(restTemplate);
        this.userMapper = userMapper;
    }

    @Override
    public String getEntityPath() {
        return ENTITY;
    }

    @Override
    String getValidationMsg(final Integer id, final String url) {
        return String.format("User not found with id: %d. URL: %s", id, url);
    }

    @Override
    UserMapper getMapper() {
        return userMapper;
    }
}
