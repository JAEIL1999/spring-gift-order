package gift.service;

import gift.dto.product.ProductRequestDto;
import gift.model.Options;
import gift.model.Product;
import gift.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    private final String IMAGE_BASE_URL = "https://media.istockphoto.com/id/1667499762/ko/%EB%B2%A1%ED%84%B0/%EC%98%81%EC%97%85%EC%A4%91-%ED%8C%90%EC%A7%80-%EC%83%81%EC%9E%90.jpg?s=612x612&w=0&k=20&c=94uRFQLclgFtnDhE4OfO1tCJdETL3uuBM9ZHD_N4P4Y=";

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public Page<Product> findAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Product findProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("상품이 없습니다"));
    }

    @Transactional
    public void createProduct(ProductRequestDto productDto) {
        Product product;
        if(productDto.getImageUrl()==null || productDto.getImageUrl().isEmpty()) {
            product = new Product(productDto.getName(),
                    productDto.getPrice(),productDto.getUsableKakao());
            product.setImageUrl(IMAGE_BASE_URL);
        } else {
            product = new Product(productDto.getName(), productDto.getPrice(),
                    productDto.getUsableKakao(), productDto.getImageUrl());
        }
        product.addOption(new Options(productDto.getOptionName(), productDto.getOptionQuantity()));
        productRepository.save(product);
        productDto.setId(product.getId());
    }

    @Transactional
    public void updateProduct(ProductRequestDto productDto) {
        Product product = productRepository.findById(productDto.getId())
                .orElseThrow(()->new IllegalArgumentException("상품이 없습니다."));
        product.update(
                productDto.getName(),
                productDto.getPrice(),
                productDto.getImageUrl());
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
