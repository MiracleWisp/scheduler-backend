package ml.uchvatov.schedule.model.repository;

import ml.uchvatov.schedule.model.entity.Schedule;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ScheduleRepository extends ReactiveCrudRepository<Schedule, UUID> {
    Flux<Schedule> findBySpecialistId(UUID specialistId);

    Mono<Schedule> findBySpecialistIdAndDay(UUID specialistId, int day);
}
