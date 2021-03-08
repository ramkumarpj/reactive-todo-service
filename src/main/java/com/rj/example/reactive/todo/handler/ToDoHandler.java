package com.rj.example.reactive.todo.handler;

import com.rj.example.reactive.todo.DTO.ItemDTO;
import com.rj.example.reactive.todo.DTO.mapper.ItemMapper;
import com.rj.example.reactive.todo.data.ItemByEmailId;
import com.rj.example.reactive.todo.services.ToDoService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@AllArgsConstructor
@Log4j2
public class ToDoHandler {

    private final ToDoService todoService;
    private final ItemMapper itemMapper;

    public Mono<ServerResponse> addItem(ServerRequest request) {

        log.traceEntry();
        var itemDTO = request.bodyToMono(ItemDTO.class);

        return itemDTO.flatMap( itemDTO1 -> Mono.just(itemMapper.itemDTOToItemByEmailId(itemDTO1)))
                .flatMap(itemByEmailId -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(todoService.addItem(itemByEmailId)
                        .flatMap(itemByEmailId1 ->
                                Mono.just(itemMapper.itemByEmailIdToItemDTO(itemByEmailId1)))
                                .doOnError(exception -> log.error(exception))
                                .doOnSuccess(itemDTO1 -> log.traceExit()),
                        ItemDTO.class))
                .doOnError(exception -> log.error(exception))
                .onErrorMap(exception -> new Exception("unable to add item"));


    }

    public Mono<ServerResponse> getItems(ServerRequest request) {

        log.traceEntry();
        var emailId = request.queryParam("emailId");

        return Mono.just(emailId)
                .filter(emailId1 -> emailId1.isPresent())
                .flatMap(emailId1 -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(todoService.getItems(emailId1.get())
                                        .flatMap(itemByEmailId1 ->
                                                Mono.just(itemMapper.itemByEmailIdToItemDTO(itemByEmailId1)))
                                        .collectList()
                                        .doOnError(exception -> log.error(exception)),
                                List.class))
                .doOnError(exception -> log.error(exception))
                .onErrorMap(exception -> new Exception("unable to get items"));
    }
}
