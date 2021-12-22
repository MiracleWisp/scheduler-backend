package ml.uchvatov.schedule.specialist.service;

import lombok.RequiredArgsConstructor;
import ml.uchvatov.schedule.model.entity.User;
import ml.uchvatov.schedule.model.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecialistService {
    private final UserRepository userRepository;

    public Mono<User> getSpecialist(UUID specialistId) {
        return userRepository.findById(specialistId);
    }
}
