package net.skhu.mood_backend.review.domain.repository;

import net.skhu.mood_backend.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
