package kr.cseungjoo.ccommerce.domain.review;

import jakarta.persistence.*;
import kr.cseungjoo.ccommerce.domain.product.Product;
import kr.cseungjoo.ccommerce.domain.user.User;
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
    @JoinColumn(name = "usersId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    public void edit(String message, int score) {
        this.message = message;
        this.score = score;
    }
}
