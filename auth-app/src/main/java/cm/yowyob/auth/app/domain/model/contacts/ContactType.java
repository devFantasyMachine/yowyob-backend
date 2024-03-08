package cm.yowyob.auth.app.domain.model.contacts;


import lombok.Getter;

/**
 *  contact type. EMAIL | PHONE | URL | WHATSAPP | YOWYOB
 */
@Getter
public enum ContactType {

    EMAIL("EMAIL"),
    PHONE("PHONE_NUMBER"),
    YOWYOB("YOWYOB"),
    WHATSAPP("WHATSAPP");

    private final String value;

    ContactType(String typeName){
        this.value = typeName;
    }

}
