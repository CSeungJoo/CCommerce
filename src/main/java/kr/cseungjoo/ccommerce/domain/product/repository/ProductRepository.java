package kr.cseungjoo.ccommerce.domain.product.repository;

import jakarta.persistence.criteria.Predicate;
import kr.cseungjoo.ccommerce.domain.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Page<Product> findAllByNameLikeIgnoreCase(String name, Pageable pageable);
}
