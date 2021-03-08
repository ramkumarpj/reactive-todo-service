package com.rj.example.reactive.todo.handler;

import com.rj.example.reactive.todo.DTO.ItemDTO;
import com.rj.example.reactive.todo.DTO.mapper.ItemMapper;
import com.rj.example.reactive.todo.config.ToDoRouter;
import com.rj.example.reactive.todo.data.ItemByEmailId;
import com.rj.example.reactive.todo.utils.ToDoTestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("unittest")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ToDoHandlerTest {

    @Autowired
    ItemMapper itemMapper;

    @Autowired
    ToDoRouter toDoRouter;

    @Test
    void addItemAndGetItems() {

        ItemByEmailId itemByEmailId1 = ToDoTestUtil
                .getItemByEmailId("testEmail1.todoHandler@email", "My Todo Item 1");
        ItemByEmailId itemByEmailId2 = ToDoTestUtil
                .getItemByEmailId("testEmail1.todoHandler@email", "My Todo Item 2");

        WebTestClient webTestClient = WebTestClient.bindToRouterFunction(toDoRouter.todoRoute()).build();

        // Add first Item
        webTestClient.post()
                .uri("/addItem")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(itemMapper.itemByEmailIdToItemDTO(itemByEmailId1)), ItemDTO.class)
                .exchange().expectStatus().isOk()
                .expectBody(ItemDTO.class).consumeWith(System.out::println)
                .value(itemDTO -> ToDoTestUtil.validateItemByEmailId(itemByEmailId1, itemMapper.itemDTOToItemByEmailId(itemDTO)));

        // Add second Item
        webTestClient.post()
                .uri("/addItem")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(itemMapper.itemByEmailIdToItemDTO(itemByEmailId2)), ItemDTO.class)
                .exchange().expectStatus().isOk()
                .expectBody(ItemDTO.class).consumeWith(System.out::println)
                .value(itemDTO -> ToDoTestUtil.validateItemByEmailId(itemByEmailId2, itemMapper.itemDTOToItemByEmailId(itemDTO)));


        // Get Items
        webTestClient.get()
                .uri("/getItems?emailId=testEmail1.todoHandler@email")
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk().expectBody(List.class)
                .consumeWith(System.out::println)
                .value(itemDTOs -> {
                    assertEquals(2, itemDTOs.size());
                    itemDTOs.forEach(System.out::println);
                });
    }
}