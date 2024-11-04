package kr.cseungjoo.ccommerce.domain.cart.repository;

import kr.cseungjoo.ccommerce.domain.cart.Cart;
import kr.cseungjoo.ccommerce.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);

}
