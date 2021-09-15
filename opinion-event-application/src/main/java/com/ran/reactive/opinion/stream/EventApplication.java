package com.ran.reactive.opinion.stream;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.ran.reactive.opinion.stream.handler.OpinionHanlder;
import com.ran.reactive.opinion.stream.model.Event;
import com.ran.reactive.opinion.stream.model.Post;
import com.ran.reactive.opinion.stream.repository.ReactiveEventRepository;
import com.ran.reactive.opinion.stream.repository.ReactivePostRepository;

@EnableReactiveMongoRepositories
@SpringBootApplication(exclude = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class })
public class EventApplication {

	private static Logger log = LoggerFactory.getLogger(EventApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EventApplication.class, args);
	}

	/**
	 * Rest API Router
	 * 
	 * @param reactiveTweetRepository
	 * @return
	 */

	@Autowired
	private OpinionHanlder opinionHandler;

	@Bean
	RouterFunction<ServerResponse> routes(ReactiveEventRepository reactiveTweetRepository, ReactivePostRepository reactivePostRepository) {
		return route(GET("/stream/events/{eventId}/posts"),
				request -> ok().contentType(MediaType.TEXT_EVENT_STREAM)
						.body(reactivePostRepository.findWithTailableCursorBy(request.pathVariable("eventId")), Post.class))
								.andRoute(GET("/stream/events/{eventId}"),
										request -> ok().contentType(MediaType.TEXT_EVENT_STREAM)
								.body(reactiveTweetRepository.findWithTailableCursorBy(request.pathVariable("eventId")), Event.class))
								.andRoute(GET("/events"),
										request -> ok().contentType(MediaType.APPLICATION_JSON)
												.body(reactiveTweetRepository.findAll(), Event.class))
								.andRoute(GET("/events/{eventId}"),
										request -> ok().contentType(MediaType.APPLICATION_JSON)
												.body(reactiveTweetRepository.findById(request.pathVariable("eventId")), Event.class))
								.andRoute(
										RequestPredicates.POST("/events/{eventId}/posts")
												.and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
												.and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)),
										opinionHandler::addPost)
								.andRoute(GET("/events/{eventId}/posts"),
										request -> ok().contentType(MediaType.APPLICATION_JSON)
												.body(reactivePostRepository.findByEventId(request.pathVariable("eventId")), Post.class))
								;

	}

}
