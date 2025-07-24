package gift.service;

import gift.dto.kakao.KakaoTokenResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class KakaoService {
    private final String clientId = "287d51e6744e5453770637758648a131";
    private final String KAUTH_TOKEN_URL_HOST = "https://kauth.kakao.com";

    public String getKakaoToken(String code) {
        KakaoTokenResponseDto kakaoTokenResponseDto = WebClient.create(KAUTH_TOKEN_URL_HOST).post()
                .uri("/oauth/token")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue("grant_type=authorization_code&client_id=" + clientId + "&redirect_uri=http://localhost:8080&code=" + code)
                .retrieve()
                .bodyToMono(KakaoTokenResponseDto.class)
                .block();

        return kakaoTokenResponseDto.accessToken;
    }

}
