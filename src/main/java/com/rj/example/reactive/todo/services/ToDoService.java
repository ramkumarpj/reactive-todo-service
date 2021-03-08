package com.rj.example.reactive.todo.services;

import com.rj.example.reactive.todo.data.ItemByEmailId;
import com.rj.example.reactive.todo.data.ItemByEmailIdRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
@AllArgsConstructor
@Log4j2
public class ToDoService {

    private final ItemByEmailIdRepository itemByEmailIdRepository;

    public Mono<ItemByEmailId> addItem(ItemByEmailId itemByEmailId) {
        return Mono.just(itemByEmailId)
                .filter(itemByEmailId1 -> StringUtils.isNotBlank(itemByEmailId1.getEmailId()))
                .flatMap(itemByEmailId1 -> {
                    itemByEmailId1.setCreateDateTime(Instant.now());
                    return Mono.just(itemByEmailId1);
                })
                .flatMap(itemByEmailId1 -> itemByEmailIdRepository.save(itemByEmailId1))
                .doOnError(exception -> log.error(exception))
                .onErrorMap(exception -> new Exception("unable to add item"));
    }

    public Flux<ItemByEmailId> getItems(String emailId) {
        return itemByEmailIdRepository.findAllByEmailId(emailId)
                .doOnError(exception -> log.error(exception))
                .onErrorMap(exception -> new Exception("unable to get items"));
    }
}
