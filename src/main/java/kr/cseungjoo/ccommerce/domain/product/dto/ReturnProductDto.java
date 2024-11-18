package kr.cseungjoo.ccommerce.domain.product.dto;

import jakarta.persistence.Column;
import kr.cseungjoo.ccommerce.domain.category.Category;
import kr.cseungjoo.ccommerce.domain.product.Product;
import kr.cseungjoo.ccommerce.domain.productImage.ProductImage;
import kr.cseungjoo.ccommerce.domain.review.Review;
import kr.cseungjoo.ccommerce.domain.review.dto.ReturnReviewDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnProductDto {
    private String name;

    private String description;

    private int price;

    private int stock;

    private String category;

    private List<ReturnReviewDto> review;

    private List<String> imgUrl;

    public ReturnProductDto(Product product) {
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.category = product.getCategory().getName();
        if (product.getReviews() != null)
            this.review = product.getReviews().stream()
                .map(ReturnReviewDto::new)
                .toList();
        this.imgUrl = product.getImages().stream()
                .map(ProductImage::getImageUrl)
                .toList();
    }
}
