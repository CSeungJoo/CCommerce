package kr.cseungjoo.ccommerce.domain.cartItem.service;

import kr.cseungjoo.ccommerce.domain.cart.Cart;
import kr.cseungjoo.ccommerce.domain.cartItem.CartItem;
import kr.cseungjoo.ccommerce.domain.cartItem.exception.InsufficientStockException;
import kr.cseungjoo.ccommerce.domain.cartItem.repository.CartItemRepository;
import kr.cseungjoo.ccommerce.domain.product.Product;
import kr.cseungjoo.ccommerce.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductService productService;

    public CartItem addCartItem(Cart cart, long productId, int quantity) {
        Product product = productService.getProductById(productId);

        if (product.getStock() < quantity)
            throw new InsufficientStockException();

        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .price(product.getPrice() * quantity)
                .quantity(quantity)
                .product(product)
                .build();

        return cartItemRepository.save(cartItem);
    }
}
