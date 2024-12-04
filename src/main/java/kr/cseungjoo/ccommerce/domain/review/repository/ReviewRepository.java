package kr.cseungjoo.ccommerce.domain.review.repository;

import kr.cseungjoo.ccommerce.domain.review.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByProduct_Id(long productId, Pageable pageable);
}
