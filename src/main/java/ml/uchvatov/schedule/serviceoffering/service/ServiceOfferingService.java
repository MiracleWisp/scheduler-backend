package ml.uchvatov.schedule.serviceoffering.service;

import lombok.RequiredArgsConstructor;
import ml.uchvatov.schedule.auth.service.AuthenticationFacade;
import ml.uchvatov.schedule.model.entity.ServiceOffering;
import ml.uchvatov.schedule.model.repository.ServiceOfferingRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceOfferingService {

    private final ServiceOfferingRepository serviceOfferingRepository;
    private final AuthenticationFacade authenticationFacade;

    public Flux<ServiceOffering> getServiceOfferingsForCurrentUser() {
        return authenticationFacade.getCurrentUserId().flatMapMany(serviceOfferingRepository::findBySpecialistId);
    }

    public Flux<ServiceOffering> getServiceOfferingsBySpecialistId(UUID uuid) {
        return serviceOfferingRepository.findBySpecialistId(uuid);
    }

    public Mono<ServiceOffering> createServiceOffering(ServiceOffering serviceOffering) {
        return authenticationFacade.getCurrentUserId().flatMap(uuid -> {
            serviceOffering.setSpecialistId(uuid);
            return serviceOfferingRepository.save(serviceOffering);
        });
    }

    public Mono<ServiceOffering> updateServiceOffering(ServiceOffering serviceOffering) {
        return serviceOfferingRepository.save(serviceOffering);
    }

    public Mono<Void> deleteServiceOffering(ServiceOffering serviceOffering) {
        return serviceOfferingRepository.delete(serviceOffering);
    }

    public Mono<Void> deleteServiceOffering(UUID serviceOfferingId) {
        return serviceOfferingRepository.deleteById(serviceOfferingId);
    }
}
