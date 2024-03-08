package cm.yowyob.letsgo.trip.infrastructure.entities;


import cm.yowyob.letsgo.trip.domain.model.resources.ResourceStatus;
import cm.yowyob.letsgo.trip.infrastructure.entities.udt.StopPointEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.ZonedDateTime;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "itinerary_product")
public class ItineraryProductEntity {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, name = "product_id")
    private UUID productId;

    @Indexed
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, name = "plan_id")
    private UUID planId;

    @Indexed
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, name = "resource_id")
    private UUID resourceId;

    @Indexed
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, name = "status")
    private ResourceStatus status;

    private StopPointEntity fromLocation;
    private StopPointEntity toLocation;
    private ZonedDateTime departure;
    private ZonedDateTime arrival;
    private Float cost;
    private Float quantity;


    @Transient
    private TripResourceEntity resource;

}
