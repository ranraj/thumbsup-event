package com.ran.reactive.opinion.stream.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.ran.reactive.opinion.stream.model.Post;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactivePostRepository extends ReactiveCrudRepository<Post, String> {

    @Tailable
    @Query("{eventId : ?0}")
    Flux<Post> findWithTailableCursorBy(String eventId);
       
    
    Mono<Post> findById(String id);
    
    @Query("{eventId : ?0}")
    Flux<Post> findByEventId(String eventId);

}
