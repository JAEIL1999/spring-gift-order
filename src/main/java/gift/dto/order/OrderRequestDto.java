package gift.dto.order;

public class OrderRequestDto {
    public Long optionId;
    public int quantity;
    public String message;

    public OrderRequestDto(Long optionId, int quantity) {
        this.optionId = optionId;
        this.quantity = quantity;
    }

    public OrderRequestDto() {}

    public Long getOptionId() {return optionId;}
    public int getQuantity() {return quantity;}
    public String getMessage() {return message;}
}
