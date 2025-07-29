package gift.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "options",
        uniqueConstraints =
        @UniqueConstraint(
                columnNames = {"name", "product_id"}))
public class Options {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @Size(max=50)
    @Pattern(regexp = "^[a-zA-Z0-9가-힣\\s()\\[\\]+\\-&/_]*$")
    private String name;

    @Column(name = "quantity")
    @Min(value = 1)
    @Max(value = 100_000_000)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public void decreaseQuantity(int quantity) {
        int restStock= this.quantity - quantity;
        if (restStock < 0) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        this.quantity = restStock;
    }

    protected Options() {}
    public Options(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    //Getters and Setters
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
    public Product getProduct() {return product;}
    public void setProduct(Product product) {this.product = product;}
}
