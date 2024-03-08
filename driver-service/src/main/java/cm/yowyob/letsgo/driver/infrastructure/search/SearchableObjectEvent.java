package cm.yowyob.letsgo.driver.infrastructure.search;


import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class SearchableObjectEvent<T>  extends ApplicationEvent  {

    private final T object;

    public SearchableObjectEvent(Object source, T object) {
        super(source);
        this.object = object;
    }
    
}
