package net.skhu.mood_backend.auth.application;

import net.skhu.mood_backend.auth.api.dto.response.MemberLoginResDto;
import net.skhu.mood_backend.global.jwt.TokenProvider;
import net.skhu.mood_backend.global.jwt.api.dto.TokenDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TokenService {

    private final TokenProvider tokenProvider;

    public TokenService(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Transactional
    public TokenDto getToken(MemberLoginResDto memberLoginResDto) {
        return tokenProvider.generateToken(memberLoginResDto.findMember().getEmail());
    }

}
