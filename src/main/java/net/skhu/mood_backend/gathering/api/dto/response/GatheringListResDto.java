package net.skhu.mood_backend.gathering.api.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record GatheringListResDto(
        String myNickname,
        List<GatheringInfoResDto> gatherings
) {
    public static GatheringListResDto from(List<GatheringInfoResDto> gatherings, String nickname) {
        return GatheringListResDto.builder()
                .gatherings(gatherings)
                .myNickname(nickname)
                .build();
    }
}
