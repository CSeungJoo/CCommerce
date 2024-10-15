package kr.cseungjoo.ccommerce.domain.orderItem.repository;

import kr.cseungjoo.ccommerce.domain.orderItem.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
