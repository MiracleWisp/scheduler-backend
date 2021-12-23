package ml.uchvatov.schedule.auth.service;

import lombok.RequiredArgsConstructor;
import ml.uchvatov.schedule.auth.dto.AuthResponse;
import ml.uchvatov.schedule.model.constant.Role;
import ml.uchvatov.schedule.model.entity.Schedule;
import ml.uchvatov.schedule.model.entity.User;
import ml.uchvatov.schedule.model.repository.ScheduleRepository;
import ml.uchvatov.schedule.model.repository.UserRepository;
import ml.uchvatov.schedule.util.JWTUtil;
import ml.uchvatov.schedule.util.MonoUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.OffsetTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationFacade authenticationFacade;
    private final JWTUtil jwtUtil;

    @Transactional
    public Mono<User> createUser(User userToCreate) {
        return userRepository.existsByEmailIgnoreCase(userToCreate.getEmail())
                .filter(exists -> !exists)
                .transform(mono -> MonoUtils.errorIfEmpty(mono, HttpStatus.UNAUTHORIZED, "Invalid email or password"))
                .flatMap(exists -> {
                    userToCreate.setPassword(passwordEncoder.encode(userToCreate.getPassword()));
                    if (userToCreate.isSpecialist()) {
                        userToCreate.setRoles(List.of(Role.ROLE_USER, Role.ROLE_SPECIALIST));
                    } else {
                        userToCreate.setRoles(List.of(Role.ROLE_USER));
                    }
                    return userRepository.save(userToCreate);
                })
                .flatMap(createdUser -> {
                    if (createdUser.isSpecialist()) {
                        List<Schedule> schedules = new ArrayList<>();
                        IntStream.range(0, 7).forEach(day -> {
                            Schedule schedule = new Schedule();
                            schedule.setDay(day);
                            schedule.setSpecialistId(createdUser.getId());
                            schedule.setWorkEndTime(OffsetTime.parse("10:00:00+00:00"));
                            schedule.setWorkStartTime(OffsetTime.parse("10:00:00+00:00"));
                            schedules.add(schedule);
                        });
                        return scheduleRepository.saveAll(schedules).then(Mono.just(createdUser));
                    }
                    return Mono.just(createdUser);
                });
    }

    public Mono<AuthResponse> login(User user) {
        return userRepository.findByEmailIgnoreCase(user.getEmail())
                .transform(mono -> MonoUtils.errorIfEmpty(mono, HttpStatus.UNAUTHORIZED, "Invalid email or password"))
                .filter(foundUser -> passwordEncoder.matches(user.getPassword(), foundUser.getPassword()))
                .transform(mono -> MonoUtils.errorIfEmpty(mono, HttpStatus.UNAUTHORIZED, "Invalid email or password"))
                .map(foundUser -> {
                    var authResponse = new AuthResponse();
                    authResponse.setToken(jwtUtil.generateToken(foundUser));
                    return authResponse;
                });
    }

    public Mono<User> getCurrentUser() {
        return authenticationFacade.getCurrentUserId()
                .flatMap(userRepository::findById)
                .transform(mono -> MonoUtils.errorIfEmpty(mono, HttpStatus.UNAUTHORIZED, "UNAUTHORIZED"));
    }
}

