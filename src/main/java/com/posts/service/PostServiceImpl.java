package com.posts.service;

import com.posts.dto.PostDTO;
import com.posts.dto.UserDTO;
import com.posts.exception.EntityExistsException;
import com.posts.exception.NotFoundException;
import com.posts.model.Post;
import com.posts.model.User;
import com.posts.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ExternalApiService<Post, PostDTO> postApiService;

    private final ExternalApiService<User, UserDTO> userApiService;

    public PostServiceImpl(final PostRepository postRepository, @Qualifier(value = "postExternalApiServiceImpl") final ExternalApiService<Post, PostDTO> postApiService,
                           @Qualifier(value = "userExternalApiServiceImpl") final ExternalApiService<User, UserDTO> userApiService) {
        this.postRepository = postRepository;
        this.postApiService = postApiService;
        this.userApiService = userApiService;
    }

    @Override
    public Post findById(final Integer id) {
        return postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post doesn't exist with id: " + id));
    }

    @Override
    public Post add(final Post post) {
        if (postRepository.existsById(post.getId())) {
            throw new EntityExistsException("Post exists with id: " + post.getId());
        }

        if (Objects.nonNull(userApiService.getById(post.getUserId()))) {
            final Post postDB = postRepository.save(post);
            log.debug("Post has been saved with id: {}", postDB.getId());
            return postDB;
        } else {
            throw new NotFoundException("User not found with id: " + post.getUserId());
        }
    }

    @Override
    @Transactional
    public Post update(final Post post) {
        if (!postRepository.existsById(post.getId())) {
            throw new NotFoundException("Post doesn't exist with id: " + post.getId());
        }
        postRepository.updatePostTitleAndBody(post.getId(), post.getTitle(), post.getBody());
        log.debug("Post has been updated with id: {}", post.getId());
        return postRepository.findById(post.getId()).orElseThrow(() -> new NotFoundException("Post doesn't exist with id: " + post.getId()));
    }

    @Override
    public void delete(final Integer id) {
        final Post post = findById(id);
        postRepository.delete(post);
        log.debug("Post has been deleted with id: {}", post.getId());
    }

    @Override
    public List<Post> findByUserId(final Integer id) {
        final List<Post> result = postRepository.findByUserId(id);
        final List<Post> postExtApi = postApiService.getAll().stream().filter(x -> id.equals(x.getUserId())).toList();
        if (Objects.nonNull(postExtApi) && !postExtApi.isEmpty()) {
            result.addAll(postExtApi);
        }
        if (Objects.isNull(result) || result.isEmpty()) {
            throw new NotFoundException("Posts not found with userId: " + id);
        }
        return result.stream().distinct().toList();
    }

    @Override
    public Post findByIdSaveFromExternalAPI(final Integer id) {
        final Post postDB = findById(id);
        if (Objects.nonNull(postDB)) {
            log.debug("Post has been searched from DB: {}", id);
            return postDB;
        } else {
            final Post postExtApi = postApiService.getById(id);
            if (Objects.nonNull(postExtApi)) {
                log.debug("Post has been searched from external API: {}", id);
                final Post storedPostDB = postRepository.save(postExtApi);
                log.debug("Post has been stored to DB: {}", storedPostDB.getId());
                return storedPostDB;
            }
            return null;
        }
    }
}
