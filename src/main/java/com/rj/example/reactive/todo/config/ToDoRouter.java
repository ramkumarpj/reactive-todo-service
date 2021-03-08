package com.rj.example.reactive.todo.config;

import com.rj.example.reactive.todo.handler.ToDoHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
public class ToDoRouter {

    private final ToDoHandler toDoHandler;

    @Bean
    public RouterFunction<ServerResponse> todoRoute() {
        return RouterFunctions.route(POST("/addItem").and(accept(MediaType.APPLICATION_JSON)), toDoHandler::addItem)
                .andRoute(GET("/getItems").and(accept(MediaType.APPLICATION_JSON)), toDoHandler::getItems);
    }
}
