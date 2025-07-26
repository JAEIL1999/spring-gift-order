package gift.service;

import gift.dto.kakao.KakaoTokenResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
public class KakaoService {
    @Value("${kakao.client_id}")
    private String clientId;

    @Value("${kakao.KAUTH_TOKEN_URL_HOST}")
    private String KAUTH_TOKEN_URL_HOST;

    public String getKakaoToken(String code) {
        KakaoTokenResponseDto kakaoTokenResponseDto = WebClient.create(KAUTH_TOKEN_URL_HOST).post()
                .uri("/oauth/token")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue("grant_type=authorization_code&client_id=" + clientId + "&redirect_uri=http://localhost:8080&code=" + code)
                .retrieve()
                .bodyToMono(KakaoTokenResponseDto.class)
                .timeout(Duration.ofSeconds(5))
                .block();

        return kakaoTokenResponseDto.getAccessToken();
    }

}
