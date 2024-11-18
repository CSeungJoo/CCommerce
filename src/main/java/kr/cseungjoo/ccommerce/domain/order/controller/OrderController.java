package kr.cseungjoo.ccommerce.domain.order.controller;

import kr.cseungjoo.ccommerce.domain.order.Order;
import kr.cseungjoo.ccommerce.domain.order.dto.CreateOrderDto;
import kr.cseungjoo.ccommerce.domain.order.dto.ReturnOrderDto;
import kr.cseungjoo.ccommerce.domain.order.service.OrderService;
import kr.cseungjoo.ccommerce.domain.product.Product;
import kr.cseungjoo.ccommerce.domain.product.dto.ReturnProductDto;
import kr.cseungjoo.ccommerce.global.basic.response.BasicResponse;
import kr.cseungjoo.ccommerce.global.redis.service.RedisService;
import kr.cseungjoo.ccommerce.global.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final RedisService redisService;

    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderDto createOrderDto) {
        Order order = orderService.createOrder(createOrderDto.getProduct_id(), createOrderDto.getQuantity());

        return BasicResponse.ok(new ReturnOrderDto(order));
    }

    @GetMapping("/orders")
    public ResponseEntity<?> getOrder(@PageableDefault Pageable pageable) {
        PrincipalDetails principal = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = Long.parseLong(redisService.getData(principal.getToken()));

        Page<Order> orders = orderService.getOrders(userId, pageable);

        List<ReturnOrderDto> returnOrderDtoList = orders.stream()
                .map(ReturnOrderDto::new)
                .toList();

        Page<ReturnOrderDto> returnOrderDtoPage = new PageImpl<>(returnOrderDtoList, orders.getPageable(), orders.getTotalPages());
        return BasicResponse.ok(returnOrderDtoPage);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") long orderId) {
        PrincipalDetails principal = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = Long.parseLong(redisService.getData(principal.getToken()));

        Order order = orderService.getOrder(orderId);

        return BasicResponse.ok(new ReturnOrderDto(order));
    }

}