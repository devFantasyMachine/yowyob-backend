package cm.yowyob.auth.app.domain.model;



import java.util.UUID;

public class RegistrationConfiguration {

    public Requirable birthDate = new Requirable();
    public boolean confirmPassword;
    public Requirable firstName = new Requirable();
    public UUID formId;
    public Requirable fullName = new Requirable();
    public Requirable lastName = new Requirable();
    public LoginIdType loginIdType;
    public Requirable middleName;
    public Requirable phone;
    public RegistrationType type;


    public static enum LoginIdType {
        email,
        phone,
        username;

        LoginIdType() {
        }
    }

    public static enum RegistrationType {
        basic,
        advanced;

        RegistrationType() {
        }
    }
}
