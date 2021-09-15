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
import com.ran.reactive.opinion.stream.repository.ReactiveEventRepository;

@EnableReactiveMongoRepositories
@SpringBootApplication(exclude = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class })
public class TweetStreamApplication {

	private static Logger log = LoggerFactory.getLogger(TweetStreamApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TweetStreamApplication.class, args);
	}

	/**
	 * Rest API Router
	 * 
	 * @param reactiveTweetRepository
	 * @return
	 */
	
	@Autowired
	private OpinionHanlder tweetHandler;
	@Bean
	RouterFunction<ServerResponse> routes(ReactiveEventRepository reactiveTweetRepository) {
		return route(GET("/stream/events"),
				request -> ok().contentType(MediaType.TEXT_EVENT_STREAM)
						.body(reactiveTweetRepository.findWithTailableCursorBy(), Event.class))
						.andRoute(GET("/events"),
								request -> ok().contentType(MediaType.APPLICATION_JSON)
										.body(reactiveTweetRepository.findAll(), Event.class))
						.andRoute(RequestPredicates.POST("/events").and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
								.and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)), tweetHandler::addTweet);

						 
	}

}
