package net.skhu.mood_backend.auth.api;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import net.skhu.mood_backend.auth.api.dto.request.TokenReqDto;
import net.skhu.mood_backend.auth.api.dto.response.MemberLoginResDto;
import net.skhu.mood_backend.auth.api.dto.response.UserInfo;
import net.skhu.mood_backend.auth.application.AuthMemberService;
import net.skhu.mood_backend.auth.application.AuthService;
import net.skhu.mood_backend.auth.application.AuthServiceFactory;
import net.skhu.mood_backend.auth.application.TokenService;
import net.skhu.mood_backend.global.jwt.api.dto.TokenDto;
import net.skhu.mood_backend.global.template.RspTemplate;
import net.skhu.mood_backend.member.domain.SocialType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDocs {

    private final AuthServiceFactory authServiceFactory;
    private final AuthMemberService memberService;
    private final TokenService tokenService;

    @PostMapping("/{provider}/token")
    public RspTemplate<TokenDto> generateAccessToken(
            @Parameter(name = "provider", description = "소셜 타입(kakao)", in = ParameterIn.PATH)
            @PathVariable(name = "provider") String provider,
            @RequestBody TokenReqDto tokenReqDto) {
        AuthService authService = authServiceFactory.getAuthService(provider);
        UserInfo userInfo = authService.getUserInfo(tokenReqDto.code());

        MemberLoginResDto getMemberDto = memberService.saveUserInfo(userInfo,
                SocialType.valueOf(provider.toUpperCase()));
        TokenDto getToken = tokenService.getToken(getMemberDto);

        return new RspTemplate<>(HttpStatus.OK, "토큰 발급", getToken);
    }

}
