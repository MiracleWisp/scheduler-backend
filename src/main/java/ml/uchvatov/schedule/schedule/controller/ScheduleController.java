package ml.uchvatov.schedule.schedule.controller;

import lombok.RequiredArgsConstructor;
import ml.uchvatov.schedule.model.entity.Schedule;
import ml.uchvatov.schedule.schedule.service.ScheduleService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public Mono<Schedule> createSchedule(@RequestBody Schedule schedule) {
        return scheduleService.createSchedule(schedule);
    }

    @PatchMapping
    public Mono<Schedule> updateSchedule(@RequestBody Schedule schedule) {
        return scheduleService.updateSchedule(schedule);
    }
}
