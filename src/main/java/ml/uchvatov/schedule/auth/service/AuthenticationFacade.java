package ml.uchvatov.schedule.auth.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.UUID;

@Component
public class AuthenticationFacade {
    public Mono<Authentication> getAuthentication() {
        return ReactiveSecurityContextHolder.getContext().map(SecurityContext::getAuthentication);
    }

    public Mono<UUID> getCurrentUserId() {
        return getAuthentication().map(Principal::getName).map(UUID::fromString);
    }
}
