package ml.uchvatov.schedule.appointment.controller;

import lombok.RequiredArgsConstructor;
import ml.uchvatov.schedule.appointment.service.AppointmentService;
import ml.uchvatov.schedule.model.entity.Appointment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public Mono<Appointment> createAppointment(@RequestBody Appointment appointment) {
        return appointmentService.createAppointment(appointment);
    }

    @PatchMapping("/{appointmentId}")
    public Mono<Appointment> updateAppointment(@RequestBody Appointment appointment, @PathVariable String appointmentId) {
        if (!appointmentId.equals(appointment.getId().toString())) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request"));
        }
        return appointmentService.updateAppointment(appointment);
    }

}
