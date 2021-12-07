package ml.uchvatov.schedule.specialist.service;

import ml.uchvatov.schedule.model.entity.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class SpecialistService {
    public Mono<User> getSpecialist(UUID specialistId) {
        return null;
    }
}
