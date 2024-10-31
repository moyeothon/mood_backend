package net.skhu.mood_backend.gathering.api.dto.response;

import lombok.Builder;

@Builder
public record ConversationTopicInfoResDto(
        String topic,
        String description
) {
    public static ConversationTopicInfoResDto of(String topic, String description) {
        return ConversationTopicInfoResDto.builder()
                .topic(topic)
                .description(description)
                .build();
    }
}
