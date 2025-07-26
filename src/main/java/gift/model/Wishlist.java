package gift.model;

import jakarta.persistence.*;

@Entity
@Table(name = "wishlist")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private User member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public Wishlist() {
    }

    public Wishlist(User member, Product product) {
        this.member = member;
        this.product = product;
    }

    public Long getId() { return id; }
    public User getUser() { return member; }
    public Product getProduct() { return product; }
}
