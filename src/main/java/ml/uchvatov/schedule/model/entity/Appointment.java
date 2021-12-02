package ml.uchvatov.schedule.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;
import java.util.UUID;

@Data
@Table("appointments")
public class Appointment {

    @Id
    private UUID id;
    private Date date;
    private String status;
    private UUID clientId;
    private UUID serviceId;
}
