package ml.uchvatov.schedule.specialist.controller;

import lombok.RequiredArgsConstructor;
import ml.uchvatov.schedule.model.entity.Review;
import ml.uchvatov.schedule.model.entity.ServiceOffering;
import ml.uchvatov.schedule.model.entity.User;
import ml.uchvatov.schedule.review.service.ReviewService;
import ml.uchvatov.schedule.serviceoffering.service.ServiceOfferingService;
import ml.uchvatov.schedule.specialist.service.SpecialistService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping(value = "/specialist/{specialistId}")
@RequiredArgsConstructor
public class SpecialistController {

    private final SpecialistService specialistService;
    private final ServiceOfferingService serviceOfferingService;
    private final ReviewService reviewService;

    @GetMapping
    public Mono<User> getSpecialist(@PathVariable String specialistId) {
        return specialistService.getSpecialist(UUID.fromString(specialistId));
    }

    @GetMapping("/serviceOfferings")
    public Flux<ServiceOffering> getSpecialistServiceOfferings(@PathVariable String specialistId) {
        return serviceOfferingService.getServiceOfferingsBySpecialistId(UUID.fromString(specialistId));
    }

    @GetMapping("/reviews")
    public Flux<Review> getSpecialistReviews(@PathVariable String specialistId) {
        return reviewService.getReviewsBySpecialistId(UUID.fromString(specialistId));
    }
}
