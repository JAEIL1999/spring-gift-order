package gift.controller;

import gift.dto.order.OrderRequestDto;
import gift.dto.order.OrderResponseDto;
import gift.repository.OptionsRepository;
import gift.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OptionsRepository optionsRepository;
    private final OrderService orderService;
    public OrderController(OptionsRepository optionsRepository, OrderService orderService) {
        this.optionsRepository = optionsRepository;
        this.orderService = orderService;
    }
    @PostMapping
    public ResponseEntity<?> newOrder(@RequestBody OrderRequestDto orderRequestDto, HttpSession session) {
        String accessToken = (String) session.getAttribute("kakaoAccessToken");
        if(accessToken == null) {
            return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }

        OrderResponseDto order = orderService.Order(orderRequestDto);
        String response = orderService.SendKakaoMessage(accessToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
