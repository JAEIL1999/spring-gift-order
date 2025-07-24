# spring-gift-order

## Step-00
코드 준비

## Step-01
### 카카오 로그인
---
카카오 로그인을 구현하였습니다.
- 3개의 파일 추가
  - AuthController
  - KakaoTokenResponseDto
  - KakaoService

*동작 설명*
1. 요구사항에 나와있는대로 "http://localhost:8080"로 GET 요청을 보내면 Controller에서 catch
2. Service 계층으로 작업 수행
3. Service에는 https://kauth.kakao.com/oauth/token 로 정보 전달
4. 카카오에서 받은 토큰을 그대로 창에 출력

*보완(첫번째 리뷰 요청 피드백 반영하면서 수정 예정)*
- 카카오 로그인 버튼을 만들어서 버튼 클릭하면 로그인 페이지로 이동할 수 있도록 해야 함
- 인터넷에서 여러 방법들을 검색해보았을 때 yml 파일을 만들어서 해당 파일 안에서 client_id와 redirect_url을 넣는 것들을 보았음