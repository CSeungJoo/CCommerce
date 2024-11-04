package kr.cseungjoo.ccommerce.domain.review;

import jakarta.persistence.*;
import kr.cseungjoo.ccommerce.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String message;

    @Column
    private int score;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;
}
