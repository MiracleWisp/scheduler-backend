package ml.uchvatov.schedule.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeslotsDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss.SSSXXX", timezone = "UTC")
    List<OffsetTime> timeslots;
}
