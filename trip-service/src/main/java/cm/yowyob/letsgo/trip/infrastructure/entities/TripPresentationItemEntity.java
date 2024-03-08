/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.entities;

import cm.yowyob.letsgo.trip.domain.model.plan.TripPresentation;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Frozen;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.Set;


@Data
@Builder
@UserDefinedType(value = "trip_presentation")
public class TripPresentationItemEntity {

    private String txt;
    private Set<String> images;

    public static TripPresentationItemEntity  fromObject(TripPresentation.TripPresentationItem item) {

        if (item == null)
            return null;

        return TripPresentationItemEntity.builder()
                .txt(item.getText())
                .images(item.getImages())
                .build();
    }


    public static TripPresentation.TripPresentationItem toObject(TripPresentationItemEntity tripPresentationItemEntity) {

        if (tripPresentationItemEntity == null)
            return null;

        return new TripPresentation.TripPresentationItem(tripPresentationItemEntity.txt, tripPresentationItemEntity.images);
    }
}
