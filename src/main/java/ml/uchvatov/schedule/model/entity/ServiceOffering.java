package ml.uchvatov.schedule.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Table("services")
public class ServiceOffering {

    @Id
    private UUID id;
    private String name;
    private String about;
    private double price;
    private int duration;
    private boolean approveRequired;
    private UUID specialistId;
}
