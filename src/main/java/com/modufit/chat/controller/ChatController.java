package com.modufit.chat.controller;

import com.modufit.chat.dto.ChatRequestDto;
import com.modufit.chat.dto.ChatResponseDto;
import com.modufit.chat.service.ChatMessageService;
import com.modufit.chat.service.ChatParticipantService;
import com.modufit.common.ApiResponse;
import com.modufit.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;


@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;
    private final ChatParticipantService chatParticipantService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/message")
    public void sendMessage(@Payload ChatRequestDto messageDto) {
        log.info("메시지 전송: {}", messageDto);
        ChatResponseDto saved = chatMessageService.saveMessage(messageDto);
        sendMessage(saved);
    }

    @MessageMapping("/chat/enter")
    public void enterChatRoom(@Payload ChatRequestDto messageDto,
                              StompHeaderAccessor headerAccessor) {
        try {
            log.info("채팅방 입장: {}", messageDto);

            headerAccessor.getSessionAttributes().put("userId", messageDto.getSenderId());
            headerAccessor.getSessionAttributes().put("chatRoomId", messageDto.getChatRoomId());

            ChatResponseDto enter = chatParticipantService.enterChatRoom(messageDto.getChatRoomId(), messageDto.getSenderId());
            sendMessage(enter);
        } catch (BusinessException e) {
            sendErrorMessage(e, headerAccessor);
        }
    }

    @MessageMapping("/chat/leave")
    public void leaveChatRoom(@Payload ChatRequestDto messageDto) {
        log.info("채팅방 퇴장: {}", messageDto);

        ChatResponseDto leave = chatParticipantService.leaveChatRoom(messageDto.getChatRoomId(), messageDto.getSenderId());
        sendMessage(leave);
    }

//    @MessageExceptionHandler(BusinessException.class)
//    @SendToUser
//    public ApiResponse<Void> handleBusinessException(BusinessException exception) {
//        log.warn("STOMP BusinessException 발생", exception);
//        return ApiResponse.error(exception);
//    }
//
//    @MessageExceptionHandler(Exception.class)
//    @SendToUser
//    public ApiResponse<Void> handleGenericException(Exception e) {
//        log.error("STOMP 알 수 없는 예외", e);
//        return ApiResponse.error("STOMP_ERROR", HttpStatus.BAD_REQUEST, "WebSocket 처리 중 오류가 발생했습니다.");
//    }


    private void sendMessage(ChatResponseDto sendMessage) {
        messagingTemplate.convertAndSend(
                "/topic/chat/room/" + sendMessage.getChatRoomId(),
                sendMessage
        );
    }

    private void sendErrorMessage(BusinessException e, StompHeaderAccessor accessor) {
        ApiResponse<Void> error = ApiResponse.error(e);
        String principalName = accessor.getUser().getName();
        log.warn("STOMP 예외 발생, principalName={} 에 전달: {}", principalName, e.getMessage());

        messagingTemplate.convertAndSendToUser(
                principalName,
                "/queue/errors",
                error
        );

    }

}