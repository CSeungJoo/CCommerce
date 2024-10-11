package kr.cseungjoo.ccommerce.domain.cart.service;

import kr.cseungjoo.ccommerce.domain.cart.Cart;
import kr.cseungjoo.ccommerce.domain.cart.repository.CartRepository;
import kr.cseungjoo.ccommerce.domain.user.User;
import kr.cseungjoo.ccommerce.domain.user.service.UserService;
import kr.cseungjoo.ccommerce.global.redis.service.RedisService;
import kr.cseungjoo.ccommerce.global.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserService userService;
    private final RedisService redisService;

    public Cart createCart() {

        PrincipalDetails principal =  (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = Long.valueOf(redisService.getData(principal.getToken()));
        User user = userService.getUserById(userId);

        Cart cart = Cart.builder()
                .user(user)
                .build();

        return cartRepository.save(cart);
    }
}
