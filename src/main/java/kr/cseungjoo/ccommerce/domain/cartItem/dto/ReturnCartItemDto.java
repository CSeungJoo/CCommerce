package kr.cseungjoo.ccommerce.domain.cartItem.dto;

import kr.cseungjoo.ccommerce.domain.cartItem.CartItem;
import kr.cseungjoo.ccommerce.domain.product.dto.ReturnProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnCartItemDto {

    private ReturnProductDto product;
    private int quantity;
    private int price;

    public ReturnCartItemDto(CartItem cartItem) {
        this.product = new ReturnProductDto(cartItem.getProduct());
        this.quantity = cartItem.getQuantity();
        this.price = cartItem.getPrice();
    }
}
