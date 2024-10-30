package net.skhu.mood_backend.Gathering.api.dto.response;

import lombok.Builder;

@Builder
public record GatheringInfoResDto(
        String host,
        String relationshipType,
        String peopleCount,
        String vibe,
        String averageAge,
        String commonInterests,
        ConversationTopicListResDto conversationTopicInfoResDtos,
        SuggestedActivityListResDto suggestedActivityInfoResDtos
) {
    public static GatheringInfoResDto of(String host,
                                         String relationshipType,
                                         String peopleCount,
                                         String vibe,
                                         String averageAge,
                                         String commonInterests,
                                         ConversationTopicListResDto conversationTopicInfoResDtos,
                                         SuggestedActivityListResDto suggestedActivityInfoResDtos) {
        return GatheringInfoResDto.builder()
                .host(host)
                .relationshipType(relationshipType)
                .peopleCount(peopleCount)
                .vibe(vibe)
                .averageAge(averageAge)
                .commonInterests(commonInterests)
                .conversationTopicInfoResDtos(conversationTopicInfoResDtos)
                .suggestedActivityInfoResDtos(suggestedActivityInfoResDtos)
                .build();
    }
}
