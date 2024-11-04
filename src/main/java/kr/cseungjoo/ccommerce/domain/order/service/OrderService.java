package kr.cseungjoo.ccommerce.domain.order.service;

import kr.cseungjoo.ccommerce.domain.model.OrderStatus;
import kr.cseungjoo.ccommerce.domain.order.Order;
import kr.cseungjoo.ccommerce.domain.order.exception.OrderCancelFailException;
import kr.cseungjoo.ccommerce.domain.order.exception.OrderNotFoundException;
import kr.cseungjoo.ccommerce.domain.order.repository.OrderRepository;
import kr.cseungjoo.ccommerce.domain.orderItem.OrderItem;
import kr.cseungjoo.ccommerce.domain.orderItem.service.OrderItemService;
import kr.cseungjoo.ccommerce.domain.product.Product;
import kr.cseungjoo.ccommerce.domain.product.service.ProductService;
import kr.cseungjoo.ccommerce.domain.user.User;
import kr.cseungjoo.ccommerce.domain.user.exception.UserNotOwnerException;
import kr.cseungjoo.ccommerce.domain.user.service.UserService;
import kr.cseungjoo.ccommerce.global.exception.ErrorCode;
import kr.cseungjoo.ccommerce.global.redis.service.RedisService;
import kr.cseungjoo.ccommerce.global.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final RedisService redisService;
    private final UserService userService;
    private final OrderItemService orderItemService;

    public Order getOrder(long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                OrderNotFoundException::new
        );
    }
    public Order createOrder(long productId, int quantity) {
        PrincipalDetails principal =  (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = Long.valueOf(redisService.getData(principal.getToken()));
        User user = userService.getUserById(userId);
        Product product = productService.getProductById(productId);

        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.PREPARING)
                .totalPrice(0)
                .build();

        orderRepository.save(order);

        OrderItem orderItem = orderItemService.createOrderItem(product, quantity, order);

        order.setTotalPrice(orderItem.getPrice() * orderItem.getQuantity());
        order.setOrderItems(Collections.singletonList(orderItem));

        return orderRepository.save(order);
    }

    public Order shippingOrder(long orderId) {
        Order order = getOrder(orderId);

        //payment

        //complete
        order.setStatus(OrderStatus.SHIPPING);

        return orderRepository.save(order);
    }

    public Order completeOrder(long orderId) {
        Order order = getOrder(orderId);

        order.setStatus(OrderStatus.COMPLETED);

        return orderRepository.save(order);
    }

    public Page<Order> getOrders(long userId, Pageable pageable) {
        User user = userService.getUserById(userId);

        return orderRepository.findAllByUser(userId, pageable);
    }

    public void cancelOrder(long orderId, long userId) {
        Order order = getOrder(orderId);

        if(order.getUser().getId() != userId)
            throw new UserNotOwnerException();

        if(order.getStatus() != OrderStatus.PREPARING)
            throw new OrderCancelFailException(ErrorCode.ORDER_CANCEL_FAIL_IN_TRANSIT);

        orderRepository.delete(order);
    }
}
