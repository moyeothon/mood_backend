package net.skhu.mood_backend.review.api.dto.response;

import lombok.Builder;

@Builder
public record ReviewInfoResDto(
        int starCount,
        String content,
        String writer
) {
    public static ReviewInfoResDto of(int starCount, String content, String writer) {
        return ReviewInfoResDto.builder()
                .starCount(starCount)
                .content(content)
                .writer(writer)
                .build();
    }
}
