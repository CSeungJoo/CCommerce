package kr.cseungjoo.ccommerce.domain.review.service;

import kr.cseungjoo.ccommerce.domain.product.service.ProductService;
import kr.cseungjoo.ccommerce.domain.review.Review;
import kr.cseungjoo.ccommerce.domain.review.exception.ReviewNotFoundException;
import kr.cseungjoo.ccommerce.domain.review.repository.ReviewRepository;
import kr.cseungjoo.ccommerce.domain.user.exception.UserNotOwnerException;
import kr.cseungjoo.ccommerce.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductService productService;

    public Review getReview(long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(
                ReviewNotFoundException::new
        );
    }

    public Page<Review> getReviewByProductId(long productId, Pageable pageable) {
        return reviewRepository.findAllByProduct_Id(productId, pageable);
    }

    public Review createReview(String message, int score, long productId) {
        Review review = Review.builder()
                .message(message)
                .score(score)
                .product(productService.getProductById(productId))
                .build();

        return reviewRepository.save(review);
    }

    public Review editReview(String message, int score, long userId, long reviewId) {
        Review review = getReview(reviewId);

        if (review.getUser().getId() != userId)
            throw new UserNotOwnerException();

        review.edit(message, score);

        return reviewRepository.save(review);
    }
}
