package com.posts.service;

import com.posts.dto.PostDTO;
import com.posts.mapper.PostMapper;
import com.posts.model.Post;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PostExternalApiServiceImpl extends ExternalApiAbstractService<Post, PostDTO> {

    private static final String ENTITY = "posts";
    private final PostMapper postMapper;

    public PostExternalApiServiceImpl(final PostMapper postMapper, final RestTemplate restTemplate) {
        super(restTemplate);
        this.postMapper = postMapper;
    }

    @Override
    public String getEntityPath() {
        return ENTITY;
    }

    @Override
    public String getValidationMsg(final Integer id, final String url) {
        return String.format("Post not found with id : %d. URL: %s", id, url);
    }

    @Override
    PostMapper getMapper() {
        return postMapper;
    }
}
