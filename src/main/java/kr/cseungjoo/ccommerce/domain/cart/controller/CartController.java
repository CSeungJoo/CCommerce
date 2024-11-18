package kr.cseungjoo.ccommerce.domain.cart.controller;

import kr.cseungjoo.ccommerce.domain.cart.Cart;
import kr.cseungjoo.ccommerce.domain.cart.dto.ReturnCartDto;
import kr.cseungjoo.ccommerce.domain.cart.service.CartService;
import kr.cseungjoo.ccommerce.domain.cartItem.CartItem;
import kr.cseungjoo.ccommerce.domain.cartItem.dto.AddCarItemDto;
import kr.cseungjoo.ccommerce.domain.cartItem.dto.UpdateCartItemDto;
import kr.cseungjoo.ccommerce.domain.cartItem.service.CartItemService;
import kr.cseungjoo.ccommerce.global.basic.response.BasicResponse;
import kr.cseungjoo.ccommerce.global.redis.service.RedisService;
import kr.cseungjoo.ccommerce.global.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final RedisService redisService;

    @GetMapping("/cart")
    public ResponseEntity<?> getCart() {
        PrincipalDetails principal = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = Long.parseLong(redisService.getData(principal.getToken()));

        Cart cart = cartService.getCartByUserId(userId);

        return BasicResponse.ok(new ReturnCartDto(cart));
    }

    @PostMapping("/cart")
    public ResponseEntity<?> addCartItem(@RequestBody AddCarItemDto dto) {
        PrincipalDetails principal = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = Long.parseLong(redisService.getData(principal.getToken()));

        Cart cart = cartService.getCartByUserId(userId);

        CartItem cartItem = cartItemService.addCartItem(cart, dto.getProduct_id(), dto.getQuantity());

        return BasicResponse.ok(new ReturnCartDto(cart));
    }

    @PutMapping("/cart/{id}")
    public ResponseEntity<?> updateCartItem(@RequestBody UpdateCartItemDto dto) {
        PrincipalDetails principal = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = Long.parseLong(redisService.getData(principal.getToken()));

        CartItem cartItem = cartItemService.updateCartItem(dto.getCartItemId(), dto.getQuantity());

        return BasicResponse.ok(new ReturnCartDto(cartItem.getCart()));
    }
}
