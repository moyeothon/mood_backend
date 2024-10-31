package net.skhu.mood_backend.gathering.api.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.skhu.mood_backend.gathering.domain.ConversationTopic;
import net.skhu.mood_backend.gathering.domain.SuggestedActivity;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GptParsingDto {
    private List<ConversationTopic> conversationTopics;
    private List<SuggestedActivity> suggestedActivities;
}
