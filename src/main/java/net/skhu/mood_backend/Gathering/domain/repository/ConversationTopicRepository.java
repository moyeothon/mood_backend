package net.skhu.mood_backend.Gathering.domain.repository;

import java.util.List;
import net.skhu.mood_backend.Gathering.domain.ConversationTopic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationTopicRepository extends JpaRepository<ConversationTopic, Long> {

    List<ConversationTopic> findByGatheringId(Long gatheringId);
    
}
