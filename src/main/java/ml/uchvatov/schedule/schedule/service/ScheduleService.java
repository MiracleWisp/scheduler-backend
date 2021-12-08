package ml.uchvatov.schedule.schedule.service;

import lombok.RequiredArgsConstructor;
import ml.uchvatov.schedule.auth.service.AuthenticationFacade;
import ml.uchvatov.schedule.model.entity.Schedule;
import ml.uchvatov.schedule.model.repository.ScheduleRepository;
import ml.uchvatov.schedule.util.MonoUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final AuthenticationFacade authenticationFacade;


    public Mono<Schedule> createSchedule(Schedule schedule) {
        return authenticationFacade.getCurrentUserId().flatMap(uuid -> {
            schedule.setSpecialistId(uuid);
            return scheduleRepository.save(schedule);
        });
    }

    public Mono<Schedule> updateSchedule(Schedule schedule) {
        return scheduleRepository.findById(schedule.getId())
                .transform(mono -> MonoUtils.errorIfEmpty(mono, HttpStatus.NOT_FOUND, "Schedule not found"))
                .filterWhen(foundSchedule -> authenticationFacade.getCurrentUserId().map(uuid -> uuid.equals(foundSchedule.getSpecialistId())))
                .transform(mono -> MonoUtils.errorIfEmpty(mono, HttpStatus.FORBIDDEN, "Trying to change schedule of another specialist"))
                .map(foundSchedule -> {
                    foundSchedule.setWorkStartTime(schedule.getWorkStartTime());
                    foundSchedule.setWorkEndTime(schedule.getWorkEndTime());
                    return foundSchedule;
                })
                .flatMap(scheduleRepository::save);
    }

    public Flux<Schedule> getSchedulesBySpecialistId(UUID specialistId) {
        return scheduleRepository.findBySpecialistId(specialistId);
    }
}
