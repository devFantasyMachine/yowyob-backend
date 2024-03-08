/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.driver.domain.model.stops;


import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;


public record LetsGoCoordinate(double latitude, double longitude) implements Serializable {

    public static final LetsGoCoordinate GREENWICH = new LetsGoCoordinate(51.48, 0d);
    public static final LetsGoCoordinate NONE = null;

    private static final double LAT_MIN = -90;
    private static final double LAT_MAX = 90;
    private static final double LON_MIN = -180;
    private static final double LON_MAX = 180;

    public LetsGoCoordinate(double latitude, double longitude) {

        this.latitude = latitude;
        this.longitude =  longitude;
    }

    /**
     * Unlike the constructor this factory method retuns {@code null} if both {@code lat} and {@code
     * lon} is {@code null}.
     */
    public static LetsGoCoordinate createOptionalCoordinate(Double latitude, Double longitude) {
        if (latitude == null && longitude == null) {
            return null;
        }

        // Set coordinate is both lat and lon exist
        if (latitude != null && longitude != null) {
            return new LetsGoCoordinate(latitude, longitude);
        }
        throw new IllegalArgumentException(
                "Both 'latitude' and 'longitude' must have a value or both must be 'null'."
        );
    }

    /**
     * Find the mean coordinate between the given set of {@code coordinates}.
     */
    public static LetsGoCoordinate mean(Collection<LetsGoCoordinate> coordinates) {
        if (coordinates == null || coordinates.isEmpty()) {
            throw new IllegalArgumentException(
                    "Unable to calculate mean for null or empty set of coordinates"
            );
        }
        if (coordinates.size() == 1) {
            return coordinates.iterator().next();
        }

        double n = coordinates.size();
        double latitude = 0.0;
        double longitude = 0.0;

        for (LetsGoCoordinate c : coordinates) {
            latitude += c.latitude();
            longitude += c.longitude();
        }

        return new LetsGoCoordinate(latitude / n, longitude / n);
    }


    /**
     * Compare to coordinates and return {@code true} if they are close together - have the same
     * location. The comparison uses an EPSILON of 1E-7 for each axis, for both latitude and
     * longitude.
     * <p>
     * When we compare two coordinates we want to see if they are within a given distance,
     * roughly within a square centimeter. This is not
     * <em>transitive</em>, hence violating the equals/hasCode guideline. Consider 3 point along
     * one of the axis:
     * <pre>
     *      | 8mm | 8mm |
     *      x --- y --- z
     *     </pre>
     * Then {@code x.sameLocation(y)} is {@code true} and {@code y.sameLocation(z)} is {@code true},
     * but {@code x.sameLocation(z)} is {@code false}.
     */
    public boolean sameLocation(LetsGoCoordinate other) {
        return equals(other);
    }

    /**
     * Add (deltaLat, deltaLon) to the current coordinates and return the new position.
     */
    public LetsGoCoordinate add(double deltaLat, double deltaLon) {
        return new LetsGoCoordinate(latitude + deltaLat, longitude + deltaLon);
    }

    /**
     * Return a string on the form: {@code "(60.12345, 11.12345)"}. Up to 5 digits are used after the
     * period(.), even if the coordinate is specified with a higher precision.
     */
    @Override
    public String toString() {
        return "(" + latitude() + ", " + longitude() + ")";
    }

    /**
     * Return true if the coordinates are numerically equal. The coordinate latitude and longitude
     * are rounded to the closest number of 1E-7 when constructed. This enforces two coordinates
     * that is close together to be equals.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LetsGoCoordinate other = (LetsGoCoordinate) o;
        return latitude == other.latitude && longitude == other.longitude;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }
}
