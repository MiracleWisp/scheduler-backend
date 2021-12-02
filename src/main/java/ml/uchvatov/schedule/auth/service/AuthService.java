package ml.uchvatov.schedule.auth.service;

import lombok.RequiredArgsConstructor;
import ml.uchvatov.schedule.model.entity.User;
import ml.uchvatov.schedule.model.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<User> createUser(User userToCreate) {
        return userRepository.existsByEmail(userToCreate.getEmail())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, "User with provided email already exists"));
                    }
                    userToCreate.setPassword(passwordEncoder.encode(userToCreate.getPassword()));
                    return userRepository.save(userToCreate);
                });
    }
}

