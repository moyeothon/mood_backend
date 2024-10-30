package net.skhu.mood_backend.Gathering.api.dto.request;

public record GatheringSaveReqDto(
        String host,
        String relationshipType,
        String peopleCount,
        String vibe,
        String averageAge,
        String commonInterests
) {
}
