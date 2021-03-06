package ml.uchvatov.schedule.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetTime;
import java.util.UUID;

@Data
@Table("schedules")
public class Schedule {

    @Id
    private UUID id;
    private int day;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss.SSSXXX", timezone = "UTC")
    private OffsetTime workStartTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss.SSSXXX", timezone = "UTC")
    private OffsetTime workEndTime;
    /*
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate endDate;
    */
    @JsonIgnore
    private UUID specialistId;
}
