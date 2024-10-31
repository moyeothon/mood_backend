package net.skhu.mood_backend.review.application;

import java.util.List;
import net.skhu.mood_backend.member.domain.Member;
import net.skhu.mood_backend.review.api.dto.request.ReviewSaveReqDto;
import net.skhu.mood_backend.review.api.dto.response.ReviewInfoResDto;
import net.skhu.mood_backend.review.api.dto.response.ReviewListResDto;
import net.skhu.mood_backend.review.domain.Review;
import net.skhu.mood_backend.review.domain.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public ReviewInfoResDto createReview(Member member, ReviewSaveReqDto reviewSaveReqDto) {
        Review review = Review.builder()
                .starCount(reviewSaveReqDto.starCount())
                .content(reviewSaveReqDto.content())
                .member(member)
                .build();

        reviewRepository.save(review);

        return ReviewInfoResDto.of(review.getStarCount(), review.getContent(), member.getNickname());
    }

    public ReviewListResDto getReviews() {
        List<Review> reviews = reviewRepository.findAll();

        List<ReviewInfoResDto> reviewInfoResDtos = reviews.stream()
                .map(review ->
                        ReviewInfoResDto.of(review.getStarCount(),
                                review.getContent(),
                                review.getMember().getNickname()))
                .toList();

        return ReviewListResDto.from(reviewInfoResDtos);
    }

}
