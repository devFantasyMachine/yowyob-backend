/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.config;

import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cassandra.DriverConfigLoaderBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.*;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DataCenterReplication;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


/**
 * CassandraConfig
 */
@Configuration
@EnableCassandraAuditing
@EnableCassandraRepositories(basePackages = "cm.yowyob.letsgo.trip.infrastructure.repositories")
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
    protected DriverConfigLoaderBuilderConfigurer getDriverConfigLoaderBuilderConfigurer()
    {
        return config ->
                config
                        .withString(DefaultDriverOption.METADATA_SCHEMA_REQUEST_TIMEOUT, "30s")
                        .withString(DefaultDriverOption.CONTROL_CONNECTION_TIMEOUT, "10s")
                        .withString(DefaultDriverOption.REQUEST_TIMEOUT, "30s")
                        .build();
    }



/*
    @Bean
    public DriverConfigLoaderBuilderCustomizer defaultProfile(){
        return builder -> builder
                .withDuration(DefaultDriverOption.METADATA_SCHEMA_REQUEST_TIMEOUT, Duration.ofMillis(60000))
                .withDuration(DefaultDriverOption.CONNECTION_INIT_QUERY_TIMEOUT, Duration.ofMillis(60000))

                .withDuration(DefaultDriverOption.REQUEST_TIMEOUT, Duration.ofMillis(15000))

                .withString(DefaultDriverOption.METADATA_SCHEMA_REQUEST_TIMEOUT, "10 seconds")
                .build();
    }


*/

/*
    @Override
    protected SessionBuilderConfigurer getSessionBuilderConfigurer() {
        return cqlSessionBuilder -> {
            //logger.info("Configuring CqlSession Builder");
            return cqlSessionBuilder
                    .withConfigLoader(DriverConfigLoader.programmaticBuilder()
                            // Resolves the timeout query 'SELECT * FROM system_schema.tables' timed out after PT2S
                            .withDuration(DefaultDriverOption.METADATA_SCHEMA_REQUEST_TIMEOUT, Duration.ofMillis(60000))
                            .withDuration(DefaultDriverOption.CONNECTION_INIT_QUERY_TIMEOUT, Duration.ofMillis(60000))
                            .withDuration(DefaultDriverOption.REQUEST_TIMEOUT, Duration.ofMillis(15000))
                            .build());
        };
    }
*/






    //@Override
    //protected List<DropKeyspaceSpecification> getKeyspaceDrops() {
    //   List<DropKeyspaceSpecification> list = new ArrayList<>();
    //  list.add(DropKeyspaceSpecification.dropKeyspace(getKeyspaceName()));
    //return list;
    //}


    /*@Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        final CreateKeyspaceSpecification specification =
                 CreateKeyspaceSpecification.createKeyspace(keyspace)
                                            .ifNotExists()
                                            .with(KeyspaceOption.DURABLE_WRITES, true)
                                            .withSimpleReplication();


        List<CreateKeyspaceSpecification> specList = new ArrayList<>();
        specList.add(specification);
        return specList;
    }*/


    @Override
    protected @NonNull List<CreateKeyspaceSpecification> getKeyspaceCreations() {

        final CreateKeyspaceSpecification specification = CreateKeyspaceSpecification.createKeyspace(keyspace)
                .ifNotExists()
                .with(KeyspaceOption.DURABLE_WRITES, true)
                .withNetworkReplication(DataCenterReplication.of(localDatacenter, 1));

        List<CreateKeyspaceSpecification> specList = new ArrayList<>();
        specList.add(specification);
        return specList;
    }

}





