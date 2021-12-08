package ml.uchvatov.schedule.serviceoffering.controller;

import lombok.RequiredArgsConstructor;
import ml.uchvatov.schedule.model.entity.ServiceOffering;
import ml.uchvatov.schedule.serviceoffering.service.ServiceOfferingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/serviceOfferings")
@RequiredArgsConstructor
public class ServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    @PostMapping
    public Mono<ServiceOffering> createServiceOffering(@RequestBody ServiceOffering serviceOffering) {
        return serviceOfferingService.createServiceOffering(serviceOffering);
    }
}
