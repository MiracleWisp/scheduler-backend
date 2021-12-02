package ml.uchvatov.schedule.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MonoUtils {
    public static <T> Mono<T> errorIfEmpty(Mono<T> source, HttpStatus status, String reason) {
        return source.switchIfEmpty(Mono.defer(() -> Mono.error(new ResponseStatusException(status, reason))));
    }
}
