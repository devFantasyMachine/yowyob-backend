/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.utils;


import org.geotools.referencing.CRS;
import org.locationtech.jts.algorithm.ConvexHull;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.geom.impl.PackedCoordinateSequenceFactory;
import org.locationtech.jts.linearref.LengthLocationMap;
import org.locationtech.jts.linearref.LinearLocation;
import org.locationtech.jts.linearref.LocationIndexedLine;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class GeometryUtils {

  private static final Logger LOG = LoggerFactory.getLogger(GeometryUtils.class);

  private static final CoordinateSequenceFactory csf = new PackedCoordinateSequenceFactory();
  private static final GeometryFactory gf = new GeometryFactory(csf);

  /** A shared copy of the WGS84 CRS with longitude-first axis order. */
  public static final CoordinateReferenceSystem WGS84_XY;

  static {
    try {
      WGS84_XY = CRS.getAuthorityFactory(true).createCoordinateReferenceSystem("EPSG:4326");
    } catch (Exception ex) {
      LOG.error("Unable to create longitude-first WGS84 CRS", ex);
      throw new RuntimeException(
        "Could not create longitude-first WGS84 coordinate reference system."
      );
    }
  }

  public static <T> Geometry makeConvexHull(
    Collection<T> collection,
    Function<T, Coordinate> mapToCoordinate
  ) {
    var gf = getGeometryFactory();
    Geometry[] points = new Geometry[collection.size()];
    int i = 0;
    for (T v : collection) {
      points[i++] = gf.createPoint(mapToCoordinate.apply(v));
    }

    var col = new GeometryCollection(points, gf);
    return new ConvexHull(col).getConvexHull();
  }

  public static LineString makeLineString(double... coords) {
    GeometryFactory factory = getGeometryFactory();
    Coordinate[] coordinates = new Coordinate[coords.length / 2];
    for (int i = 0; i < coords.length; i += 2) {
      coordinates[i / 2] = new Coordinate(coords[i], coords[i + 1]);
    }
    return factory.createLineString(coordinates);
  }

  public static LineString makeLineString(List<Coordinate> coordinates) {
    GeometryFactory factory = getGeometryFactory();
    return factory.createLineString(coordinates.toArray(new Coordinate[] {}));
  }

  public static LineString makeLineString(Coordinate[] coordinates) {
    GeometryFactory factory = getGeometryFactory();
    return factory.createLineString(coordinates);
  }

  public static <T> LineString concatenateLineStrings(
    List<T> inputObjects,
    Function<T, LineString> mapper
  ) {
    return concatenateLineStrings(inputObjects.stream().map(mapper).toList());
  }

  public static LineString concatenateLineStrings(List<LineString> lineStrings) {
    GeometryFactory factory = getGeometryFactory();
    Predicate<Coordinate[]> nonZeroLength = coordinates -> coordinates.length != 0;
    return factory.createLineString(
      lineStrings
        .stream()
        .filter(Objects::nonNull)
        .map(LineString::getCoordinates)
        .filter(nonZeroLength)
        .<CoordinateArrayListSequence>collect(
          CoordinateArrayListSequence::new,
          (acc, segment) -> {
            if ((acc.size() == 0 || !acc.getCoordinate(acc.size() - 1).equals(segment[0]))) {
              acc.extend(segment);
            } else {
              acc.extend(segment, 1);
            }
          },
          (head, tail) -> head.extend(tail.toCoordinateArray())
        )
    );
  }

  public static LineString addStartEndCoordinatesToLineString(
    Coordinate startCoord,
    LineString lineString,
    Coordinate endCoord
  ) {
    Coordinate[] coordinates = new Coordinate[lineString.getCoordinates().length + 2];
    coordinates[0] = startCoord;
    for (int j = 0; j < lineString.getCoordinates().length; j++) {
      coordinates[j + 1] = lineString.getCoordinates()[j];
    }
    coordinates[lineString.getCoordinates().length + 1] = endCoord;
    return makeLineString(coordinates);
  }

  public static LineString removeStartEndCoordinatesFromLineString(LineString lineString) {
    Coordinate[] coordinates = new Coordinate[lineString.getCoordinates().length - 2];
    for (int j = 1; j < lineString.getCoordinates().length - 1; j++) {
      coordinates[j - 1] = lineString.getCoordinates()[j];
    }
    return makeLineString(coordinates);
  }

  public static GeometryFactory getGeometryFactory() {
    return gf;
  }

  /**
   * Splits the input geometry into two LineStrings at the given point.
   */
  public static SplitLineString splitGeometryAtPoint(Geometry geometry, Coordinate nearestPoint) {
    // An index in JTS can actually refer to any point along the line. It is NOT an array index.
    LocationIndexedLine line = new LocationIndexedLine(geometry);
    LinearLocation l = line.indexOf(nearestPoint);

    LineString beginning = (LineString) line.extractLine(line.getStartIndex(), l);
    LineString ending = (LineString) line.extractLine(l, line.getEndIndex());

    return new SplitLineString(beginning, ending);
  }

  /**
   * Splits the input geometry into two LineStrings at a fraction of the distance covered.
   */
  public static SplitLineString splitGeometryAtFraction(Geometry geometry, double fraction) {
    LineString empty = new LineString(null, gf);
    Coordinate[] coordinates = geometry.getCoordinates();
    CoordinateSequence sequence = gf.getCoordinateSequenceFactory().create(coordinates);
    LineString total = new LineString(sequence, gf);

    if (coordinates.length < 2) {
      return new SplitLineString(empty, empty);
    }
    if (fraction <= 0) {
      return new SplitLineString(empty, total);
    }
    if (fraction >= 1) {
      return new SplitLineString(total, empty);
    }

    double totalDistance = total.getLength();
    double requestedDistance = totalDistance * fraction;

    // An index in JTS can actually refer to any point along the line. It is NOT an array index.
    LocationIndexedLine line = new LocationIndexedLine(geometry);
    LinearLocation l = LengthLocationMap.getLocation(geometry, requestedDistance);

    LineString beginning = (LineString) line.extractLine(line.getStartIndex(), l);
    LineString ending = (LineString) line.extractLine(l, line.getEndIndex());

    return new SplitLineString(beginning, ending);
  }

  /**
   * Returns the chunk of the given geometry between the two given coordinates.
   * <p>
   * Assumes that "second" is after "first" along the input geometry.
   */
  public static LineString getInteriorSegment(
    Geometry geomerty,
    Coordinate first,
    Coordinate second
  ) {
    SplitLineString splitGeom = GeometryUtils.splitGeometryAtPoint(geomerty, first);
    splitGeom = GeometryUtils.splitGeometryAtPoint(splitGeom.ending(), second);
    return splitGeom.beginning();
  }

}
