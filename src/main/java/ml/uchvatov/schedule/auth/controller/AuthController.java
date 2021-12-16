package ml.uchvatov.schedule.auth.controller;

import lombok.RequiredArgsConstructor;
import ml.uchvatov.schedule.auth.dto.AuthResponse;
import ml.uchvatov.schedule.auth.service.AuthService;
import ml.uchvatov.schedule.model.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Mono<AuthResponse> login(@RequestBody User user) {
        return authService.login(user);
    }

    @PostMapping("/signup")
    public Mono<Void> signup(@RequestBody User user) {
        return authService.createUser(user).flatMap(createdUser -> Mono.empty());
    }

    @GetMapping("/me")
    public Mono<User> getCurrentUser() {
        return authService.getCurrentUser();
    }
}
