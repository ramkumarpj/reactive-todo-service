package com.rj.example.reactive.todo.data;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;

@Table
@Data
public class ItemByEmailId {

    @PrimaryKeyColumn(ordinal=0, type = PrimaryKeyType.PARTITIONED)
    private String emailId;

    @PrimaryKeyColumn(ordinal=0, type = PrimaryKeyType.CLUSTERED)
    private Instant createDateTime;

    @Column
    private String item;

}
