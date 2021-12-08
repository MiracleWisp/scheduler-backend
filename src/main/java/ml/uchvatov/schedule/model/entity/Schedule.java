package ml.uchvatov.schedule.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Table("schedules")
public class Schedule {

    @Id
    private UUID id;
    private short day;
    @JsonFormat(pattern="hh:mm")
    private LocalTime workStartTime;
    @JsonFormat(pattern="hh:mm")
    private LocalTime workEndTime;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate endDate;
    private UUID specialistId;
}
