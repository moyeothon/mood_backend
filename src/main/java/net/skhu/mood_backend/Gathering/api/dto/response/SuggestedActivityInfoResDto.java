package net.skhu.mood_backend.Gathering.api.dto.response;

import lombok.Builder;

@Builder
public record SuggestedActivityInfoResDto(
        String activity,
        String description
) {
    public static SuggestedActivityInfoResDto of(String activity, String description) {
        return SuggestedActivityInfoResDto.builder()
                .activity(activity)
                .description(description)
                .build();
    }
}
