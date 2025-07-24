package gift.controller;

import gift.jwt.JwtUtil;
import gift.model.Product;
import gift.model.Wishlist;
import gift.service.WishlistService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;
    private final JwtUtil jwtUtil;

    public  WishlistController(WishlistService wishlistService, JwtUtil jwtUtil) {
        this.wishlistService = wishlistService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public ResponseEntity<Page<Wishlist>> getWishlist(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, Pageable pageable){
        String userEmail = extractEmailFromHeader(authorization);
        Page<Wishlist> wishlistPage = wishlistService.getWishlist(pageable,userEmail);
        return ResponseEntity.ok(wishlistPage);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Product> AddWishProduct(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, 
            @PathVariable Long productId){
        try {
            String userEmail = extractEmailFromHeader(authorization);
            Product addedProduct = wishlistService.addProduct(userEmail, productId);
            return ResponseEntity.ok(addedProduct);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> DeleteWishProduct(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, 
            @PathVariable Long productId){
        String userEmail = extractEmailFromHeader(authorization);
        wishlistService.deleteProduct(userEmail, productId);
        return ResponseEntity.ok().build();

    }

    private String extractEmailFromHeader(String authorization) {
        if(authorization == null || !authorization.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid authorization");
        }
        String token = authorization.substring(7);

        if(jwtUtil.validateToken(token)) {
            return jwtUtil.getEmailFromToken(token);
        }

        throw new IllegalArgumentException("Invalid authorization");
    }
}
