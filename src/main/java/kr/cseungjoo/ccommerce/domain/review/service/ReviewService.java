package kr.cseungjoo.ccommerce.domain.review.service;

import kr.cseungjoo.ccommerce.domain.product.service.ProductService;
import kr.cseungjoo.ccommerce.domain.review.Review;
import kr.cseungjoo.ccommerce.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductService productService;

    public Review createReview(String message, int score, long productId) {
        Review review = Review.builder()
                .message(message)
                .score(score)
                .product(productService.getProductById(productId))
                .build();

        return reviewRepository.save(review);
    }
}
