package kr.cseungjoo.ccommerce.domain.cartItem.repository;

import kr.cseungjoo.ccommerce.domain.cartItem.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
