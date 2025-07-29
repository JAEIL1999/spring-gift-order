package gift.controller;

import gift.service.KakaoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    private final KakaoService kakaoService;

    public AuthController(KakaoService kakaoService) {
        this.kakaoService = kakaoService;
    }

    @Value("${kakao.client_id}")
    private String client_id;
    @Value("${kakao.redirect_uri}")
    private String redirect_uri;

    @GetMapping("/login/page")
    public String loginPage(Model model) {
        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+client_id+"&redirect_uri="+ redirect_uri;
        model.addAttribute("location", location);
        return "kakao/login";
    }

    @GetMapping
    public ResponseEntity<?> kakaoLogin(
            @RequestParam("code") String accessCode, HttpServletRequest request) {
        // 받은 토큰은 추후에 사용을 위해 남겨둠
        String accessToken = kakaoService.getKakaoToken(accessCode);
        HttpSession session = request.getSession(true);
        session.setAttribute("kakaoAccessToken", accessToken);
        return new ResponseEntity<>("로그인 성공", HttpStatus.OK);
    }
}
