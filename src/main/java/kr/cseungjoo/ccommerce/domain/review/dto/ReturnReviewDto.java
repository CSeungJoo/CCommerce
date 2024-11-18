package kr.cseungjoo.ccommerce.domain.review.dto;

import kr.cseungjoo.ccommerce.domain.review.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnReviewDto {
    private String message;
    private int score;

    public ReturnReviewDto(Review review) {
        this.message = review.getMessage();
        this.score = review.getScore();
    }
}
