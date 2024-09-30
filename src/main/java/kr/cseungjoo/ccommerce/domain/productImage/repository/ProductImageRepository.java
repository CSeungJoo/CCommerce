package kr.cseungjoo.ccommerce.domain.productImage.repository;

import kr.cseungjoo.ccommerce.domain.productImage.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
