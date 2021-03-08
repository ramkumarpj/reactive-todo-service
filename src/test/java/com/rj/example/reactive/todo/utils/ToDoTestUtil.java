package com.rj.example.reactive.todo.utils;

import com.rj.example.reactive.todo.data.ItemByEmailId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ToDoTestUtil {

    public static void validateItemByEmailId(ItemByEmailId expected, ItemByEmailId actual) {
        assertEquals(expected.getEmailId(),actual.getEmailId());
        assertEquals(expected.getItem(),actual.getItem());
        assertNotNull(actual.getCreateDateTime());
    }

    public static ItemByEmailId getItemByEmailId(String emailId, String item) {
        ItemByEmailId itemByEmailId = new ItemByEmailId();
        itemByEmailId.setEmailId(emailId);
        itemByEmailId.setItem(item);
        return itemByEmailId;
    }
}
