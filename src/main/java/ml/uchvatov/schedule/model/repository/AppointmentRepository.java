package ml.uchvatov.schedule.model.repository;

import ml.uchvatov.schedule.model.entity.Appointment;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface AppointmentRepository extends ReactiveCrudRepository<Appointment, UUID> {
    @Query("SELECT * from appointments join services s on s.id = appointments.service_id where s.specialist_id = :specialistId")
    Flux<Appointment> findBySpecialistId(UUID specialistId);

    Flux<Appointment> findByClientId(UUID clientId);

    @Query("select case when count(1) > 0 then true else false end from appointments a join services s on s.id = a.service_id where NOT (:startDate > a.date + make_interval(mins => s.duration) OR a.date > :endDate)")
    Mono<Boolean> existsConflictingAppointments(ZonedDateTime startDate, ZonedDateTime endDate);
}
