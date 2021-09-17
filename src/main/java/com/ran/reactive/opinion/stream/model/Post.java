package com.ran.reactive.opinion.stream.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ran.reactive.opinion.stream.vo.EventVO;
import com.ran.reactive.opinion.stream.vo.PostVO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Document(collection = "posts")
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Post {


	@Id
    @JsonProperty("id_str")
    private String id;
	
	@JsonProperty("event_str")
    private String eventId;
		
    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("message")
    private String message;

    @JsonProperty("image_url")
	private String imageUrl;
    
    @JsonProperty("user")
    private User user;
    
    
    public static Post parse(PostVO postVO){
    	Post post = new Post();
    	post.id = postVO.getId();
		post.eventId = postVO.getEventId();
		post.createdAt = postVO.getCreatedAt();
		post.message = postVO.getMessage();
		post.imageUrl = postVO.getImageUrl();
		post.user = postVO.getUser();
    	return post;
    }
     
}
