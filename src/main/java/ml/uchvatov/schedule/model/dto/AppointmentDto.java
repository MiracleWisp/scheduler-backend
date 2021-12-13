package ml.uchvatov.schedule.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ml.uchvatov.schedule.model.constant.AppointmentStatus;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class AppointmentDto {
    private UUID id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
    private ZonedDateTime date;
    private AppointmentStatus status;
    private String clientName;
    private String specialistName;
    private String specialistJobTitle;
    private String serviceName;
    private String servicePrice;
    private int serviceDuration;
}
