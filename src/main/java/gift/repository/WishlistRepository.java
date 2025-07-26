package gift.repository;

import gift.model.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    @Query("SELECT w FROM Wishlist w JOIN FETCH w.member m JOIN FETCH w.product p")
    Page<Wishlist> findByUserEmail(Pageable attr0, String userEmail);

    boolean existsByMemberEmailAndProductId(String email, Long productId);

    void deleteByMemberEmailAndProductId(String email, Long productId);
}
