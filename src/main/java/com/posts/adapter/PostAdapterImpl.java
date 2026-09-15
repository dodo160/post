package com.posts.adapter;

import com.posts.dto.PostDTO;
import com.posts.mapper.PostMapper;
import com.posts.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostAdapterImpl implements PostAdapter {

    private final PostService postService;
    private final PostMapper postMapper;

    public PostAdapterImpl(PostService postService, PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }


    @Override
    public PostDTO getById(final Integer id) {
        return postMapper.toDto(postService.findByIdSaveFromExternalAPI(id));
    }

    @Override
    public List<PostDTO> getByUserId(final Integer id) {
        return postMapper.toDtoList(postService.findByUserId(id));
    }

    @Override
    public PostDTO add(final PostDTO dto) {
        return postMapper.toDto(postService.add(postMapper.fromDto(dto)));
    }

    @Override
    public PostDTO update(final PostDTO dto) {
        return postMapper.toDto(postService.update(postMapper.fromDto(dto)));
    }

    @Override
    public void delete(final Integer id) {
        postService.delete(id);
    }
}
