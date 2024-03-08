package cm.yowyob.letsgo.trip.domain.utils;

public class ObjectUtils {
    public static <T> T getOrDefault(T value, T defaultValue){

        return value == null ? defaultValue : value;
    }

}
