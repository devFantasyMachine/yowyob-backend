package cm.yowyob.auth.app.domain.model.contacts;

import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Contact {

    @NonNull
    private String value;
    private boolean isVerified;
    private LocalDateTime isVerifiedAt;
    private Instant createdAt;
    private ContactType type;


    protected Contact(String value, ContactType type) {
         this(value, false, LocalDateTime.now(), null, type);
    }



    public Boolean isEmail(){
        return ContactType.EMAIL.equals(this.type);
    }

    public Boolean isPhoneNumber(){
        return ContactType.PHONE.equals(this.type) || ContactType.WHATSAPP.equals(this.type);
    }



    @NonNull
    public String getValue() {
        return value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact that)) return false;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public Contact makeVerified() {
        return new Contact(value, true, LocalDateTime.now(), createdAt, type);
    }

}
