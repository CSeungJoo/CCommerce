package kr.cseungjoo.ccommerce.domain.orderItem.dto;

import kr.cseungjoo.ccommerce.domain.orderItem.OrderItem;
import kr.cseungjoo.ccommerce.domain.product.dto.ReturnProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnOrderItemDto {

    private ReturnProductDto product;
    private int quantity;
    private int price;

    public ReturnOrderItemDto(OrderItem orderItem) {
        this.product = new ReturnProductDto(orderItem.getProduct());
        this.quantity = orderItem.getQuantity();
        this.price = orderItem.getPrice();
    }
}
