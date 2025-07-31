package gift.controller;

import gift.dto.order.OrderRequestDto;
import gift.dto.order.OrderResponseDto;
import gift.service.KakaoService;
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
    private final OrderService orderService;
    private final KakaoService kakaoService;
    public OrderController(OrderService orderService, KakaoService kakaoService) {
        this.orderService = orderService;
        this.kakaoService = kakaoService;
    }
    @PostMapping
    public ResponseEntity<?> newOrder(@RequestBody OrderRequestDto orderRequestDto, HttpSession session) {
        String accessToken = (String) session.getAttribute("kakaoAccessToken");
        if(accessToken == null) {
            return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }

        OrderResponseDto order = orderService.order(orderRequestDto);
        String response = kakaoService.SendKakaoMessage(accessToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
