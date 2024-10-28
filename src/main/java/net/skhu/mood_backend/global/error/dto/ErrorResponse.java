package net.skhu.mood_backend.global.error.dto;

public record ErrorResponse(
        int statusCode,
        String message
) {
}