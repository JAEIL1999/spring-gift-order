package gift.repository;

import gift.model.Options;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OptionsRepository extends JpaRepository<Options, Long> {
    List<Options> findByProductId(Long productId);
    void deleteAllByProductId(Long productId);
}
