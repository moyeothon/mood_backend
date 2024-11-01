package net.skhu.mood_backend.gathering.api.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record GatheringListResDto(
        List<GatheringInfoResDto> gatherings
) {
    public static GatheringListResDto from(List<GatheringInfoResDto> gatherings) {
        return GatheringListResDto.builder()
                .gatherings(gatherings)
                .build();
    }
}
