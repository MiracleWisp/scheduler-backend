package ml.uchvatov.schedule.schedule.service;

import lombok.RequiredArgsConstructor;
import ml.uchvatov.schedule.auth.service.AuthenticationFacade;
import ml.uchvatov.schedule.model.entity.Schedule;
import ml.uchvatov.schedule.model.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final AuthenticationFacade authenticationFacade;

    public Flux<Schedule> getAll() {
        return scheduleRepository.findAll();
    }
}
