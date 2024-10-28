package net.skhu.mood_backend.member.domain.repository;

import java.util.Optional;
import net.skhu.mood_backend.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
}
