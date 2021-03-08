package com.rj.example.reactive.todo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
public class ItemDTO {

    private String emailId;
    private Instant createDateTime;
    private String item;
}
