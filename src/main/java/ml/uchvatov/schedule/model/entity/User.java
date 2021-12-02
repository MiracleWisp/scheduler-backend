package ml.uchvatov.schedule.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
public class User {

    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean is_specialist;
    private String jobTitle;
    private String about;
}
