package net.skhu.mood_backend.gathering.domain.repository;

import java.util.List;
import net.skhu.mood_backend.gathering.domain.Gathering;
import net.skhu.mood_backend.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GatheringRepository extends JpaRepository<Gathering, Long> {

    @Query(value = """
            SELECT g
            FROM Gathering g
            JOIN g.member m
            WHERE m = :member
            ORDER BY g.createdAt DESC
            """)
    List<Gathering> findByMember(Member member);
    
}
