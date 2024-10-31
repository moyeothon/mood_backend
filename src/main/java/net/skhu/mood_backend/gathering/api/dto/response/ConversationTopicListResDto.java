package net.skhu.mood_backend.gathering.api.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record ConversationTopicListResDto(
        List<ConversationTopicInfoResDto> conversationTopicInfoResDtos
) {
    public static ConversationTopicListResDto from(List<ConversationTopicInfoResDto> conversationTopicInfoResDtos) {
        return ConversationTopicListResDto.builder()
                .conversationTopicInfoResDtos(conversationTopicInfoResDtos)
                .build();
    }
}