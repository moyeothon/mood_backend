package net.skhu.mood_backend.review.api.dto.request;

public record ReviewSaveReqDto(
        int starCount,
        String content
) {

}
