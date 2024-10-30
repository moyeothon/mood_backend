package net.skhu.mood_backend.Gathering.domain;

/*
  SuggestedActivity
  추천 활동
 */

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.skhu.mood_backend.global.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SuggestedActivity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String activity;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gathering_id")
    private Gathering gathering;

    @Builder
    private SuggestedActivity(String activity, String description, Gathering gathering) {
        this.activity = activity;
        this.description = description;
        this.gathering = gathering;
    }

    public void updateSuggestedActivity(String activity, String description) {
        this.activity = activity;
        this.description = description;
    }

}
