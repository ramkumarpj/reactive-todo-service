package com.rj.example.reactive.todo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Profile({"test"})
@Configuration
@EnableCassandraRepositories
        (basePackages = "com.rj.example.reactive.todo.data")
public class CassandraConfig extends AbstractCassandraConfiguration {

    private String TODOKEYSPACE = "todoKeySpace";

    @Autowired
    private Environment environment;

    @Override
    protected String getKeyspaceName() {
        return TODOKEYSPACE;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

   @Override
    protected String getLocalDataCenter() {
        if(Optional.ofNullable(System.getProperty("spring.data.cassandra.localDataCenter")).isEmpty())
        {
            System.setProperty("spring.data.cassandra.localDataCenter", environment.getProperty("spring.data.cassandra.localDataCenter"));
        }
        return System.getProperty("spring.data.cassandra.localDataCenter");
    }

    @Override
    public String getContactPoints() {
        if(Optional.ofNullable(System.getProperty("spring.data.cassandra.contact-points")).isEmpty())
        {
            System.setProperty("spring.data.cassandra.contact-points", environment.getProperty("spring.data.cassandra.contact-points"));
        }
        return System.getProperty("spring.data.cassandra.contact-points");
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        CreateKeyspaceSpecification specification = CreateKeyspaceSpecification
                .createKeyspace(TODOKEYSPACE).ifNotExists()
                .with(KeyspaceOption.DURABLE_WRITES, true).withSimpleReplication();
        return Arrays.asList(specification);
    }
}
