package ml.uchvatov.schedule.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Table("service")
public class Service {

    @Id
    private UUID id;
    private String name;
    private String about;
    private double price;
    private int duration;
    private boolean approveRequired;

}
