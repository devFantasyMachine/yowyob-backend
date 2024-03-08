package cm.yowyob.auth.app.domain.model.code;

public class Base64String implements Code{

    private final String value;

    public Base64String(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
