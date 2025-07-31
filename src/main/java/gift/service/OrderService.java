package gift.service;

import gift.dto.order.OrderRequestDto;
import gift.dto.order.OrderResponseDto;
import gift.model.Options;
import gift.repository.OptionsRepository;
import gift.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class OrderService {
    private final OptionsRepository optionsRepository;
    private final WishlistRepository wishlistRepository;

    public OrderService(OptionsRepository optionsRepository, WishlistRepository wishlistRepository) {
        this.optionsRepository = optionsRepository;
        this.wishlistRepository = wishlistRepository;
    }

    public OrderResponseDto order(OrderRequestDto orderRequestDto) {
        Options options = optionsRepository.findById(orderRequestDto.getOptionId())
                .orElseThrow(() -> new NoSuchElementException("해당 옵션이 존재하지 않습니다."));

        options.decreaseQuantity(orderRequestDto.getQuantity());

        wishlistRepository.findById(options.getProduct().getId())
                .ifPresent(wishlist -> wishlistRepository.deleteById(wishlist.getId()));

        return new OrderResponseDto(orderRequestDto.getOptionId(),
                orderRequestDto.getQuantity(),
                orderRequestDto.getMessage());
    }
}
