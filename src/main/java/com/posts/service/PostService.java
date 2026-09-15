package com.posts.service;

import com.posts.model.Post;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface PostService {

    Post findById(@NotNull Integer id);

    Post add(@Valid @NotNull Post post);

    Post update(@Valid @NotNull Post post);

    void delete(@NotNull Integer id);

    List<Post> findByUserId(@NotNull Integer id);

    Post findByIdSaveFromExternalAPI(@NotNull Integer id);
}
