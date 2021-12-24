package ml.uchvatov.schedule.appointment.service;

import lombok.RequiredArgsConstructor;
import ml.uchvatov.schedule.auth.service.AuthenticationFacade;
import ml.uchvatov.schedule.model.constant.AppointmentStatus;
import ml.uchvatov.schedule.model.dto.AppointmentDto;
import ml.uchvatov.schedule.model.entity.Appointment;
import ml.uchvatov.schedule.model.entity.Schedule;
import ml.uchvatov.schedule.model.entity.ServiceOffering;
import ml.uchvatov.schedule.model.repository.AppointmentRepository;
import ml.uchvatov.schedule.model.repository.ScheduleRepository;
import ml.uchvatov.schedule.model.repository.ServiceOfferingRepository;
import ml.uchvatov.schedule.util.MonoUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.OffsetTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ScheduleRepository scheduleRepository;
    private final ServiceOfferingRepository serviceOfferingRepository;
    private final AuthenticationFacade authenticationFacade;

    public Mono<Appointment> createAppointment(Appointment appointment) {

        return serviceOfferingRepository.findById(appointment.getServiceId())
                .transform(mono -> MonoUtils.errorIfEmpty(mono, HttpStatus.NOT_FOUND, "Service offering not found"))
                .zipWhen(serviceOffering -> scheduleRepository.findBySpecialistIdAndDay(serviceOffering.getSpecialistId(), appointment.getDate().getDayOfWeek().ordinal()))
                .transform(mono -> MonoUtils.errorIfEmpty(mono, HttpStatus.NOT_FOUND, "Specialist has no work hours at chosen day of week"))
                .filter(tuple -> {
                    Schedule schedule = tuple.getT2();
                    OffsetTime startTime = appointment.getDate().toOffsetDateTime().toOffsetTime();
                    appointment.setEndDate(appointment.getDate().plusMinutes(tuple.getT1().getDuration()));
                    OffsetTime endTime = startTime.plus(tuple.getT1().getDuration(), ChronoUnit.MINUTES);
                    return !startTime.isBefore(schedule.getWorkStartTime()) && !endTime.isAfter(schedule.getWorkEndTime());
                })
                .transform(mono -> MonoUtils.errorIfEmpty(mono, HttpStatus.NOT_FOUND, "Specialist has no work hours at chosen time"))
                .flatMap(tuple ->
                {
                    ServiceOffering serviceOffering = tuple.getT1();
                    return appointmentRepository.existsConflictingAppointments(appointment.getDate(), appointment.getDate().plus(serviceOffering.getDuration(), ChronoUnit.MINUTES), serviceOffering.getId());
                })
                .filter(exists -> !exists)
                .transform(mono -> MonoUtils.errorIfEmpty(mono, HttpStatus.CONFLICT, "Timeslot already in use"))
                .flatMap(tuple -> authenticationFacade.getCurrentUserId())
                .flatMap(uuid -> {
                    appointment.setClientId(uuid);
                    appointment.setStatus(AppointmentStatus.DRAFT);
                    return appointmentRepository.save(appointment);
                });
    }

    public Mono<Appointment> updateAppointment(Appointment appointment) {
        return appointmentRepository.findById(appointment.getId())
                .transform(mono -> MonoUtils.errorIfEmpty(mono, HttpStatus.NOT_FOUND, "Appointment not found"))
                .filterWhen(foundAppointment -> authenticationFacade.getCurrentUserId().map(uuid -> uuid.equals(foundAppointment.getClientId())))
                .transform(mono -> MonoUtils.errorIfEmpty(mono, HttpStatus.FORBIDDEN, "Trying to change appointment of another customer"))
                .map(foundAppointment -> {
                    foundAppointment.setStatus(appointment.getStatus());
                    return foundAppointment;
                })
                .flatMap(appointmentRepository::save);
    }

    public Flux<AppointmentDto> getAppointmentsBySpecialistId(UUID specialistId) {
        return appointmentRepository.findBySpecialistId(specialistId);
    }

    public Flux<AppointmentDto> getCurrentUserAppointments() {
        return authenticationFacade.getCurrentUserId()
                .flatMapMany(appointmentRepository::findByClientId);
    }

    public Mono<Void> deleteAppointment(UUID appointmentId) {
        return appointmentRepository.deleteById(appointmentId);
    }
}
