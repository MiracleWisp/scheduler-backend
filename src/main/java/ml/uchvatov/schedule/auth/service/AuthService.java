package ml.uchvatov.schedule.auth.service;

import lombok.RequiredArgsConstructor;
import ml.uchvatov.schedule.auth.dto.AuthResponse;
import ml.uchvatov.schedule.model.constant.Role;
import ml.uchvatov.schedule.model.entity.User;
import ml.uchvatov.schedule.model.repository.UserRepository;
import ml.uchvatov.schedule.util.JWTUtil;
import ml.uchvatov.schedule.util.MonoUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    public Mono<User> createUser(User userToCreate) {
        return userRepository.existsByEmail(userToCreate.getEmail())
                .filter(exists -> !exists)
                .transform(mono -> MonoUtils.errorIfEmpty(mono, HttpStatus.CONFLICT, "Invalid email or password"))
                .flatMap(exists -> {
                    userToCreate.setPassword(passwordEncoder.encode(userToCreate.getPassword()));
                    userToCreate.setRoles(List.of(Role.ROLE_USER));
                    return userRepository.save(userToCreate);
                });
    }

    public Mono<AuthResponse> login(User user) {
        return userRepository.findByEmail(user.getEmail())
                .transform(mono -> MonoUtils.errorIfEmpty(mono, HttpStatus.CONFLICT, "Invalid email or password"))
                .filter(foundUser -> passwordEncoder.matches(user.getPassword(), foundUser.getPassword()))
                .transform(mono -> MonoUtils.errorIfEmpty(mono, HttpStatus.CONFLICT, "Invalid email or password"))
                .map(foundUser -> {
                    var authResponse = new AuthResponse();
                    authResponse.setToken(jwtUtil.generateToken(foundUser));
                    return authResponse;
                });
    }
}

