package cm.yowyob.auth.app.domain.model.contacts;

import org.springframework.util.Assert;

import java.util.regex.Pattern;


public class URLValidator implements ContactValidator {

    private static final Pattern DEFAULT_URL_PATTERN = Pattern.compile("^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    private Pattern pattern;

    public URLValidator(){
        this(DEFAULT_URL_PATTERN);
    }

    protected URLValidator(Pattern pattern) {
        this.pattern = pattern;
    }

    URLValidator withRegex(String regex){
        Assert.hasText(regex, "Regex must not be null");
        this.pattern = Pattern.compile(regex);
        return this;
    }

    @Override
    public boolean isValid(String value) {
        return pattern.matcher(value).matches();
    }

    @Override
    public String getClause() {
        return pattern.pattern();
    }
}
