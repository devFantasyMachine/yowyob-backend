/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.entities;


import cm.yowyob.letsgo.trip.infrastructure.entities.udt.StopLocationEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;


@Data
@Builder
@UserDefinedType(value = "route_entity")
public class RouteEntity {

    @Embedded(onEmpty = Embedded.OnEmpty.USE_EMPTY, prefix = "from_")
    private StopLocationEntity fromLocation;

    @Embedded(onEmpty = Embedded.OnEmpty.USE_EMPTY, prefix = "to_")
    private StopLocationEntity toLocation;

}
