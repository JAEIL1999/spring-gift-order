package gift.dto.order;

import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class OrderResponseDto {

    @Id
    Long id;

    Long optionId;

    int quantity;

    LocalDateTime orderDateTime;

    String message;

    public OrderResponseDto(Long optionId, int quantity, String message) {
        this.optionId = optionId;
        this.quantity = quantity;
        this.orderDateTime = LocalDateTime.now();
        this.message = message;
    }

    public OrderResponseDto() {}

    public Long getId() {return id;}
    public Long getOptionId() {return optionId;}
    public int getQuantity() {return quantity;}
    public LocalDateTime getOrderDateTime() {return orderDateTime;}
    public String getMessage() {return message;}
}
