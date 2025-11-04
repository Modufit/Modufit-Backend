package com.modufit.common.exception.business;

import com.modufit.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class ChatExceptions {
    public static class ChatRoomNotFoundException extends BusinessException {
        public ChatRoomNotFoundException(Long roomId) {
            super(
                    "CHAT_ROOM_NOT_FOUND",
                    "해당 채팅방(ID: " + roomId + ")을 찾을 수 없습니다.",
                    HttpStatus.NOT_FOUND
            );
        }
    }

    public static class MessageSendFailedException extends BusinessException {
        public MessageSendFailedException(String reason) {
            super(
                    "MESSAGE_SEND_FAILED",
                    "메시지 전송에 실패했습니다. 사유: " + reason,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public static class ChatAccessDeniedException extends BusinessException {
        public ChatAccessDeniedException() {
            super(
                    "CHAT_ACCESS_DENIED",
                    "해당 채팅방에 접근할 권한이 없습니다.",
                    HttpStatus.FORBIDDEN
            );
        }
    }
}
