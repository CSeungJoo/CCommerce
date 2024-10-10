package kr.cseungjoo.ccommerce.domain.product.specification;

import jakarta.persistence.criteria.Predicate;
import kr.cseungjoo.ccommerce.domain.category.Category;
import kr.cseungjoo.ccommerce.domain.product.Product;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> hasOptions(String name, Long categoryId, int minPrice, int maxPrice) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.like(root.get("name"), name));
            if (categoryId != null)
                predicates.add(cb.equal(root.get("categoryId"), categoryId));
            predicates.add(cb.between(root.get("price"), minPrice, maxPrice));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
