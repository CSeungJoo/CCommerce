package kr.cseungjoo.ccommerce.domain.product.dto;

import kr.cseungjoo.ccommerce.domain.category.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDto {
    private String name;
    private String description;
    private int price;
    private int stock;
    private Category category;
    private List<MultipartFile> images;
}
