package cm.yowyob.letsgo.trip.domain.model.schedule;


import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

import static cm.yowyob.letsgo.trip.domain.model.schedule.JobStatus.*;


@Setter
@Getter
public abstract class ScheduledObject {

    private final LocalDateTime beginAt;
    private LocalDateTime endAt;
    private LocalDateTime nextActivation;
    private Duration repeatInterval;
    private final Boolean isCron;
    private String cron;
    private Long repeatCount;
    private Long repeatCountMax;
    private JobStatus jobStatus;

    public ScheduledObject(LocalDateTime beginAt, LocalDateTime endAt, LocalDateTime nextActivation, Duration repeatInterval, Boolean isCron, String cron, Long repeatCount, Long repeatCountMax, JobStatus jobStatus) {
        this.beginAt = beginAt;
        this.endAt = endAt;
        this.nextActivation = nextActivation;
        this.repeatInterval = repeatInterval;
        this.isCron = isCron;
        this.cron = cron;
        this.repeatCount = repeatCount;
        this.repeatCountMax = repeatCountMax;
        this.jobStatus = jobStatus;
    }


    public boolean isApplicable() {

        return (this.jobStatus == SCHEDULED ||
                this.jobStatus == EDITED_SCHEDULED ||
                this.jobStatus == SCHEDULED_STARTED ||
                this.jobStatus == RESUMED);
    }


    public abstract String groupJob();

    public void delete() {
        this.jobStatus = JobStatus.DELETED;
    }

    public void resume() {
        setJobStatus(JobStatus.RESUMED);
    }

    public void pause() {
        setJobStatus(JobStatus.PAUSED);
    }

    public void update() {
        setJobStatus(EDITED_SCHEDULED);
    }

    public void scheduleNew() {
        setJobStatus(SCHEDULED);
    }

    public void startNow() {
        setJobStatus(SCHEDULED_STARTED);
    }


    public abstract String getId();


}
