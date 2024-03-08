package cm.yowyob.auth.app.domain.util;


public abstract class StringUtils {


    public static boolean hasText(String str) {
        return str != null && !str.isBlank();
    }


}
