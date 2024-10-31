package net.skhu.mood_backend.review.api;

import net.skhu.mood_backend.global.template.RspTemplate;
import net.skhu.mood_backend.member.domain.Member;
import net.skhu.mood_backend.review.api.dto.request.ReviewSaveReqDto;
import net.skhu.mood_backend.review.api.dto.response.ReviewInfoResDto;
import net.skhu.mood_backend.review.api.dto.response.ReviewListResDto;
import net.skhu.mood_backend.review.application.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController implements ReviewControllerDocs {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("")
    public RspTemplate<ReviewInfoResDto> createReview(@AuthenticationPrincipal Member member,
                                                      @RequestBody ReviewSaveReqDto reviewSaveReqDto) {
        return new RspTemplate<>(HttpStatus.OK, "후기 작성", reviewService.createReview(member, reviewSaveReqDto));
    }

    @GetMapping("")
    public RspTemplate<ReviewListResDto> getReviews(@AuthenticationPrincipal Member member) {
        return new RspTemplate<>(HttpStatus.OK, "후기 조회", reviewService.getReviews());
    }

}
