package ml.uchvatov.schedule.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.UUID;

@Data
public class Review {

    @Id
    private UUID id;
    private String text;
    private short rating;
    private Instant createdAt;
    private UUID authorId;
    private UUID specialistId;
}
