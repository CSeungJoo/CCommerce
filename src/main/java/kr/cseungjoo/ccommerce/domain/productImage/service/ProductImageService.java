package kr.cseungjoo.ccommerce.domain.productImage.service;

import kr.cseungjoo.ccommerce.domain.product.Product;
import kr.cseungjoo.ccommerce.domain.productImage.ProductImage;
import kr.cseungjoo.ccommerce.domain.productImage.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductImageService {
    private final ProductImageRepository productImageRepository;

    public ProductImage saveProductImage(MultipartFile file, Product product) {
        //cdn 연결후 url return
        String url = "testValue/"+ file;
        ProductImage productImage = ProductImage.builder().imageUrl(url).product(product).build();
        return productImageRepository.save(productImage);
    }

    public void removeProductImage(String fileName) {
        // cdn에 파일 삭제
    }
    public void removeProductImageAll(List<String> fileNameList) {
        // cdn에 파일 다중 삭제
    }
}
