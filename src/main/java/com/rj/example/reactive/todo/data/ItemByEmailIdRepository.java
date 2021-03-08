package com.rj.example.reactive.todo.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ItemByEmailIdRepository extends ReactiveCrudRepository<ItemByEmailId, String> {
    Flux<ItemByEmailId> findAllByEmailId(String emailId);
}
