package cm.yowyob.auth.app.domain.model.code;


import lombok.AllArgsConstructor;


@AllArgsConstructor
public class DigitCode implements Code {

    private Integer value;

    @Override
    public String getValue() {
        return String.valueOf(value);
    }

}
