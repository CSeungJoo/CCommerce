package kr.cseungjoo.ccommerce.domain.orderItem.service;

import kr.cseungjoo.ccommerce.domain.order.Order;
import kr.cseungjoo.ccommerce.domain.orderItem.OrderItem;
import kr.cseungjoo.ccommerce.domain.orderItem.repository.OrderItemRepository;
import kr.cseungjoo.ccommerce.domain.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItem createOrderItem(Product product, int quantity, Order order) {
        return orderItemRepository.save(OrderItem.builder()
                .order(order)
                .quantity(quantity)
                .price(product.getPrice())
                .product(product)
                .build());
    }
}
