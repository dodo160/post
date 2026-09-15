package com.posts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    @NotNull
    private Integer id;
    @NotNull
    private Integer userId;
    @NotBlank
    private String title;
    @NotBlank
    private String body;
}
