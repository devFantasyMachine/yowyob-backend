/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.utils;

import org.locationtech.jts.geom.LineString;

public record SplitLineString(LineString beginning, LineString ending) {}
