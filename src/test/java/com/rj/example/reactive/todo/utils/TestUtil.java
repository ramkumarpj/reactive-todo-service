package com.rj.example.reactive.todo.utils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.net.InetSocketAddress;

@Component
@Scope("singleton")
public class TestUtil {

    @Container
    private GenericContainer cassandraContainer;

    private Network todoNetwork = Network.newNetwork();

    public TestUtil() {

        cassandraContainer = new GenericContainer<>(DockerImageName.parse("cassandra:latest"))
                .withCreateContainerCmdModifier(createContainerCmd -> createContainerCmd.withHostName("cassandra-todo"))
                .withNetwork(todoNetwork)
                .withNetworkAliases("cassandra-todo")
                .withExposedPorts(9042, 9042);
        cassandraContainer.start();

        //Cassandra
        final String containerIpAddress = cassandraContainer.getContainerIpAddress();
        final int containerPort = cassandraContainer.getMappedPort(9042);
        final InetSocketAddress containerEndPoint = new InetSocketAddress(containerIpAddress, containerPort);
        System.out.println("Cassandra Connection ==== " + containerIpAddress + ":" + containerPort);
        System.setProperty("spring.data.cassandra.port", String.valueOf(containerPort));
        System.setProperty("spring.data.cassandra.contact-points", containerEndPoint.toString());
        System.setProperty("spring.data.cassandra.localDataCenter", "datacenter1");

    }
}
