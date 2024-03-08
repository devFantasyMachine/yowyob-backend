package cm.yowyob.auth.app.domain.model.contacts;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.regex.Pattern;

@Component
public class PhoneValidator implements ContactValidator {


    private static final String patterns
            = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";


    public static final Pattern DEFAULT_PHONE_PATTERN = Pattern.compile(patterns);


    private Pattern phonePatter;

    public PhoneValidator(){
        this(DEFAULT_PHONE_PATTERN);
    }

    public PhoneValidator(Pattern phonePatter) {
        this.phonePatter = phonePatter;
    }

    public PhoneValidator withRegex(String regex){

        Assert.hasText(regex, "Regex must not be null");
        this.phonePatter = Pattern.compile(regex);
        return this;
    }

    @Override
    public boolean isValid(String value) {
        return phonePatter.matcher(value).matches();
    }

    @Override
    public String getClause() {
        return phonePatter.pattern();
    }


}
