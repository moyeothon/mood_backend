package net.skhu.mood_backend.review.api.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record ReviewListResDto(
        List<ReviewInfoResDto> reviews
) {
    public static ReviewListResDto from(List<ReviewInfoResDto> reviews) {
        return ReviewListResDto.builder()
                .reviews(reviews)
                .build();
    }
}
