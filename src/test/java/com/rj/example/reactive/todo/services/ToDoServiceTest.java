package com.rj.example.reactive.todo.services;

import com.rj.example.reactive.todo.data.ItemByEmailId;
import com.rj.example.reactive.todo.data.ItemByEmailIdRepository;
import com.rj.example.reactive.todo.utils.ToDoTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
@ActiveProfiles("unittest")
class ToDoServiceTest {

    @Autowired
    ItemByEmailIdRepository itemByEmailIdRepository;


    @Test
    void addItemAndGetItems() {

        ItemByEmailId itemByEmailId = ToDoTestUtil
                .getItemByEmailId("testEmail1@email", "My ToDo 1");
        ItemByEmailId itemByEmailId2 = ToDoTestUtil
                .getItemByEmailId("testEmail1@email", "My ToDo 2");

        ToDoService todoService = new ToDoService(itemByEmailIdRepository);
        StepVerifier.create(todoService.addItem(itemByEmailId))
                .assertNext(itemByEmailId1 -> ToDoTestUtil.validateItemByEmailId(itemByEmailId, itemByEmailId1))
                .verifyComplete();
        StepVerifier.create(todoService.addItem(itemByEmailId2))
                .assertNext(itemByEmailId1 ->
                        ToDoTestUtil.validateItemByEmailId(itemByEmailId2, itemByEmailId1)).verifyComplete();

        StepVerifier.create(todoService.getItems(itemByEmailId.getEmailId()).collectList())
                .assertNext(itemByEmailIds ->
                        assertEquals(2, itemByEmailIds.size())).verifyComplete();
    }


}