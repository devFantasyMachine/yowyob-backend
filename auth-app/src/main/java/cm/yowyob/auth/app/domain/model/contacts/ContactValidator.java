package cm.yowyob.auth.app.domain.model.contacts;




/**
 *  contact validator
 *
 */
public interface ContactValidator {

    /**
     * is valid
     *
     * @param value the value to checkRegistrationRequest
     * @return {@link Boolean}
     */
    default boolean isValid(String value) {
        return false;
    }

    /**
     * get clause
     *
     * @return {@link String}
     * @see String
     */
    String getClause();
}
