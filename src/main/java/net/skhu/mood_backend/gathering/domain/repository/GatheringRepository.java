package net.skhu.mood_backend.gathering.domain.repository;

import net.skhu.mood_backend.gathering.domain.Gathering;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatheringRepository extends JpaRepository<Gathering, Long> {
}
