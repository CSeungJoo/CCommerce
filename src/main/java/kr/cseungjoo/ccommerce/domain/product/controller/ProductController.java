package kr.cseungjoo.ccommerce.domain.product.controller;

import kr.cseungjoo.ccommerce.domain.product.Product;
import kr.cseungjoo.ccommerce.domain.product.dto.CreateProductDto;
import kr.cseungjoo.ccommerce.domain.product.dto.ReturnProductDto;
import kr.cseungjoo.ccommerce.domain.product.service.ProductService;
import kr.cseungjoo.ccommerce.global.basic.response.BasicResponse;
import kr.cseungjoo.ccommerce.global.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final RedisService redisService;

    @GetMapping("/products")
    public ResponseEntity<?> getProducts(@RequestParam("q") String text, @PageableDefault Pageable pageable) {
        Page<Product> products = productService.searchProduct(text, pageable);

        List<ReturnProductDto> returnProductDtoList = products.stream()
                .map(ReturnProductDto::new)
                .toList();

        Page<ReturnProductDto> returnProductDtoPage = new PageImpl<>(returnProductDtoList, products.getPageable(), products.getTotalPages());
        return BasicResponse.ok(returnProductDtoPage);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") long product_id) {
        Product product = productService.getProductById(product_id);

        return BasicResponse.ok(new ReturnProductDto(product));
    }

    @PostMapping("/products")
    public ResponseEntity<?> createProduct(@RequestBody CreateProductDto dto) {
        Product product = productService.createProduct(dto);

        return BasicResponse.ok(new ReturnProductDto(product));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") long product_id, @RequestBody CreateProductDto dto) {
        Product product = productService.updateProduct(product_id, dto);

        return BasicResponse.ok(new ReturnProductDto(product));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") long product_id) {
        productService.deleteProduct(product_id);

        return BasicResponse.ok("성공적으로 삭제되었습니다.");
    }
}
