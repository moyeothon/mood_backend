package net.skhu.mood_backend.Gathering.api.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record SuggestedActivityListResDto(
        List<SuggestedActivityInfoResDto> suggestedActivityInfoResDtos
) {
    public static SuggestedActivityListResDto from(List<SuggestedActivityInfoResDto> suggestedActivityInfoResDtos) {
        return SuggestedActivityListResDto.builder()
                .suggestedActivityInfoResDtos(suggestedActivityInfoResDtos)
                .build();
    }
}
