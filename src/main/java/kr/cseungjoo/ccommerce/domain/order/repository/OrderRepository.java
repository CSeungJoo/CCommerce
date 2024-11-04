package kr.cseungjoo.ccommerce.domain.order.repository;

import kr.cseungjoo.ccommerce.domain.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByUser(long userId, Pageable pageable);
}
