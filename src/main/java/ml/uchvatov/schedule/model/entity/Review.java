package ml.uchvatov.schedule.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Table("reviews")
public class Review {

    @Id
    private UUID id;
    private String text;
    private short rating;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
    private ZonedDateTime createdAt;
    private UUID authorId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UUID specialistId;
}
