package ml.uchvatov.schedule.schedule.controller;

import lombok.RequiredArgsConstructor;
import ml.uchvatov.schedule.model.entity.Schedule;
import ml.uchvatov.schedule.schedule.service.ScheduleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(value = "/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public Flux<Schedule> createServiceOffering() {
        return scheduleService.getAll();
    }
}
