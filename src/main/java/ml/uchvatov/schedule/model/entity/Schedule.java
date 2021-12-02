package ml.uchvatov.schedule.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Table("schedules")
public class Schedule {

    @Id
    private UUID id;
    private short day;
    private LocalTime workStartTime;
    private LocalTime workEndTime;
    private Instant startDate;
    private Instant endDate;
    private UUID specialistId;
}
