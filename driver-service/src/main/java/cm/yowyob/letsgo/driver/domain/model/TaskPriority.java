package cm.yowyob.letsgo.driver.domain.model;


import lombok.Getter;



@Getter
public enum TaskPriority {

    NORMAL("0"),

    URGENT("1");


    private final String level;
    TaskPriority(String  level) {
        this.level = level;
    }


}
