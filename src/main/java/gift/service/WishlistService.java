package gift.service;

import gift.model.Product;
import gift.model.User;
import gift.model.Wishlist;
import gift.repository.ProductRepository;
import gift.repository.UserRepository;
import gift.repository.WishlistRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.NoSuchElementException;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    public WishlistService(WishlistRepository wishlistRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Page<Wishlist> getWishlist(Pageable pageable, String email) {
        return wishlistRepository.findByUserEmail(pageable, email);
    }

    public Product addProduct(String email, Long productId) {
        boolean exist = wishlistRepository.existsByMemberEmailAndProductId(email, productId);
        if (!exist) {
            try {
                Product product = productRepository
                        .findById(productId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "상품이 존재하지 않습니다"));
                User member = userRepository
                        .findByEmail(email)
                        .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자가 존재하지 않습니다"));
                Wishlist wishlist = new Wishlist(member, product);
                wishlistRepository.save(wishlist);
                return product;
            } catch (NoSuchElementException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "상품이 존재하지 않습니다");
            }
        }
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "상품이 존재하지 않습니다"));


    }

    public void deleteProduct(String email, Long productId) {
        wishlistRepository.deleteByMemberEmailAndProductId(email, productId);
    }
}
