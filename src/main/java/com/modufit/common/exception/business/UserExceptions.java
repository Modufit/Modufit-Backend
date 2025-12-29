package com.modufit.common.exception.business;

import com.modufit.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class UserExceptions {
    public static class UserNotFoundException extends BusinessException {
        public UserNotFoundException(long userId) {
            super("USER_NOT_FOUND", String.format("ID가 " + userId + "인 회원을 찾을 수 없습니다.", userId), HttpStatus.NOT_FOUND);
        }

        public UserNotFoundException(String email) {
            super("USER_NOT_FOUND", String.format("ID가 " + email + "인 회원을 찾을 수 없습니다.", email), HttpStatus.NOT_FOUND);
        }
    }

    public static class UserCreationException extends BusinessException {
        public UserCreationException() {
            super("USER_CREATION_FAILED", "회원 생성에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
    }
}
