package ml.uchvatov.schedule.model.repository;

import ml.uchvatov.schedule.model.entity.Schedule;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.UUID;

public interface ScheduleRepository extends ReactiveCrudRepository<Schedule, UUID> {
    Flux<Schedule> findBySpecialistIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(UUID specialistId, LocalDate startDate, LocalDate endDate);
}
