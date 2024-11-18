package kr.cseungjoo.ccommerce.domain.order.dto;

import kr.cseungjoo.ccommerce.domain.model.OrderStatus;
import kr.cseungjoo.ccommerce.domain.order.Order;
import kr.cseungjoo.ccommerce.domain.orderItem.dto.ReturnOrderItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnOrderDto {
    private int totalPrice;
    private OrderStatus status;
    private List<ReturnOrderItemDto> orderItems;

    public ReturnOrderDto(Order order) {
        this.totalPrice = order.getTotalPrice();
        this.status = order.getStatus();
        if (!orderItems.isEmpty())
            this.orderItems = order.getOrderItems().stream().map(ReturnOrderItemDto::new).toList();
    }
}
