package ml.uchvatov.schedule.serviceoffering.controller;

import lombok.RequiredArgsConstructor;
import ml.uchvatov.schedule.model.entity.ServiceOffering;
import ml.uchvatov.schedule.serviceoffering.service.ServiceOfferingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/service")
@RequiredArgsConstructor
public class ServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    @GetMapping
    public Flux<ServiceOffering> getServices() {
        return serviceOfferingService.getServiceOfferingsForCurrentUser();
    }

    @PostMapping
    public Mono<ServiceOffering> createServiceOffering(@RequestBody ServiceOffering serviceOffering) {
        return serviceOfferingService.createServiceOffering(serviceOffering);
    }
}
