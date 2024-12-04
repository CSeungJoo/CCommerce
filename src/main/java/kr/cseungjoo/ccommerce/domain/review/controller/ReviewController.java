package kr.cseungjoo.ccommerce.domain.review.controller;

import kr.cseungjoo.ccommerce.domain.review.Review;
import kr.cseungjoo.ccommerce.domain.review.dto.CreateReviewDto;
import kr.cseungjoo.ccommerce.domain.review.dto.EditReviewDto;
import kr.cseungjoo.ccommerce.domain.review.dto.ReturnReviewDto;
import kr.cseungjoo.ccommerce.domain.review.service.ReviewService;
import kr.cseungjoo.ccommerce.global.basic.response.BasicResponse;
import kr.cseungjoo.ccommerce.global.redis.service.RedisService;
import kr.cseungjoo.ccommerce.global.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final RedisService redisService;

    @GetMapping("/review/p/{productId}")
    public ResponseEntity<?> getReviewByProductId(@PathVariable("productId") Long productId, @PageableDefault Pageable pageable) {
        Page<Review> reviewPage = reviewService.getReviewByProductId(productId, pageable);

        List<ReturnReviewDto> returnReviewDtoList = reviewPage.stream()
                .map(ReturnReviewDto::new)
                .toList();
        Page<ReturnReviewDto> returnReviewDtoPage = new PageImpl<>(returnReviewDtoList, reviewPage.getPageable(), reviewPage.getTotalPages());

        return BasicResponse.ok(returnReviewDtoPage);
    }

    @GetMapping("/review/r/{reviewId}")
    public ResponseEntity<?> getReviewByReviewId(@PathVariable("reviewId") Long reviewId) {
        Review review = reviewService.getReview(reviewId);

        return BasicResponse.ok(new ReturnReviewDto(review));
    }

    @PostMapping("/review")
    public ResponseEntity<?> createReview(@RequestBody CreateReviewDto createReviewDto) {
        Review review = reviewService.createReview(createReviewDto.getMessage(), createReviewDto.getScore(), createReviewDto.getProductId());

        return BasicResponse.ok(new ReturnReviewDto(review));
    }

    @PutMapping("/review")
    public ResponseEntity<?> editReview(@RequestBody EditReviewDto editReviewDto) {
        PrincipalDetails principal = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = Long.parseLong(redisService.getData(principal.getToken()));
        Review review = reviewService.editReview(editReviewDto.getMessage(), editReviewDto.getScore(), userId, editReviewDto.getReviewId());

        return BasicResponse.ok(new ReturnReviewDto(review));
    }
}
