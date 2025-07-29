package gift.mapper;

import gift.dto.product.ProductRequestDto;
import gift.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductRequestDto toDto(Product product) {
        ProductRequestDto dto = new ProductRequestDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setImageUrl(product.getImageUrl());
        dto.setOptions(product.getOptions());
        return dto;
    }

}
