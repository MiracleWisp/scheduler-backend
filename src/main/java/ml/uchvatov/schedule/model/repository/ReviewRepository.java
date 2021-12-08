package ml.uchvatov.schedule.model.repository;

import ml.uchvatov.schedule.model.entity.Review;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ReviewRepository extends ReactiveCrudRepository<Review, UUID> {
    Flux<Review> findBySpecialistId(UUID specialistId);
}
