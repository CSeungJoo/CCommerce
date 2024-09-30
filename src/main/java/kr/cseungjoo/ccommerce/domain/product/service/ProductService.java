package kr.cseungjoo.ccommerce.domain.product.service;

import kr.cseungjoo.ccommerce.domain.product.Product;
import kr.cseungjoo.ccommerce.domain.product.dto.CreateProductDto;
import kr.cseungjoo.ccommerce.domain.product.repository.ProductRepository;
import kr.cseungjoo.ccommerce.domain.productImage.ProductImage;
import kr.cseungjoo.ccommerce.domain.productImage.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductImageService productImageService;

    public Product createProduct(CreateProductDto createProductDto) {
        Product product = Product.builder()
                .price(createProductDto.getPrice())
                .name(createProductDto.getName())
                .stock(createProductDto.getStock())
                .description(createProductDto.getDescription())
                .category(createProductDto.getCategory())
                .build();

        product = productRepository.saveAndFlush(product);
        List<ProductImage> images = new ArrayList<>();
        for (MultipartFile image : createProductDto.getImages()) {
            images.add(productImageService.saveProductImage(image, product));
        }

        product.setImages(images);

        return productRepository.save(product);
    }
}
