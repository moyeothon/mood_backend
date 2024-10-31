package net.skhu.mood_backend.gathering.domain.repository;

import java.util.List;
import net.skhu.mood_backend.gathering.domain.SuggestedActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestedActivityRepository extends JpaRepository<SuggestedActivity, Long> {

    List<SuggestedActivity> findByGatheringId(Long gatheringId);

}
