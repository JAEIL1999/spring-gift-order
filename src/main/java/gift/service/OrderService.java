package gift.service;

import gift.dto.order.OrderRequestDto;
import gift.dto.order.OrderResponseDto;
import gift.model.Options;
import gift.repository.OptionsRepository;
import gift.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.NoSuchElementException;

@Service
public class OrderService {
    private final OptionsRepository optionsRepository;
    private final WishlistRepository wishlistRepository;

    public OrderService(OptionsRepository optionsRepository, WishlistRepository wishlistRepository) {
        this.optionsRepository = optionsRepository;
        this.wishlistRepository = wishlistRepository;
    }
    @Value("${kakao.KAPI_TEMPLATE_MESSAGE}")
    private String KAPI_TEMPLATE_MESSAGE;

    public OrderResponseDto Order(OrderRequestDto orderRequestDto) {
        Options options = optionsRepository.findById(orderRequestDto.getOptionId())
                .orElseThrow(() -> new NoSuchElementException("해당 옵션이 존재하지 않습니다."));

        options.decreaseQuantity(orderRequestDto.getQuantity());

        wishlistRepository.findById(options.getProduct().getId())
                .ifPresent(wishlist -> wishlistRepository.deleteById(wishlist.getId()));

        return new OrderResponseDto(orderRequestDto.getOptionId(),
                orderRequestDto.getQuantity(),
                orderRequestDto.getMessage());
    }

    public String SendKakaoMessage(String access_token) {
        String templateJson = """
        {
            "object_type": "text",
            "text": "주문이 완료되었습니다. 배송이 시작되면 다시 알려드릴게요!",
            "link": {
                "web_url": "https://developers.kakao.com",
                "mobile_web_url": "https://developers.kakao.com"
            },
            "button_title": "내 주문 확인하기"
        }
        """;
        WebClient webClient = WebClient.create(KAPI_TEMPLATE_MESSAGE);
        return webClient.post()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", "Bearer " + access_token)
                .bodyValue("template_object="+templateJson)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
