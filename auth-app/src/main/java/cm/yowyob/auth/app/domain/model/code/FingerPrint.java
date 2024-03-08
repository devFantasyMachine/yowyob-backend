package cm.yowyob.auth.app.domain.model.code;


import lombok.AllArgsConstructor;

import java.util.Objects;


@AllArgsConstructor
public class FingerPrint implements Code {

    private String value;

    public static FingerPrint of(String fingerPrint) {

        if (fingerPrint == null)
            return null;

        return new FingerPrint(fingerPrint);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FingerPrint that)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
