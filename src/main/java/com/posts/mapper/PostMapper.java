package com.posts.mapper;

import com.posts.dto.PostDTO;
import com.posts.model.Post;
import org.mapstruct.Mapper;

@Mapper
public interface PostMapper extends CommonMapper<Post, PostDTO> {
}
