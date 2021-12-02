package ml.uchvatov.schedule.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
public class Service {

    @Id
    private UUID id;
    private String name;
    private String about;
    private double price;
    private int duration;
    private boolean approveRequired;

}
