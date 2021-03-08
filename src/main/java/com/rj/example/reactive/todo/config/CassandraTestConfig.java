package com.rj.example.reactive.todo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.Arrays;
import java.util.List;

@Profile("unittest")
@Configuration
@EnableCassandraRepositories
        (basePackages = "com.rj.example.reactive.todo.data")
public class CassandraTestConfig extends AbstractCassandraConfiguration {

    private String TODOKEYSPACE = "todoKeySpace";

    @Override
    protected String getKeyspaceName() {
        return TODOKEYSPACE;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    public int getPort() {
        return Integer.getInteger("spring.data.cassandra.port");
    }

    @Override
    protected String getLocalDataCenter() {
        return System.getProperty("spring.data.cassandra.localDataCenter");
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        CreateKeyspaceSpecification specification = CreateKeyspaceSpecification
                .createKeyspace(TODOKEYSPACE).ifNotExists()
                .with(KeyspaceOption.DURABLE_WRITES, true).withSimpleReplication();
        return Arrays.asList(specification);
    }
}