package ml.uchvatov.schedule.auth.controller;

import lombok.RequiredArgsConstructor;
import ml.uchvatov.schedule.auth.dto.AuthResponse;
import ml.uchvatov.schedule.auth.service.AuthService;
import ml.uchvatov.schedule.model.entity.User;
import org.springframework.http.ResponseEntity;
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
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody User user) {
        return authService.login(user).map(ResponseEntity::ok);
    }

    @PostMapping("/signup")
    public Mono<ResponseEntity<Void>> signup(@RequestBody User user) {
        return authService.createUser(user).map(createdUser -> ResponseEntity.ok().build());
    }

    @GetMapping("/me")
    public Mono<User> getCurrentUser() {
        return authService.getCurrentUser();
    }
}
