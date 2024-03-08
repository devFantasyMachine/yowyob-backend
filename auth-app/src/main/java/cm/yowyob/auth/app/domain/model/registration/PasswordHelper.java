package cm.yowyob.auth.app.domain.model.registration;


import java.util.Objects;

public interface PasswordHelper {

    default String encode(String rawPassword){
        return rawPassword;
    }

    default Boolean matches(String rawPassword,  String encodedPassword){

        return Objects.equals(rawPassword, encodedPassword);
    }

    String randomPassword();

}
