package kr.cseungjoo.ccommerce.domain.productImage;

import jakarta.persistence.*;
import kr.cseungjoo.ccommerce.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_images")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;
}

