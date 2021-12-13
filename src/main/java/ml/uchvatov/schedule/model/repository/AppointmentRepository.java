package ml.uchvatov.schedule.model.repository;

import ml.uchvatov.schedule.model.dto.AppointmentDto;
import ml.uchvatov.schedule.model.entity.Appointment;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface AppointmentRepository extends ReactiveCrudRepository<Appointment, UUID> {

    @Query("""
            SELECT a.*, c.first_name || ' ' || c.last_name as client_name,
            s.price as service_price, s.duration as service_duration, s.name as service_name,
            sp.first_name || ' ' || sp.last_name as specialist_name, sp.job_title as specialist_job_title
            from appointments a
            join services s on s.id = a.service_id
            join users c on a.client_id = c.id
            join users sp on s.specialist_id = sp.id
            where s.specialist_id = :specialistId
            """)
    Flux<AppointmentDto> findBySpecialistId(UUID specialistId);

    Flux<Appointment> findByClientId(UUID clientId);

    @Query("""
            select case when count(1) > 0 then true else false end from appointments a join services s on s.id = a.service_id
            where a.status != 'CANCELED' AND created_at + make_interval(mins => 1) > now()
            AND NOT (:startDate > a.date + make_interval(mins => s.duration) OR a.date > :endDate)
            """)
    Mono<Boolean> existsConflictingAppointments(ZonedDateTime startDate, ZonedDateTime endDate);
}
