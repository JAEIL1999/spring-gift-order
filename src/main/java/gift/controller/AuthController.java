package gift.controller;

import gift.service.KakaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class AuthController {
    private final KakaoService kakaoService;

    public AuthController(KakaoService kakaoService) {
        this.kakaoService = kakaoService;
    }
    @GetMapping
    public ResponseEntity<?> kakaoLogin(
            @RequestParam("code") String accessCode) {
        String accessToken = kakaoService.getKakaoToken(accessCode);
        return ResponseEntity.ok(accessToken);
    }
}
