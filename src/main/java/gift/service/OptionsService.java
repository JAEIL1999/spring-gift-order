package gift.service;

import gift.model.Options;
import gift.model.Product;
import gift.repository.OptionsRepository;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionsService {
    private final OptionsRepository optionsRepository;
    private final ProductRepository productRepository;

    public OptionsService(OptionsRepository optionsRepository, ProductRepository productRepository) {
        this.optionsRepository = optionsRepository;
        this.productRepository = productRepository;
    }

    public List<Options> getOptions(Long productId) {
        return optionsRepository.findByProductId(productId);
    }

    public Options newOptions(Long productId, Options options) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 상품을 찾을 수 없습니다: " + productId));
        options.setProduct(product);
        return optionsRepository.save(options);
    }

}
