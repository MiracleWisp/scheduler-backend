package ml.uchvatov.schedule.specialist.controller;

import lombok.RequiredArgsConstructor;
import ml.uchvatov.schedule.appointment.service.AppointmentService;
import ml.uchvatov.schedule.model.dto.AppointmentDto;
import ml.uchvatov.schedule.model.entity.Review;
import ml.uchvatov.schedule.model.entity.Schedule;
import ml.uchvatov.schedule.model.entity.ServiceOffering;
import ml.uchvatov.schedule.model.entity.User;
import ml.uchvatov.schedule.review.service.ReviewService;
import ml.uchvatov.schedule.schedule.service.ScheduleService;
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
@RequestMapping(value = "/specialists/{specialistId}")
@RequiredArgsConstructor
public class SpecialistController {

    private final SpecialistService specialistService;
    private final ServiceOfferingService serviceOfferingService;
    private final ReviewService reviewService;
    private final ScheduleService scheduleService;
    private final AppointmentService appointmentService;

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

    @GetMapping("/schedules")
    public Flux<Schedule> getSpecialistSchedules(@PathVariable String specialistId) {
        return scheduleService.getSchedulesBySpecialistId(UUID.fromString(specialistId));
    }

    @GetMapping("/appointments")
    public Flux<AppointmentDto> getSpecialistAppointments(@PathVariable String specialistId) {
        return appointmentService.getAppointmentsBySpecialistId(UUID.fromString(specialistId));
    }
}
