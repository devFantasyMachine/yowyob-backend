/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.services;

import cm.yowyob.letsgo.trip.domain.exceptions.ReservationNotFoundException;
import cm.yowyob.letsgo.trip.domain.managers.ReservationService;
import cm.yowyob.letsgo.trip.domain.model.reservation.LockedTripResources;
import cm.yowyob.letsgo.trip.infrastructure.entities.LockedTripResourcesEntity;
import cm.yowyob.letsgo.trip.infrastructure.repositories.ReservationRepository;
import cm.yowyob.letsgo.trip.mappers.LockedTripResourcesMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class CassandraReservationService implements ReservationService  {

    ReservationRepository reservationRepository;

    @Override
    public LockedTripResources save(LockedTripResources lockedTripResources) {

        LockedTripResourcesEntity lockedTripResourcesEntity =
                reservationRepository.save(LockedTripResourcesMapper.mapFromObject(lockedTripResources));

        return LockedTripResourcesMapper.mapToObject(lockedTripResourcesEntity);
    }

    @Override
    public LockedTripResources getById(String reservationId) throws ReservationNotFoundException {

        LockedTripResourcesEntity lockedTripResourcesEntity =
                reservationRepository.findByLockedId(UUID.fromString(reservationId));

        if (lockedTripResourcesEntity == null)
            throw new  ReservationNotFoundException();

        return LockedTripResourcesMapper.mapToObject(lockedTripResourcesEntity);
    }

    @Override
    public List<LockedTripResources> getByReservationGroup(String reservationGroup) {

        List<LockedTripResourcesEntity> reservationEntities =
                reservationRepository.findAllByGroup(reservationGroup);

        return reservationEntities.stream()
                .map(LockedTripResourcesMapper::mapToObject)
                .collect(Collectors.toList());
    }


}
