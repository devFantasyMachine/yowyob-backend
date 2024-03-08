/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model;

import cm.yowyob.letsgo.trip.domain.model.plan.stops.LetsGoCoordinate;

public record Address (String city, String country, LetsGoCoordinate coordinate) {
}
