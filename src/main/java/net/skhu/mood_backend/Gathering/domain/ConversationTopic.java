package net.skhu.mood_backend.Gathering.domain;

/*
    ConversationTopic
    대화 주제
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
public class ConversationTopic extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gathering_id")
    private Gathering gathering;

    @Builder
    private ConversationTopic(String topic, String description, Gathering gathering) {
        this.topic = topic;
        this.description = description;
        this.gathering = gathering;
    }

    public void updateConversationTopic(String topic, String description) {
        this.topic = topic;
        this.description = description;
    }

}
