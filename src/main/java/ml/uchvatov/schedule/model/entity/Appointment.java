package ml.uchvatov.schedule.model.entity;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.UUID;

public class Appointment {

    @Id
    private UUID id;
    private Date date;
    private String status;
    private UUID clientId;
    private UUID serviceId;
}
