package com.posts.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@Entity
@DynamicUpdate
@Table(name = "post")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post implements Serializable {

    private static final long serialVersionUID = -782851271717560088L;

    @Id
    private int id;
    private int userId;
    @NotBlank
    @Column(nullable = false)
    private String title;
    @NotBlank
    @Column(nullable = false)
    private String body;
}
