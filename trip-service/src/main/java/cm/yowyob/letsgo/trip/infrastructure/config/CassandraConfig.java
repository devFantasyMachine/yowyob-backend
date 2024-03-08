/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.config;

/*

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DataCenterReplication;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.ArrayList;
import java.util.List;



@Configuration
@EnableCassandraRepositories(basePackages = "cm.enspy.gi.project.trip_service.repository")
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${spring.data.cassandra.keyspace-name}")
    private String keyspace;

    @Value("${spring.data.cassandra.contact-points}")
    private String hosts;

    @Value("${spring.data.cassandra.port}")
    private int port;

    @Value("${spring.data.cassandra.local-datacenter}")
    private String localDatacenter;

    @Value("${spring.data.cassandra.username}")
    private String username;

    @Value("${spring.data.cassandra.password}")
    private String password;

    @Value("${spring.data.cassandra.schema-action}")
    private SchemaAction schemaAction;

    @Override
    protected String getContactPoints() {
        return hosts;
    }

    @Override
    protected String getLocalDataCenter() {
        return localDatacenter;
    }

    //@Override
    //protected int getPort() {
    //    return port;
    //}

    @Override
    public SchemaAction getSchemaAction() {
        return schemaAction;
    }

    @Override
    protected String getKeyspaceName() {
        return keyspace;
    }
    

    //@Override
    //protected List<DropKeyspaceSpecification> getKeyspaceDrops() {
     //   List<DropKeyspaceSpecification> list = new ArrayList<>();
      //  list.add(DropKeyspaceSpecification.dropKeyspace(getKeyspaceName()));
        //return list;
    //}
    
    
    */
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
    }*//*



    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        final CreateKeyspaceSpecification specification = CreateKeyspaceSpecification.createKeyspace(keyspace)
                .ifNotExists()
                .with(KeyspaceOption.DURABLE_WRITES, true)
                .withNetworkReplication(DataCenterReplication.of(localDatacenter, 1));

        List<CreateKeyspaceSpecification> specList = new ArrayList<>();
        specList.add(specification);
        return specList;
    }

}




*/
