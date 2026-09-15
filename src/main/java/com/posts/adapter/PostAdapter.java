package com.posts.adapter;

import com.posts.dto.PostDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface PostAdapter {

    PostDTO getById(@NotNull Integer id);

    List<PostDTO> getByUserId(@NotNull Integer id);

    PostDTO add(@Valid @NotNull PostDTO dto);

    PostDTO update(@Valid @NotNull PostDTO dto);

    void delete(@NotNull Integer id);
}
