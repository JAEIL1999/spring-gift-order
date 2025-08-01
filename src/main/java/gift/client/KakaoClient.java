package gift.client;

import gift.dto.kakao.KakaoTokenResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Component
public class KakaoClient {
    private final WebClient authWebClient;
    private final WebClient apiWebClient;
    private final String clientId;
    private final String redirectUri;

    public KakaoClient(@Value("${kakao.KAUTH_TOKEN_URL_HOST}") String authHost,
                       @Value("${kakao.KAPI_TEMPLATE_MESSAGE}") String apiHost,
                       @Value("${kakao.client_id}") String clientId,
                       @Value("${kakao.redirect_uri}") String redirectUri) {
        this.authWebClient = WebClient.create(authHost);
        this.apiWebClient = WebClient.create(apiHost);
        this.clientId = clientId;
        this.redirectUri = redirectUri;
    }

    public String getAccessToken(String code){
        String body = "grant_type=authorization_code&client_id=" + clientId + "&redirect_uri=" + redirectUri + "&code=" + code;

        KakaoTokenResponseDto responseDto = authWebClient.post()
                .uri("/oauth/token")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(KakaoTokenResponseDto.class)
                .timeout(Duration.ofSeconds(5))
                .block();
        if (responseDto == null || responseDto.getAccessToken() == null) {
            throw new RuntimeException("카카오 액세스 토큰을 받아오는데 실패했습니다.");
        }
        return responseDto.getAccessToken();
    }

    public String sendKakaoMessage(String accessToken){
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

        return apiWebClient.post()
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue("template_object="+templateJson)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
