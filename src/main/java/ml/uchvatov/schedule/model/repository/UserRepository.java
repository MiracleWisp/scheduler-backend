package ml.uchvatov.schedule.model.repository;

import ml.uchvatov.schedule.model.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository extends ReactiveCrudRepository<User, UUID> {
    Mono<Boolean> existsByEmailIgnoreCase(String email);

    Mono<User> findByEmailIgnoreCase(String email);
}
