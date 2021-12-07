package ml.uchvatov.schedule.model.repository;

import ml.uchvatov.schedule.model.entity.ServiceOffering;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ServiceOfferingRepository extends ReactiveCrudRepository<ServiceOffering, UUID> {
    Flux<ServiceOffering> findBySpecialistId(UUID specialistId);
}
