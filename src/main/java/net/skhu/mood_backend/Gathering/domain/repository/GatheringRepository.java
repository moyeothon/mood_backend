package net.skhu.mood_backend.Gathering.domain.repository;

import net.skhu.mood_backend.Gathering.domain.Gathering;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatheringRepository extends JpaRepository<Gathering, Long> {
}
