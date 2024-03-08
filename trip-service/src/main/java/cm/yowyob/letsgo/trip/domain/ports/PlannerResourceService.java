/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.ports;

import cm.yowyob.letsgo.trip.domain.exceptions.DriverNotFoundException;
import cm.yowyob.letsgo.trip.domain.exceptions.VehicleNotFoundException;
import cm.yowyob.letsgo.trip.domain.model.Driver;
import cm.yowyob.letsgo.trip.domain.model.vehicle.Vehicle;

import java.util.Collection;
import java.util.Set;

/**
 *  planner resource service
 *
 */
public interface PlannerResourceService {


    /**
     * get planner driver
     *
     * @param sub planner sub
     * @param driverId driver identifier
     * @return {@link Driver}
     * @see Driver
     * @throws DriverNotFoundException cm.yowyob.letsgo.trip.domain.exceptions. driver not found exception
     */
    Driver getPlannerDriver(String sub, String driverId) throws DriverNotFoundException;

    /**
     * get planner vehicle
     *
     * @param userId planner sub
     * @param vehicleId vehicle identifier
     * @return {@link Vehicle}
     * @see Vehicle
     * @throws VehicleNotFoundException vehicle not found
     */
    Vehicle getPlannerVehicle(String userId, String vehicleId)throws VehicleNotFoundException;


    /**
     * get all drivers
     *
     *
     * @param plannerId the planner id
     * @param values values that represent vehicle's ids
     * @return {@link Set}
     * @see Set
     * @see Driver
     * @throws DriverNotFoundException cm.yowyob.letsgo.trip.domain.exceptions. driver not found exception
     */
    Set<Driver> getAllDrivers(String plannerId, Collection<String> values) throws DriverNotFoundException;

    /**
     * get all vehicles
     *
     *
     * @param plannerId The planner id
     * @param values values
     * @return {@link Set}
     * @see Set
     * @see Vehicle
     * @throws VehicleNotFoundException cm.yowyob.letsgo.trip.domain.exceptions. vehicle not found exception
     */
    Set<Vehicle> getAllVehicles(String plannerId, Collection<String> values)throws VehicleNotFoundException;

}
