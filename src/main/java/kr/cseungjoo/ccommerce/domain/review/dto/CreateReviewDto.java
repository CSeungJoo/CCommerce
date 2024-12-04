package kr.cseungjoo.ccommerce.domain.review.dto;

import lombok.*;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewDto {
    private String message;
    private int score;
    private long productId;
}
