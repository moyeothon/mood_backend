package net.skhu.mood_backend.gathering.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.skhu.mood_backend.global.entity.BaseEntity;
import net.skhu.mood_backend.member.domain.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gathering extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "누구")
    private String host;

    @Schema(description = "친분")
    private String relationshipType;

    @Schema(description = "사람수")
    private String peopleCount;

    @Schema(description = "분위기")
    private String vibe;

    @Schema(description = "평균 연령대")
    private String averageAge;

    @Schema(description = "공통 관심사")
    private String commonInterests;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Schema(description = "대화 주제")
    @OneToMany(mappedBy = "gathering", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ConversationTopic> conversationTopics = new ArrayList<>();

    @Schema(description = "추천 활동")
    @OneToMany(mappedBy = "gathering", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<SuggestedActivity> suggestedActivities = new ArrayList<>();

    @Builder
    private Gathering(String host, String relationshipType, String peopleCount, String vibe, String averageAge,
                      String commonInterests, Member member) {
        this.host = host;
        this.relationshipType = relationshipType;
        this.peopleCount = peopleCount;
        this.vibe = vibe;
        this.averageAge = averageAge;
        this.commonInterests = commonInterests;
        this.member = member;
    }

    public void updateGathering(String host,
                                String relationshipType,
                                String peopleCount,
                                String vibe,
                                String averageAge,
                                String commonInterests) {
        this.host = host;
        this.relationshipType = relationshipType;
        this.peopleCount = peopleCount;
        this.vibe = vibe;
        this.averageAge = averageAge;
        this.commonInterests = commonInterests;
    }

}
