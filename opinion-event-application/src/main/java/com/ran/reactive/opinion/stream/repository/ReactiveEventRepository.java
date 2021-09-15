package com.ran.reactive.opinion.stream.repository;

import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.ran.reactive.opinion.stream.model.Event;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveEventRepository extends ReactiveCrudRepository<Event, String> {

    @Tailable
    Flux<Event> findWithTailableCursorBy();
    
    @Tailable
    Flux<Event> findWithTailableCursorBy(String id);
    
    Mono<Event> findById(String id);

}
