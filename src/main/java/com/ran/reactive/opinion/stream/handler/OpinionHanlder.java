package com.ran.reactive.opinion.stream.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.ran.reactive.opinion.stream.model.Event;
import com.ran.reactive.opinion.stream.model.Post;
import com.ran.reactive.opinion.stream.repository.ReactiveEventRepository;
import com.ran.reactive.opinion.stream.repository.ReactivePostRepository;
import com.ran.reactive.opinion.stream.vo.EventVO;
import com.ran.reactive.opinion.stream.vo.PostVO;

import reactor.core.publisher.Mono;

@Component
public class OpinionHanlder {
	
	@Autowired
	ReactiveEventRepository reactiveTweetRepository;
	
	@Autowired
	ReactivePostRepository reactivePostRepository;
	
	public Mono<ServerResponse> addTweet(ServerRequest serverRequest) {
		Mono<EventVO> tweetVo = serverRequest.bodyToMono(EventVO.class);
		Mono<Event> tweet = tweetVo.map(f -> Event.parse(f))
				.flatMap(reactiveTweetRepository::save);
		tweetVo = Mono.from(tweet.map(f -> 
			EventVO.parse(f)
		));
				
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(tweetVo, EventVO.class);
	}
	
	public Mono<ServerResponse> addPost(ServerRequest serverRequest) {
		String eventId = serverRequest.pathVariable("eventId");
		Mono<PostVO> postVo = serverRequest.bodyToMono(PostVO.class);		
		Mono<Post> post = postVo.map(f -> Post.parse(f)).map(a -> {a.setEventId(eventId); return a;})
				.flatMap(reactivePostRepository::save);
		postVo = Mono.from(post.map(f -> 
			PostVO.parse(f)
		));
				
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(postVo, PostVO.class);
	}
}
