package kr.cseungjoo.ccommerce.domain.review.repository;

import kr.cseungjoo.ccommerce.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
