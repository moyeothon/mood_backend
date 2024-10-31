package net.skhu.mood_backend.gathering.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.skhu.mood_backend.gathering.api.dto.request.GatheringSaveReqDto;
import net.skhu.mood_backend.gathering.api.dto.response.GatheringInfoResDto;
import net.skhu.mood_backend.global.template.RspTemplate;
import net.skhu.mood_backend.member.domain.Member;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface GatheringControllerDocs {

    @Operation(summary = "모임 생성", description = "모임을 생성합니다. host(분위기), relationshipType(관계), peopleCount(인원수), vibe(분위기), averageAge(평균나이), commonInterests(공통 관심사항)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모임 생성 성공")
    })
    RspTemplate<GatheringInfoResDto> createGathering(@AuthenticationPrincipal Member member,
                                                     @RequestBody GatheringSaveReqDto gatheringSaveReqDto)
            throws Exception;

    @Operation(summary = "모임 정보 확인", description = "모임 정보를 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모임 정보 반환 성공")
    })
    RspTemplate<GatheringInfoResDto> gatheringInfo(@AuthenticationPrincipal Member member,
                                                   @PathVariable(name = "id") Long gatheringId);

    @Operation(summary = "모임 정보 수정", description = "모임의 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모임 정보 반환 성공")
    })
    RspTemplate<GatheringInfoResDto> updateGathering(@AuthenticationPrincipal Member member,
                                                     @PathVariable(name = "id") Long gatheringId,
                                                     @RequestBody GatheringSaveReqDto gatheringSaveReqDto)
            throws Exception;

    @Operation(summary = "다른 주제 추천 받기", description = "모임의 정보를 유지하고 대화 주제와 추천 활동을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모임 정보 반환 성공")
    })
    RspTemplate<GatheringInfoResDto> updateConversationTopicsAndSuggestedActivities(
            @AuthenticationPrincipal Member member,
            @PathVariable(name = "id") Long gatheringId) throws Exception;

}
