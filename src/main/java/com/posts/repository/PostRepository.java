package com.posts.repository;

import com.posts.model.Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {

    List<Post> findByUserId(int userId);

    @Modifying(flushAutomatically = true)
    @Query("update Post p set p.title = :title, p.body = :body where p.id = :id")
    void updatePostTitleAndBody(@Param(value = "id") int id, @Param(value = "title") String title, @Param(value = "body") String body);
}
