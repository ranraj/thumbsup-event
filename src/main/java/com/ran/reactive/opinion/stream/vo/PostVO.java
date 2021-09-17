package com.ran.reactive.opinion.stream.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ran.reactive.opinion.stream.model.Post;
import com.ran.reactive.opinion.stream.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostVO {

	private String id;

	private String eventId;

	private Date createdAt;

	private String message;

	private String imageUrl;

	private User user;

	public static PostVO parse(Post post) {
		PostVO postVO = new PostVO();
		postVO.id = post.getId();
		postVO.eventId = post.getEventId();
		postVO.createdAt = post.getCreatedAt();
		postVO.message = post.getMessage();
		postVO.imageUrl = post.getImageUrl();
		postVO.user = post.getUser();
		return postVO;
	}

}