package cm.yowyob.letsgo.trip.domain.model;

public class ObjectUtils {

    public static <T> T getOrDefault(T value, T defaultValue){

        return value == null ? defaultValue : value;
    }

}
