package gift.mapper;

import gift.dto.product.ProductRequestDto;
import gift.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductRequestDto toDto(Product product) {
        return new ProductRequestDto(product.getName(),
                product.getPrice(),
                product.getImageUrl(),
                product.isUsableKakao(),
                product.getOptions());
    }

}
