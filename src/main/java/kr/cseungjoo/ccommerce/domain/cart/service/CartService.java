package kr.cseungjoo.ccommerce.domain.cart.service;

import kr.cseungjoo.ccommerce.domain.cart.Cart;
import kr.cseungjoo.ccommerce.domain.cart.repository.CartRepository;
import kr.cseungjoo.ccommerce.domain.user.User;
import kr.cseungjoo.ccommerce.domain.user.service.UserService;
import kr.cseungjoo.ccommerce.global.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserService userService;
    private final RedisService redisService;

    public Cart createCart(long userId) {
        User user = userService.getUserById(userId);

        Cart cart = Cart.builder()
                .user(user)
                .build();

        return cartRepository.save(cart);
    }

    public Cart getCartByUserId(long userId) {
        User user = userService.getUserById(userId);

        Optional<Cart> cartOpt = cartRepository.findByUser(user);
        return cartOpt.orElseGet(() -> createCart(userId));
    }

    public Cart cleanCart(long userId) {
        Cart cart = getCartByUserId(userId);

        cart.clean();

        return cartRepository.save(cart);
    }
}
