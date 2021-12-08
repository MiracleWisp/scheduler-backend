package ml.uchvatov.schedule.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ml.uchvatov.schedule.model.constant.AppointmentStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Table("appointments")
public class Appointment {

    @Id
    private UUID id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
    private ZonedDateTime date;
    private AppointmentStatus status;
    @JsonIgnore
    private UUID clientId;
    private UUID serviceId;
}
