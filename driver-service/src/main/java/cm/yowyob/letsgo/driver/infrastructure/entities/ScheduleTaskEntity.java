package cm.yowyob.letsgo.driver.infrastructure.entities;

import cm.yowyob.letsgo.driver.domain.model.TaskPriority;
import cm.yowyob.letsgo.driver.domain.model.TaskStatus;
import cm.yowyob.letsgo.driver.domain.model.TaskType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;




@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("schedule_task")
public class ScheduleTaskEntity {


    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, name = "task_id")
    private UUID taskId;

    @Indexed
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, name = "target_id")
    private String driverId;

    private String refId;
    private String ownerId;

    private LocalDateTime fromDate;
    private LocalDateTime toDate;

    private String label;


    private String from_name;
    private String from_city;

    private String to_name;
    private String to_city;

    private Boolean isCronTask;
    private String cron;

    private String note;

    private TaskType taskType;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDateTime createdAt;
    private LocalDateTime executedAt;
    private LocalDateTime abortedAt;
}
