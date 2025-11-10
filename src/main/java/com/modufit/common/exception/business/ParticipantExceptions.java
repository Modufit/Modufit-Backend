package com.modufit.common.exception.business;

import com.modufit.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class ParticipantExceptions {
    public static class ParticipantNotFoundException extends BusinessException {
        public ParticipantNotFoundException(Long participantId) {
            super(
                    "PARTICIPANT_NOT_FOUND",
                    "참여자(ID: " + participantId + ")를 찾을 수 없습니다.",
                    HttpStatus.NOT_FOUND
            );
        }

        public ParticipantNotFoundException(Long chatRoomId, Long userId) {
            super(
                    "PARTICIPANT_NOT_FOUND",
                    "채팅방(ID: " + chatRoomId + ")에서 사용자(ID: " + userId + ")를 찾을 수 없습니다.",
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

}
