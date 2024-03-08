package cm.yowyob.letsgo.driver.domain.model;


import lombok.Getter;

@Getter
public enum TaskStatus {

    SCHEDULED("SCHEDULED"),

    ONGOING("ONGOING"),

    EXECUTED("EXECUTED"),

    ABORTED("ABORTED");


    private final String label;
    TaskStatus(String label) {
        this.label = label;
    }

}
