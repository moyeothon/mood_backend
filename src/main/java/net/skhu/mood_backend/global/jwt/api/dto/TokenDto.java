package net.skhu.mood_backend.global.jwt.api.dto;

import lombok.Builder;

@Builder
public record TokenDto(
        String accessToken
) {
}
