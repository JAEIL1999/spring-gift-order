package gift.service;

import gift.client.KakaoClient;
import org.springframework.stereotype.Service;

@Service
public class KakaoService {
    private final KakaoClient kakaoClient;

    public KakaoService(KakaoClient kakaoClient) {
        this.kakaoClient = kakaoClient;
    }

    public String getKakaoToken(String code) {
        return kakaoClient.getAccessToken(code);
    }

    public String SendKakaoMessage(String access_token) {
        return kakaoClient.sendKakaoMessage(access_token);
    }

}
