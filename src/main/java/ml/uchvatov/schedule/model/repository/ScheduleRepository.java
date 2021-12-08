package ml.uchvatov.schedule.model.repository;

import ml.uchvatov.schedule.model.entity.Schedule;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ScheduleRepository extends ReactiveCrudRepository<Schedule, UUID> {
    Flux<Schedule> findBySpecialistId(UUID specialistId);
}
