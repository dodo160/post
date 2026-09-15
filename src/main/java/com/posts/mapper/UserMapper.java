package com.posts.mapper;

import com.posts.dto.UserDTO;
import com.posts.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper extends CommonMapper<User, UserDTO> {
}
