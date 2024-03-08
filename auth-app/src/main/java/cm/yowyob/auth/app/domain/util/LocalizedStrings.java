package cm.yowyob.auth.app.domain.util;

import java.util.Locale;
import java.util.TreeMap;

public class LocalizedStrings extends TreeMap<Locale, String> {

    public LocalizedStrings() {
        super((var0, var1x) -> {
            return var0 == null ? -1 : (var1x == null ? 1 : var0.toString().compareTo(var1x.toString()));
        });
    }

    public LocalizedStrings(Locale locale, String value) {
        super((var0, var1x) -> {
            return var0 == null ? -1 : (var1x == null ? 1 : var0.toString().compareTo(var1x.toString()));
        });
        this.put(locale, value);
    }

    public LocalizedStrings(Locale local1, String value1, Locale locale2, String value2) {
        super((var0, var1x) -> {
            return var0 == null ? -1 : (var1x == null ? 1 : var0.toString().compareTo(var1x.toString()));
        });
        this.put(local1, value1);
        this.put(locale2, value2);
    }

    public void normalize() {
        Normalizer.trimMap(this);
        Normalizer.lineReturnsMap(this);
    }

    public void removeEmpty() {
        Normalizer.removeEmpty(this);
    }

}