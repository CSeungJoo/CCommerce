package kr.cseungjoo.ccommerce.domain.cart.repository;

import kr.cseungjoo.ccommerce.domain.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
