/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.utils;

import org.locationtech.jts.geom.Geometry;

import java.io.Serializable;

/**
 * A list of coordinates encoded as a string.
 * <p>
 * See <a href="http://code.google.com/apis/maps/documentation/polylinealgorithm.html">Encoded
 * polyline algorithm format</a>
 */

public record EncodedPolyline(String points, int length) implements Serializable {
  public static EncodedPolyline encode(Geometry geometry) {
    return PolylineEncoder.encodeGeometry(geometry);
  }
}
