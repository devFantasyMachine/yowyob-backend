package cm.yowyob.auth.app.domain.util;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Normalizer {

    public Normalizer() {
    }

    public static <T> void deDuplicate(List<T> var0) {
        if (var0 != null) {
            HashSet var1 = new HashSet();
            var0.removeIf((var1x) -> !var1.add(var1x));
        }
    }

    public static String lineReturns(String var0) {
        return var0 == null ? null : var0.replaceAll("\\r\\n|\\r", "\n");
    }

    public static <T> void lineReturnsMap(Map<T, String> var0) {
        var0.forEach((var1, var2) -> {
            if (var2 != null) {
                var0.put(var1, var2.replaceAll("\\r\\n|\\r", "\n"));
            }

        });
    }

    public static <T> void removeEmpty(List<T> var0) {
        if (var0 != null) {
            var0.removeIf(Objects::isNull);
        }
    }

    public static <T, U> void removeEmpty(Map<T, U> var0) {
        if (var0 != null) {
            var0.keySet().removeIf((var1) -> {
                return var0.get(var1) == null;
            });
        }
    }

    public static String removeLineReturns(String var0) {
        return var0 == null ? null : var0.replaceAll("\\r\\n|\\r|\\n", "");
    }

    public static <T extends Collection<String>> void toLowerCase(Collection<String> var0, Supplier<T> var1) {
        if (var0 != null && !var0.isEmpty()) {
            Collection var2 = (Collection)var0.stream().map(String::toLowerCase).collect(Collectors.toCollection(var1));
            var0.clear();
            var0.addAll(var2);
        }
    }

    public static String toLowerCase(String var0) {
        return var0 == null ? null : var0.toLowerCase();
    }

    public static String trim(String var0) {
        return var0 == null ? null : var0.trim();
    }

    public static <T> void trimMap(Map<T, String> var0) {
        var0.forEach((var1, var2) -> {
            if (var2 != null) {
                var0.put(var1, var2.trim());
            }

        });
    }

    public static String trimToNull(String var0) {
        if (var0 == null) {
            return null;
        } else {
            var0 = var0.trim();
            return var0.isEmpty() ? null : var0;
        }
    }

    public static ZonedDateTime truncateToMilliseconds(ZonedDateTime var0) {
        return var0 == null ? null : var0.truncatedTo(ChronoUnit.MILLIS);
    }
}
