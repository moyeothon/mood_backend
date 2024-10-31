package net.skhu.mood_backend.gathering.exception;

import net.skhu.mood_backend.global.error.exception.AccessDeniedGroupException;

public class GatheringAccessDeniedException extends AccessDeniedGroupException {
    public GatheringAccessDeniedException(String message) {
        super(message);
    }

    public GatheringAccessDeniedException() {
        this("모임 생성자가 아닙니다.");
    }
}
