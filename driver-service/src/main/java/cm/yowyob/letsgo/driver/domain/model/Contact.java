package cm.yowyob.letsgo.driver.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contact {


    public static final String PHONE_PATTERNS
            = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";

    @NonNull
    private String value;
    private boolean isVerified;
    private boolean isFavorite;
    private LocalDateTime isVerifiedAt;
    private LocalDateTime createdAt;
    private ContactType type;


    public Contact(String value, ContactType type) {
         this(value, false, false, LocalDateTime.now(), null, type);
    }


    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @NonNull
    public String getValue() {
        return value;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public void setIsVerifiedAt(LocalDateTime isVerifiedAt) {
        this.isVerifiedAt = isVerifiedAt;
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
}
