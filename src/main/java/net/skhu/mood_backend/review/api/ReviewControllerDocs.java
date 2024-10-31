package net.skhu.mood_backend.review.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.skhu.mood_backend.global.template.RspTemplate;
import net.skhu.mood_backend.member.domain.Member;
import net.skhu.mood_backend.review.api.dto.request.ReviewSaveReqDto;
import net.skhu.mood_backend.review.api.dto.response.ReviewInfoResDto;
import net.skhu.mood_backend.review.api.dto.response.ReviewListResDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

public interface ReviewControllerDocs {

    @Operation(summary = "후기 작성", description = "후기를 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "후기 작성 성공")
    })
    RspTemplate<ReviewInfoResDto> createReview(@AuthenticationPrincipal Member member,
                                               @RequestBody ReviewSaveReqDto reviewSaveReqDto);

    @Operation(summary = "후기 리스트 조회", description = "후기 리스트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "후기 리스트 조회 성공")
    })
    RspTemplate<ReviewListResDto> getReviews(@AuthenticationPrincipal Member member);
}
