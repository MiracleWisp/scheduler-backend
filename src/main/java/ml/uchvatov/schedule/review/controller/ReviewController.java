package ml.uchvatov.schedule.review.controller;

import lombok.RequiredArgsConstructor;
import ml.uchvatov.schedule.model.entity.Review;
import ml.uchvatov.schedule.review.service.ReviewService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public Mono<Review> createReview(@RequestBody Review review) {
        return reviewService.createReview(review);
    }
}
