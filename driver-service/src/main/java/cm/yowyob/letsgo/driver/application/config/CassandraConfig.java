/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.driver.application.config;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.EnableCassandraAuditing;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DataCenterReplication;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.ArrayList;
import java.util.List;


/**
 * CassandraConfig
 */
@Configuration
@EnableCassandraAuditing
@EnableCassandraRepositories(basePackages = "cm.yowyob.letsgo.resource.infrastructure.repositories")
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${spring.cassandra.keyspace-name}")
    private String keyspace;

    @Value("${spring.cassandra.contact-points}")
    private String hosts;

    @Value("${spring.cassandra.local-datacenter}")
    private String localDatacenter;

    @Value("${spring.cassandra.username}")
    private String username;

    @Value("${spring.cassandra.password}")
    private String password;

    @Value("${spring.cassandra.schema-action}")
    private SchemaAction schemaAction;

    @Override
    protected @NonNull String getContactPoints() {
        return hosts;
    }

    @Override
    protected String getLocalDataCenter() {
        return localDatacenter;
    }


    @Override
    public @NonNull SchemaAction getSchemaAction() {
        return schemaAction;
    }

    @Override
    protected @NonNull String getKeyspaceName() {
        return keyspace;
    }


    @Override
    protected @NonNull List<CreateKeyspaceSpecification> getKeyspaceCreations() {

        final CreateKeyspaceSpecification specification =
                CreateKeyspaceSpecification.createKeyspace(keyspace)
                        .ifNotExists()
                        .with(KeyspaceOption.DURABLE_WRITES, true)
                        .withNetworkReplication(DataCenterReplication.of(localDatacenter, 1));

        List<CreateKeyspaceSpecification> specList = new ArrayList<>();
        specList.add(specification);
        return specList;
    }

}





