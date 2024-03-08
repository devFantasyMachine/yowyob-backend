/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.services;

import cm.yowyob.letsgo.trip.domain.model.*;
import cm.yowyob.letsgo.trip.domain.exceptions.DriverNotFoundException;
import cm.yowyob.letsgo.trip.domain.exceptions.VehicleNotFoundException;
import cm.yowyob.letsgo.trip.domain.model.vehicle.Vehicle;
import cm.yowyob.letsgo.trip.domain.ports.PlannerResourceService;
import cm.yowyob.letsgo.trip.domain.model.plan.TransportMode;
import lombok.NonNull;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class InMemoryPlannerResourceService implements PlannerResourceService {


    @Override
    public Driver getPlannerDriver(String sub, String driverId) throws DriverNotFoundException {

        return Driver.builder()
                .driverId(driverId)
                .firstName("Teubou")
                .lastName("Christian")
                .about("Cool")
                .score(Score.INITIAL)
                .gender("M")
                .build();
    }

    @Override
    public Vehicle getPlannerVehicle(String sub, String vehicleId) throws VehicleNotFoundException {

        return Vehicle.builder()
                .vehicleId(vehicleId)
                .model("Toyota Carina")
                .mark("Toyota")
                .transportMode(TransportMode.BUS)
                .build();
    }

    @Override
    public Set<Driver> getAllDrivers(String plannerId, Collection<String> values) throws DriverNotFoundException {

        return values.stream()
                .map(s -> {
                    try {
                        return getPlannerDriver("", s);
                    } catch (DriverNotFoundException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Vehicle> getAllVehicles(String plannerId, @NonNull Collection<String> values) throws VehicleNotFoundException {

        return values.stream()
                .map(s -> {
                    try {
                        return getPlannerVehicle("", s);
                    } catch (VehicleNotFoundException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }


}
