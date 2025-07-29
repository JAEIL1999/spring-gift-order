package gift.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "image_url")
    private String imageUrl;

    @Transient
    private boolean usableKakao;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Options> options = new ArrayList<>();

    protected Product() {}

    public void update(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product(String name, int price, boolean usableKakao, String imageUrl) {
        this.name = name;
        this.price = price;
        this.usableKakao = usableKakao;
        this.imageUrl = imageUrl;
    }
    public Product(String name, int price, boolean usableKakao) {
        this.name = name;
        this.price = price;
        this.usableKakao = usableKakao;
    }

    //Getters and Setters
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public int getPrice() {return price;}
    public boolean isUsableKakao() {return usableKakao;}
    public String getImageUrl() {return imageUrl;}
    public void setImageUrl(String imageUrl) {this.imageUrl = imageUrl;}
    public List<Options> getOptions() {return options;}
    public void setOptions(List<Options> options) {this.options = options;}
    public void addOption(Options option) {
        this.options.add(option);
        option.setProduct(this);
    }
}
