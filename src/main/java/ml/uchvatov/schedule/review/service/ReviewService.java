package ml.uchvatov.schedule.review.service;

import lombok.RequiredArgsConstructor;
import ml.uchvatov.schedule.auth.service.AuthenticationFacade;
import ml.uchvatov.schedule.model.entity.Review;
import ml.uchvatov.schedule.model.entity.ServiceOffering;
import ml.uchvatov.schedule.model.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final AuthenticationFacade authenticationFacade;

    public Mono<Review> createReview(Review review) {
        return authenticationFacade.getCurrentUserId().flatMap(uuid -> {
            review.setAuthorId(uuid);
            return reviewRepository.save(review);
        });
    }

    public Flux<Review> getReviewsBySpecialistId(UUID specialistId) {
        return reviewRepository.findBySpecialistId(specialistId);
    }
}
