package cm.yowyob.letsgo.driver.domain.model;

import java.util.*;

public class ObjectUtils {
    public static <T> T getOrDefault(T value, T defaultValue){

        return value == null ? defaultValue : value;
    }

    public static <T> Set<T> getMutableSet(Collection<T> list){

        if (list == null)
            return new HashSet<>();

        return new HashSet<>(list);
    }

    public static <T> List<T> getMutableList(Collection<T> list){

        if (list == null)
            return new ArrayList<>();

        return new ArrayList<>(list);
    }


}
