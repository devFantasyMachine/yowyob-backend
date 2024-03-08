package cm.yowyob.auth.app.domain.util;




public abstract class ObjectUtils {


    public static <T> T getOrDefault(T object, T defaultObject){

        if (object != null)
            return object;

        return defaultObject;
    }


}
