package ml.uchvatov.schedule.auth.controller;

import lombok.AllArgsConstructor;
import ml.uchvatov.schedule.auth.dto.AuthResponse;
import ml.uchvatov.schedule.auth.service.AuthService;
import ml.uchvatov.schedule.model.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody User user) {
        return authService.login(user).map(authResponse -> ResponseEntity.ok().body(authResponse));
    }

    @PostMapping("/signup")
    public Mono<ResponseEntity<Void>> signup(@RequestBody User user) {
        return authService.createUser(user).map(createdUser -> ResponseEntity.ok().build());
    }
}
