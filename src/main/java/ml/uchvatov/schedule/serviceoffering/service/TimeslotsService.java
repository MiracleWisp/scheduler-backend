package ml.uchvatov.schedule.serviceoffering.service;

import lombok.RequiredArgsConstructor;
import ml.uchvatov.schedule.model.dto.TimeslotsDto;
import ml.uchvatov.schedule.model.entity.Appointment;
import ml.uchvatov.schedule.model.repository.AppointmentRepository;
import ml.uchvatov.schedule.model.repository.ScheduleRepository;
import ml.uchvatov.schedule.model.repository.ServiceOfferingRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static java.time.ZoneOffset.UTC;

@Service
@RequiredArgsConstructor
public class TimeslotsService {

    private final AppointmentRepository appointmentRepository;
    private final ScheduleRepository scheduleRepository;
    private final ServiceOfferingRepository serviceOfferingRepository;

    public Mono<TimeslotsDto> getAvailableTimeslots(UUID serviceId, ZonedDateTime date) {

        return serviceOfferingRepository.findById(serviceId)
                .flatMap(serviceOffering -> Mono.zip(
                        Mono.just(serviceOffering),
                        scheduleRepository.findBySpecialistIdAndDay(serviceOffering.getSpecialistId(), date.getDayOfWeek().ordinal()),
                        getAppointments(serviceOffering.getSpecialistId(), date)
                )).map(tuple -> {
                    int duration = tuple.getT1().getDuration();
                    OffsetTime workStartTime = tuple.getT2().getWorkStartTime();
                    OffsetTime workEndTime = tuple.getT2().getWorkEndTime();
                    OffsetTime timeslot = workStartTime;
                    List<OffsetTime> timeslots = new ArrayList<>();
                    for (Appointment appointment : tuple.getT3()) {
                        while (!timeslot.plusMinutes(duration).isAfter(appointment.getDate().toOffsetDateTime().toOffsetTime())) {
                            timeslots.add(timeslot);
                            timeslot = timeslot.plusMinutes(15);
                        }
                        timeslot = appointment.getEndDate().toOffsetDateTime().toOffsetTime().withOffsetSameInstant(UTC);
                    }
                    while (!timeslot.plusMinutes(duration).isAfter(workEndTime)) {
                        timeslots.add(timeslot);
                        timeslot = timeslot.plusMinutes(15);
                    }
                    return new TimeslotsDto(timeslots);
                });

    }

    private Mono<List<Appointment>> getAppointments(UUID specialistId, ZonedDateTime date) {
        ZonedDateTime endDate = date.plus(1, ChronoUnit.DAYS);
        return appointmentRepository.findBySpecialistIdAndDateBetween(specialistId, date, endDate)
                .sort(Comparator.comparing(Appointment::getDate))
                .collectList();
    }
}
