package com.reddit.dto;

import com.reddit.model.Post;
import com.reddit.model.SubReddit;
import com.reddit.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubRedditDto {
    private Long id;
    private String name;
    private String description;
    private Integer numberOfPosts;
}
