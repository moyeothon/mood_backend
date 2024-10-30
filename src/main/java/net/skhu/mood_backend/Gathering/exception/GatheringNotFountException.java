package net.skhu.mood_backend.Gathering.exception;

import net.skhu.mood_backend.global.error.exception.NotFoundGroupException;

public class GatheringNotFountException extends NotFoundGroupException {
    public GatheringNotFountException(String message) {
        super(message);
    }

    public GatheringNotFountException() {
        this("존재하지 않는 모임입니다.");
    }
}
