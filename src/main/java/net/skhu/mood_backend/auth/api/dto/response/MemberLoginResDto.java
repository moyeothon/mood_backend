package net.skhu.mood_backend.auth.api.dto.response;

import lombok.Builder;
import net.skhu.mood_backend.member.domain.Member;

@Builder
public record MemberLoginResDto(
        Member findMember
) {
    public static MemberLoginResDto from(Member member) {
        return MemberLoginResDto.builder()
                .findMember(member)
                .build();
    }
}
