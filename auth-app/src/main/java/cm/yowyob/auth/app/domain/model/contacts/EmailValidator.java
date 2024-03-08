package cm.yowyob.auth.app.domain.model.contacts;

import org.springframework.util.Assert;

import java.util.regex.Pattern;


public class EmailValidator implements ContactValidator {

    private static final Pattern DEFAULT_EMAIL_PATTERN = Pattern.compile("^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    private Pattern emailPatter;

    public EmailValidator(){
        this(DEFAULT_EMAIL_PATTERN);
    }

    protected EmailValidator(Pattern emailPatter) {
        this.emailPatter = emailPatter;
    }

    EmailValidator withRegex(String regex){
        Assert.hasText(regex, "Regex must not be null");
        this.emailPatter = Pattern.compile(regex);
        return this;
    }

    @Override
    public boolean isValid(String value) {
        return emailPatter.matcher(value).matches();
    }

    @Override
    public String getClause() {
        return emailPatter.pattern();
    }
}
