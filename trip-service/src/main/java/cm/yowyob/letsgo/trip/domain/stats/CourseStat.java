/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.stats;

import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.Route;

/**
 * trajet
 *  bookCount mean the number of reservations that the planner have received with this route
 */
public record CourseStat(Route route, Long count, Long bookCount) {


}
