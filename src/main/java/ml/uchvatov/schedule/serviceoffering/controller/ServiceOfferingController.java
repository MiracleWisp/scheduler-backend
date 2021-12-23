package ml.uchvatov.schedule.serviceoffering.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import ml.uchvatov.schedule.model.dto.TimeslotsDto;
import ml.uchvatov.schedule.model.entity.ServiceOffering;
import ml.uchvatov.schedule.serviceoffering.service.ServiceOfferingService;
import ml.uchvatov.schedule.serviceoffering.service.TimeslotsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.util.UUID;

@RestController
@RequestMapping(value = "/serviceOfferings")
@RequiredArgsConstructor
public class ServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;
    private final TimeslotsService timeslotsService;

    @PostMapping
    @PreAuthorize("hasRole('SPECIALIST')")
    public Mono<ServiceOffering> createServiceOffering(@RequestBody ServiceOffering serviceOffering) {
        return serviceOfferingService.createServiceOffering(serviceOffering);
    }


    @GetMapping("/{serviceId}/timeslots")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    public Mono<TimeslotsDto> getSpecialistTimeslots(@PathVariable String serviceId, @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime date) {
        return timeslotsService.getAvailableTimeslots(UUID.fromString(serviceId), date);
    }
}
