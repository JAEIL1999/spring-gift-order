package gift.dto.product;

import gift.model.Options;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

// public record 키워드를 사용하고, 괄호 안에 필드를 정의합니다.
public record ProductRequestDto(
        @NotBlank(message = "상품명을 입력해주세요.")
        @Size(max = 15, message = "상품명의 길이는 15이하로 입력해주세요.")
        @Pattern(regexp = "^[a-zA-Z0-9가-힣\\s()\\[\\]+\\-&/_]*$",
                message = "허용되지 않는 입력 방식을 사용하셨습니다. (가능한 특수 기호: ( ), [ ], +, -, &, /, _)")
        String name,

        @NotNull(message = "상품의 가격을 입력해주세요.")
        @Min(value = 100, message = "상품의 가격은 최소 100원입니다.")
        int price,

        String imageUrl,

        boolean usableKakao, // '카카오' 정책 준수 여부를 받는 필드

        @Valid
        @NotEmpty(message = "최소 하나의 옵션이 필요합니다.")
        @Size(max = 10, message = "옵션은 최대 10개까지 등록 가능합니다.")
        List<Options> options
) {
    // 사용자 정의 유효성 검사 로직은 record 본문에 그대로 둘 수 있습니다.
    @AssertTrue(message = "'카카오'가 포함된 상품명은 담당 MD와 협의가 필요합니다.")
    private boolean isKakaoPolicyCompliant() {
        if (name != null && name.contains("카카오")) {
            return usableKakao;
        }
        return true;
    }
}