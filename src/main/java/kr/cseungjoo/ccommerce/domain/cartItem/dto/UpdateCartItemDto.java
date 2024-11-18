package kr.cseungjoo.ccommerce.domain.cartItem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCartItemDto {
    private long cartItemId;
    private int quantity;

}
