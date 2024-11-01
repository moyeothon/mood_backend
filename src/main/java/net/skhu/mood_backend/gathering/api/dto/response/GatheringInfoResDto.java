package net.skhu.mood_backend.gathering.api.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record GatheringInfoResDto(
        Long gatheringId,
        LocalDateTime createdAt,
        String host,
        String relationshipType,
        String peopleCount,
        String vibe,
        String averageAge,
        String commonInterests,
        ConversationTopicListResDto conversationTopicInfoResDtos,
        SuggestedActivityListResDto suggestedActivityInfoResDtos
) {
    public static GatheringInfoResDto of(Long gatheringId,
                                         LocalDateTime createAt,
                                         String host,
                                         String relationshipType,
                                         String peopleCount,
                                         String vibe,
                                         String averageAge,
                                         String commonInterests,
                                         ConversationTopicListResDto conversationTopicInfoResDtos,
                                         SuggestedActivityListResDto suggestedActivityInfoResDtos) {
        return GatheringInfoResDto.builder()
                .gatheringId(gatheringId)
                .createdAt(createAt)
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

    public static GatheringInfoResDto myOf(Long gatheringId,
                                           LocalDateTime createAt,
                                           String host,
                                           String relationshipType,
                                           String peopleCount,
                                           String vibe,
                                           String averageAge,
                                           String commonInterests) {
        return GatheringInfoResDto.builder()
                .gatheringId(gatheringId)
                .createdAt(createAt)
                .host(host)
                .relationshipType(relationshipType)
                .peopleCount(peopleCount)
                .vibe(vibe)
                .averageAge(averageAge)
                .commonInterests(commonInterests)
                .build();
    }
}
