package kr.cseungjoo.ccommerce.domain.cart.dto;

import kr.cseungjoo.ccommerce.domain.cart.Cart;
import kr.cseungjoo.ccommerce.domain.cartItem.dto.ReturnCartItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnCartDto {
    private List<ReturnCartItemDto> cartItems;

    public ReturnCartDto(Cart cart) {
        if (!cart.getCartItems().isEmpty())
            this.cartItems = cart.getCartItems().stream()
                .map(ReturnCartItemDto::new)
                .toList();
    }
}
