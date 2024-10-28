package net.skhu.mood_backend.auth.application;

import net.skhu.mood_backend.auth.api.dto.response.UserInfo;

public interface AuthService {

    UserInfo getUserInfo(String code);

    String getProvider();
}
